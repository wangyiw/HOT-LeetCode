"""
最简 Trace 装饰器实现
自动写入 TraceID 到日志
"""

import asyncio
import logging
import functools
import inspect
from opentelemetry import trace

# 获取全局 tracer
tracer = trace.get_tracer(__name__)


def simple_trace(span_name: str = None):
    """
    最简 Trace 装饰器
    
    功能：
    1. 创建 Span（自动生成或继承 TraceID）
    2. TraceID 自动传播到日志（通过 TraceAwareFormatter）
    3. 记录异常
    
    Usage:
        @simple_trace("send_email")
        async def send_email(to, subject):
            logger.info("发送邮件")  # 自动带 TraceID
            return result
    """
    def decorator(func):
        name = span_name or func.__name__
        
        @functools.wraps(func)
        async def async_wrapper(*args, **kwargs):
            with tracer.start_as_current_span(name) as span:
                try:
                    return await func(*args, **kwargs)
                except Exception as e:
                    span.record_exception(e)
                    raise
        
        @functools.wraps(func)
        def sync_wrapper(*args, **kwargs):
            with tracer.start_as_current_span(name) as span:
                try:
                    return func(*args, **kwargs)
                except Exception as e:
                    span.record_exception(e)
                    raise
        
        return async_wrapper if inspect.iscoroutinefunction(func) else sync_wrapper
    
    return decorator


# ============ 使用示例 ============

if __name__ == "__main__":
    # 独立运行时添加项目根目录到Python路径
    import sys
    import os
    sys.path.insert(0, os.path.dirname(os.path.dirname(os.path.dirname(__file__))))
    
    # 初始化 Eagle 日志系统
    from src.utils.eagle_logger import setup_eagle_logging
    logger, _, _ = setup_eagle_logging()
    
    @simple_trace("process_hotel")
    async def process_hotel(hotel_name: str):
        logger.info(f"开始处理酒店: {hotel_name}")  #  自动带 TraceID
        await asyncio.sleep(0.1)
        logger.info(f"处理完成: {hotel_name}")      #  同一个 TraceID
        return f"Processed: {hotel_name}"
    
    @simple_trace("send_email")
    async def send_email(to: str):
        logger.info(f"发送邮件到: {to}")  #  继承父 Span 的 TraceID
        return f"Sent to {to}"
    
    @simple_trace("main_pipeline")
    async def main():
        logger.info("开始主流程")  #  生成新的 TraceID
        result1 = await process_hotel("Hotel ABC")
        result2 = await send_email("test@example.com")
        logger.info("主流程完成")  #  同一个 TraceID
        return [result1, result2]
    
    # 运行测试
    asyncio.run(main())
