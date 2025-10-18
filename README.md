### **综合媒体应用服务平台 - 项目介绍**

**一个基于 Spring Boot 与策略模式构建的现代化、可扩展多功能的音乐服务平台后端代码。**

![Java](https://img.shields.io/badge/Java-17+-red?style=flat&logo=openjdk)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-green?style=flat&logo=springboot)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat&logo=mysql)

#### 🎵 项目简介

`本服务平台` 是一个整合了多家第三方音乐服务API的后端应用。它没有直接提供音乐资源，而是作为一个**智能的“音乐服务聚合器”与“代理网关”**，为用户提供了统一、便捷的音乐在线播放、搜索与下载入口。其核心在于通过优雅的**架构设计**与**响应式编程**，解决了多数据源集成、高并发请求及代码可维护性等核心工程问题。
目前只有音乐和视频解析两大部分,后续将会陆续更新其他服务例如全网动漫高清观看等

#### 前端部分 
前端部分采用vue3 + pinia + tailwindcss + ant部分组件 + electron等技术,项目开源于hongchuwudi/hc-utils,这里不在过多介绍。

#### 音乐模块核心特性

- **🎼 多平台音乐聚合**：无缝集成多个主流音乐平台，打破平台壁垒，实现“一次搜索，全网结果”。
- **🧩 智能路由策略**：采用**策略模式**，系统能够根据音乐来源标识，自动选择并调用对应的第三方API，业务扩展极其便捷。
- **⚡ 高性能混合IO**：基于 **Spring WebClient**，针对高并发、低延迟的**元数据查询**使用**非阻塞调用**；针对大文件、稳定传输的**音乐下载**使用**阻塞调用**，实现性能与稳定性的最佳平衡。
- **🔧 工程化最佳实践**：
  - **配置中心化**：所有第三方服务URL等配置通过 `@ConfigurationProperties` 统一管理，杜绝硬编码，支持多环境无缝切换。
  - **结构化日志**：利用 **Spring AOP** 与 **SLF4J** 实现声明式日志切面，无侵入式记录关键业务链路，便于监控与调试。
  - **清晰的分层架构**：采用多模块设计，严格分离控制层、业务层、数据层与通用组件，代码结构清晰，易于维护与协作。

#### 系统架构与技术栈

**后端技术栈**
- **框架**: Spring Boot 3.x, Spring Framework
- **数据持久层**: MyBatis, MySQL
- **HTTP客户端**: Spring WebClient (响应式 & 阻塞)
- **架构模式**: 策略模式、面向切面编程(AOP)
- **日志**: SLF4J + Logback

**项目模块**
```
music-service/
├── hc-app          # 应用启动层
├── hc-proxy        # 第三方API代理与策略模式实现层
├── hc-service      # 核心业务逻辑层
├── hc-common       # 通用工具与配置
└── hc-pojo         # 实体模型层
```

#### 🚀 快速开始

**1. 克隆项目**
```bash
git clone https://github.com/hongchuwudi/hc-utils-server.git
```

**2. 配置数据库**
- 创建 MySQL 数据库。
- 在 `src/main/resources/application.yml` 中配置您的数据源。

**3. 配置第三方API密钥**
在 `application.yml`同级目录中创建application-proApiUrl.yml,application-prod.yml和 application-dev.yml 中填入您从各音乐平台申请的API密钥与端点URL。
 1. application-proApiUrl.yml
```yaml
# application-proApiUrl.yml 其中的url和秘钥因为某种原因无法公开,但可以联系作者qq2772167017获取
bilibili:
  api:
    base: "...."
    referer: "...."
    user-agent: "...."
    search-url: "...."
    type-search-url: "...."
    play: "...."
    login: "...."
    video-info: "...."
    login-status: "...."

my-music:
  wangyiMusicUrl:
    url: "..."
    key: "..."
  kuWoMusicUrl:
    url: "..."
    key: "..."
  qqMusicUrl:
    url: "..."
    key: "..."
  qishuiMusicUrl:
    url: "..."
    key: "..."

my-paper:
  jin-shan-url: "...." # 金山毒霸Wallpaper
```
2.application-dev.yml
``` yaml
# 开发环境 - 覆盖主配置的默认值
spring:
  config:
    activate:
      on-profile: dev

  # 开发环境数据库配置
  datasource:
    url: jdbc:mysql://localhost:3306/hc_utils?useSSL=false&serverTimezone=Asia/Shanghai
    username: root
    password: hongchu

# 开发环境应用配置
app:
  proxy:
    timeout: 10000  # 开发环境超时时间长
    max-connections: 500
  debug: true
  cache-enabled: false
```
3.application-prod.yml
``` yaml
# 生产环境配置
spring:
  config:
    activate:
      on-profile: prod

  datasource:
    url: jdbc:mysql://${DB_HOST:prod-mysql}:${DB_PORT:3306}/${DB_NAME:hc_prod}?useSSL=false&serverTimezone=Asia/Shanghai
    username: ${DB_USERNAME}  # 必须提供
    password: ${DB_PASSWORD}  # 必须提供

server:
  port: 8080  # 生产环境用标准端口

app:
  proxy:
    timeout: ${PROXY_TIMEOUT:5000}     # 生产环境默认5秒
    max-connections: ${PROXY_MAX_CONNECTIONS:1000}
  debug: false
  cache-enabled: true

```

**4. 运行项目**
```bash
mvn spring-boot:run
```
应用启动后，可通过 `http://localhost:16666` 访问API。

#### 📌 核心设计解读

**1. 策略模式的应用**
在 `proxy` 模块中，我们定义了 `MusicPlatformStrategy` 接口，并为每个集成的音乐平台（如网易云、酷狗）提供了具体的实现类。通过一个简单的上下文路由器，系统可根据入参自动委派给正确的策略执行，未来新增平台只需实现此接口即可，完全符合**开闭原则**。
**2. 混合IO模型**
项目充分利用了 Spring 的响应式编程能力。对于海量的歌曲搜索与信息拉取，使用 `WebClient` 进行**非阻塞调用**以最大化利用系统资源，提升吞吐量。而对于需要稳定传输流的文件下载，则采用传统的**阻塞式调用**，确保下载过程的可靠性。

#### 🤝 贡献
我们欢迎任何形式的贡献！如果您有好的想法或发现了Bug，请随时提交 Issue 或 Pull Request。

#### 📄 许可证
本项目仅用于**个人学习与技术交流**。所有音乐版权归原平台及权利人所有，请尊重知识产权，合法使用。
