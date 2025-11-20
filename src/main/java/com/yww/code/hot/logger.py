"""
Eagle 日志系统配置文件（完整版）
支持 Logs、Traces、Metrics 三种类型的数据写入
"""
import logging
from src.config import settings
from opentelemetry import trace, metrics
from opentelemetry.sdk.trace import TracerProvider
from opentelemetry.sdk.trace.export import BatchSpanProcessor
from opentelemetry.sdk.resources import Resource
from opentelemetry.exporter.otlp.proto.grpc.trace_exporter import OTLPSpanExporter
from opentelemetry._logs import set_logger_provider
from opentelemetry.sdk._logs import LoggerProvider, LoggingHandler
from opentelemetry.sdk._logs.export import BatchLogRecordProcessor
from opentelemetry.exporter.otlp.proto.grpc._log_exporter import OTLPLogExporter
from opentelemetry.sdk.metrics import MeterProvider
from opentelemetry.sdk.metrics.export import PeriodicExportingMetricReader
from opentelemetry.exporter.otlp.proto.grpc.metric_exporter import OTLPMetricExporter
from opentelemetry.trace import format_trace_id, format_span_id

# 全局变量，避免重复初始化
_eagle_initialized = False
_eagle_logger = None
_eagle_tracer = None
_eagle_meter = None


# ==================== TraceID 工具函数 ====================

def generate_trace_id():
    """生成新的 trace_id"""
    return trace.generate_trace_id()


def generate_span_id():
    """生成新的 span_id"""
    return trace.generate_span_id()


def format_dida_trace_id(trace_id=None, span_id=None, flags="01"):
    """
    格式化为 X-Dida-Trace-Id 格式
    格式：{version}-{trace_id}-{span_id}-{flags}
    
    Args:
        trace_id: OpenTelemetry trace_id，如果为None则生成新的
        span_id: OpenTelemetry span_id，如果为None则生成新的
        flags: 采样标志，"00"表示未采样，"01"表示已采样
    
    Returns:
        str: 格式化的 trace_id 字符串
    """
    if trace_id is None:
        trace_id = generate_trace_id()
    if span_id is None:
        span_id = generate_span_id()
    
    version = "00"
    trace_id_hex = format_trace_id(trace_id)
    span_id_hex = format_span_id(span_id)
    
    return f"{version}-{trace_id_hex}-{span_id_hex}-{flags}"


def parse_dida_trace_id(dida_trace_id):
    """
    解析 X-Dida-Trace-Id 头信息
    
    Args:
        dida_trace_id: X-Dida-Trace-Id 头的值
    
    Returns:
        dict: 包含 version, trace_id, span_id, flags 的字典
    """
    try:
        parts = dida_trace_id.split('-')
        if len(parts) != 4:
            raise ValueError("Invalid X-Dida-Trace-Id format")
        
        return {
            "version": parts[0],
            "trace_id": parts[1],
            "span_id": parts[2],
            "flags": parts[3]
        }
    except Exception as e:
        return {
            "version": "00",
            "trace_id": "00000000000000000000000000000000",
            "span_id": "0000000000000000",
            "flags": "00",
            "error": str(e)
        }


def get_current_trace_context():
    """
    获取当前的 trace 上下文
    
    Returns:
        dict: 包含当前 trace_id 和 span_id 的字典
    """
    current_span = trace.get_current_span()
    if current_span and current_span.is_recording():
        span_context = current_span.get_span_context()
        return {
            "trace_id": format_trace_id(span_context.trace_id),
            "span_id": format_span_id(span_context.span_id),
            "is_valid": span_context.is_valid
        }
    else:
        # 如果没有活跃的 span，生成新的
        trace_id = generate_trace_id()
        span_id = generate_span_id()
        return {
            "trace_id": format_trace_id(trace_id),
            "span_id": format_span_id(span_id),
            "is_valid": True
        }


def create_trace_context(trace_id_hex=None, span_id_hex=None):
    """
    创建新的 trace 上下文
    
    Args:
        trace_id_hex: 16进制格式的 trace_id
        span_id_hex: 16进制格式的 span_id
    
    Returns:
        dict: trace 上下文信息
    """
    if trace_id_hex:
        try:
            trace_id = int(trace_id_hex, 16)
        except ValueError:
            trace_id = generate_trace_id()
    else:
        trace_id = generate_trace_id()
    
    if span_id_hex:
        try:
            span_id = int(span_id_hex, 16)
        except ValueError:
            span_id = generate_span_id()
    else:
        span_id = generate_span_id()
    
    return {
        "trace_id": format_trace_id(trace_id),
        "span_id": format_span_id(span_id),
        "trace_id_int": trace_id,
        "span_id_int": span_id
    }


# ==================== 日志格式化器 ====================


