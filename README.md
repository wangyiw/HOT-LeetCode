# 项目说明

## 一、项目简介

HOT - LEETCODE 刷题本

你可以在其中实现/练习各类算法与数据结构（如 `com.yww.code.hot.list.sortList` 等），也可以将其作为日常练习与刷题的代码仓库。

## 二、开发环境

- **JDK 版本**：建议使用 JDK 8 或以上版本
- **构建工具**：Maven 3.x
- **开发工具**：推荐 IntelliJ IDEA / Eclipse / VS Code（安装 Java 扩展）

## 三、项目结构

```text
.
├── pom.xml                // Maven 项目配置文件
├── src
│   ├── main
│   │   ├── java          // Java 源码目录
│   │   └── resources     // 资源文件目录
│   └── test
│       ├── java          // 测试代码目录
│       └── resources     // 测试资源目录
└── target                 // Maven 编译输出目录（自动生成）
```

## 四、构建与运行

在项目根目录（与 `pom.xml` 同级）执行以下命令：

```bash
# 编译
mvn compile

# 运行测试
mvn test

# 打包（生成 jar 包）
mvn package
```

如果项目中配置了可执行入口（`main` 方法或 Spring Boot 启动类），可以使用：

```bash
mvn spring-boot:run   # 若为 Spring Boot 项目
```

或在 IDE 中直接右键带有 `main` 方法的类，选择 `Run` 运行。

## 五、常见问题

- **依赖下载缓慢**：可以在 Maven 配置中使用国内镜像仓库（如阿里云 Maven 仓库）。
- **JDK 版本不兼容**：请确保本地 JDK 版本与 `pom.xml` 中配置的 `maven-compiler-plugin` 一致或更高。