class TraceAwareFormatter(logging.Formatter):
    """
    自动添加 TraceID 的日志格式化器
    
    TraceID 来源优先级：
    1. 当前活跃的 Span Context（OpenTelemetry 自动管理）
    2. HTTP 请求头（通过 propagator 传播）
    3. 如果都没有，不设置 TraceID（留空）
    """
    
    def format(self, record):
        # 默认值：不设置 TraceID
        record.trace_id = ""
        record.span_id = ""
        record.dida_trace_id = ""
        
        # 1. 优先从当前活跃的 Span 获取 TraceID
        current_span = trace.get_current_span()
        if current_span and current_span.is_recording():
            span_context = current_span.get_span_context()
            if span_context.is_valid:
                # 使用当前 Span 的 TraceID（可能来自 HTTP 请求头）
                record.trace_id = format_trace_id(span_context.trace_id)
                record.span_id = format_span_id(span_context.span_id)
                record.dida_trace_id = f"00-{record.trace_id}-{record.span_id}-01"
                return super().format(record)
        
        # 2. 尝试从 Context 中获取（即使没有活跃 Span）
        try:
            from opentelemetry import context
            current_context = context.get_current()
            span_context = trace.get_current_span(current_context).get_span_context()
            
            if span_context and span_context.is_valid:
                record.trace_id = format_trace_id(span_context.trace_id)
                record.span_id = format_span_id(span_context.span_id)
                record.dida_trace_id = f"00-{record.trace_id}-{record.span_id}-01"
                return super().format(record)
        except Exception:
            # 忽略错误，继续下一步
            pass
        
        # 3. 如果都没有，留空（不自己生成）
        # TraceID 应该由上游服务传递或者在 HTTP 入口处生成
        return super().format(record)


def add_trace_to_log_record(record, trace_id=None, span_id=None, dida_trace_id=None):
    """为日志记录添加 trace 信息"""
    if trace_id:
        record.trace_id = trace_id
    if span_id:
        record.span_id = span_id
    if dida_trace_id:
        record.dida_trace_id = dida_trace_id
    return record


def log_with_trace(logger, level, message, trace_id=None, span_id=None, dida_trace_id=None, **extra):
    """
    带 TraceID 的日志记录方法
    
    Args:
        logger: 日志记录器
        level: 日志级别 ('info', 'error', 'warning', 'debug')
        message: 日志消息
        trace_id: 16进制格式的 trace_id
        span_id: 16进制格式的 span_id  
        dida_trace_id: X-Dida-Trace-Id 格式的字符串
        **extra: 其他额外字段
    """
    # 如果没有提供 trace 信息，尝试从当前上下文获取
    if not trace_id or not span_id:
        current_span = trace.get_current_span()
        if current_span and current_span.is_recording():
            span_context = current_span.get_span_context()
            if span_context.is_valid:
                trace_id = format_trace_id(span_context.trace_id)
                span_id = format_span_id(span_context.span_id)
                if not dida_trace_id:
                    dida_trace_id = f"00-{trace_id}-{span_id}-01"
    
    # 添加 trace 信息到 extra
    if trace_id:
        extra['trace_id'] = trace_id
    if span_id:
        extra['span_id'] = span_id
    if dida_trace_id:
        extra['dida_trace_id'] = dida_trace_id
    
    # 根据级别记录日志
    if level.lower() == 'debug':
        logger.debug(message, extra=extra)
    elif level.lower() == 'info':
        logger.info(message, extra=extra)
    elif level.lower() == 'warning':
        logger.warning(message, extra=extra)
    elif level.lower() == 'error':
        logger.error(message, extra=extra)
    else:
        logger.info(message, extra=extra)


def setup_eagle_logging(log_level="INFO"):
    """
    设置完整的鹰眼系统（Logs + Traces + Metrics）
    返回专门的鹰眼日志器、追踪器和指标器

    Args:
        log_level: 日志级别，如 "DEBUG", "INFO", "WARNING", "ERROR"

    Returns:
        tuple: (eagle_logger, eagle_tracer, eagle_meter)
    """
    global _eagle_initialized, _eagle_logger, _eagle_tracer, _eagle_meter
    
    # 如果已经初始化，直接返回
    if _eagle_initialized:
        return _eagle_logger, _eagle_tracer, _eagle_meter
    
    # 从全局配置读取鹰眼配置
    eagle_endpoint = settings.EAGLE_OTLP_ENDPOINT or "https://otel-hotel.cn-hangzhou-intranet.log.aliyuncs.com:10010"
    service_name = settings.SERVICE_NAME or "didatravel-hotel-agents"
    ak_id = settings.EAGLE_ACCESS_KEY_ID or ""
    ak_secret = settings.EAGLE_ACCESS_KEY_SECRET or ""

    # 转换日志级别
    log_level = getattr(logging, log_level.upper(), logging.INFO)

    # 创建资源
    resource = Resource.create({
        "service.name": service_name,
        "service.namespace": "di-ai",
    })

    # 通用的 Headers 配置
    headers = {
        "x-sls-otel-project": "otel-hotel",
        "x-sls-otel-instance-id": "di-ai",
        "x-sls-otel-ak-id": ak_id,
        "x-sls-otel-ak-secret": ak_secret
    }
    
    # 添加 X-Dida-Trace-Id 支持
    def get_trace_id():
        """获取当前 trace ID，格式: {version}-{trace_id}-{span_id}-{flags}"""
        current_span = trace.get_current_span()
        if current_span and current_span.get_span_context().is_valid:
            span_context = current_span.get_span_context()
            # 格式化为 W3C Trace Context 格式
            trace_id = f"00-{span_context.trace_id:032x}-{span_context.span_id:016x}-01"
            return trace_id
        return None

    # 1. 配置 Logs 系统 (di-ai-logs)
    logger_provider = LoggerProvider(resource=resource)
    set_logger_provider(logger_provider)

    # 为日志导出器添加动态 trace ID 头
    def create_log_exporter_with_trace():
        """创建带有动态 trace ID 的日志导出器"""
        current_headers = headers.copy()
        trace_id = get_trace_id()
        if trace_id:
            current_headers["X-Dida-Trace-Id"] = trace_id
        return OTLPLogExporter(
            endpoint=eagle_endpoint,
            headers=current_headers,
            insecure=False
        )
    
    log_exporter = create_log_exporter_with_trace()

    logger_provider.add_log_record_processor(BatchLogRecordProcessor(log_exporter))

    # 使用统一的 TraceAwareFormatter
    class EnhancedTraceFormatter(TraceAwareFormatter):
        def format(self, record):
            # 先调用父类方法添加 trace 信息
            super().format(record)
            
            # 如果 extra 中有 trace 信息，优先使用
            if hasattr(record, '__dict__') and 'trace_id' in record.__dict__:
                # extra 中已有 trace_id，保持不变
                pass
            
            return logging.Formatter.format(self, record)

    eagle_handler = LoggingHandler(level=log_level, logger_provider=logger_provider)
    formatter = EnhancedTraceFormatter(datefmt='%Y-%m-%d %H:%M:%S')
    eagle_handler.setFormatter(formatter)

    # 创建专门的鹰眼日志器
    _eagle_logger = logging.getLogger("eagle_logger")
    _eagle_logger.setLevel(log_level)
    _eagle_logger.addHandler(eagle_handler)

    # 2. 配置 Traces 系统 (di-ai-traces)
    trace.set_tracer_provider(TracerProvider(resource=resource))
    
    # 为 trace 导出器添加动态 trace ID 头
    def create_trace_exporter_with_trace():
        """创建带有动态 trace ID 的追踪导出器"""
        current_headers = headers.copy()
        trace_id = get_trace_id()
        if trace_id:
            current_headers["X-Dida-Trace-Id"] = trace_id
        return OTLPSpanExporter(
            endpoint=eagle_endpoint,
            headers=current_headers,
            insecure=False
        )
    
    trace_exporter = create_trace_exporter_with_trace()
    span_processor = BatchSpanProcessor(trace_exporter)
    trace.get_tracer_provider().add_span_processor(span_processor)
    
    # 创建追踪器
    _eagle_tracer = trace.get_tracer("eagle_tracer")

    # 3. 配置 Metrics 系统 (di-ai-metrics)
    metric_exporter = OTLPMetricExporter(
        endpoint=eagle_endpoint,
        headers=headers,
        insecure=False
    )
    
    metric_reader = PeriodicExportingMetricReader(
        exporter=metric_exporter,
        export_interval_millis=5000  # 5秒导出一次
    )
    
    meter_provider = MeterProvider(
        resource=resource,
        metric_readers=[metric_reader]
    )
    metrics.set_meter_provider(meter_provider)
    
    # 创建指标器
    _eagle_meter = metrics.get_meter("eagle_meter")

    print(f"✓ Eagle 完整系统已配置完成")
    print(f"  - 服务名称: {service_name}")
    print(f"  - Logs: di-ai-logs (鹰眼日志器)")
    print(f"  - Traces: di-ai-traces (链路追踪)")
    print(f"  - Metrics: di-ai-metrics (监控指标)")
    print(f"  - AK_ID: {ak_id[:10] if ak_id else '未设置'}...")
    print(f"  - AK_SECRET: {'已设置' if ak_secret else '未设置'}")
    print(f"  - Headers 配置: {'完整' if ak_id and ak_secret else '缺失'}")

    # 标记为已初始化
    _eagle_initialized = True

    # 返回三个组件
    return _eagle_logger, _eagle_tracer, _eagle_meter


def setup_eagle_logging_simple(log_level="INFO"):
    """
    简化版本，只返回logs日志器（向后兼容）
    """
    eagle_logger, _, _ = setup_eagle_logging(log_level)
    return eagle_logger





# 为了兼容性，保留原来的函数但改为调用新的函数
def init_eagle_logging():
    """保持兼容性的初始化函数"""
    return setup_eagle_logging()


# 如果需要在应用启动时自动初始化，取消下面的注释
# logger = setup_eagle_logging()
# logger.info("Eagle 日志系统已启动")
