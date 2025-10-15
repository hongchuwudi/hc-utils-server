## 1. WebClient 是什么？

### 简单理解
**WebClient** 就是 Spring 提供的"**智能快递员**"，专门用来发送 HTTP 请求。

```java
// 传统方式 - 像自己跑去取快递
RestTemplate template = new RestTemplate();
String result = template.getForObject("http://api.com/data", String.class);
// 你必须等着快递员把包裹完全准备好才能拿

// WebClient方式 - 像手机下单，快递上门
WebClient client = WebClient.create();
Mono<String> result = client.get().uri("http://api.com/data").retrieve().bodyToMono(String.class);
// 下完单就可以干别的事，快递到了会通知你
```

## 2. 核心概念：Mono 和 Flux

### Mono - "单个快递包裹"
```java
Mono<String> mono = Mono.just("Hello"); 
// 就像：一个可能装有数据的盒子，要么有1个东西，要么空的

// 实际用法：
Mono<User> user = getUserById(123);     // 查单个用户
Mono<Boolean> result = deleteUser(123); // 删除操作
```

### Flux - "多个快递包裹"
```java
Flux<String> flux = Flux.just("苹果", "香蕉", "橙子");
// 就像：一条传送带，上面会陆续送来多个数据

// 实际用法：
Flux<Order> orders = getUserOrders(123); // 获取用户的所有订单
Flux<Message> messages = getChatStream(); // 获取聊天消息流
```

## 3. 阻塞 vs 非阻塞（核心原理）

### 阻塞模式 - "傻等"
```java
public String blockingCall() {
    String result = webClient.get()
            .uri("/api/data")
            .retrieve()
            .bodyToMono(String.class)
            .block(); // 线程在这里卡住等待
    
    System.out.println("拿到结果: " + result);
    return result;
}

// 执行过程：
// 线程：[发送请求] ---等待3秒---> [处理结果]
// ❌ 线程3秒钟什么都干不了，浪费！
```

### 非阻塞模式 - "聪明等待"
```java
public Mono<String> nonBlockingCall() {
    Mono<String> resultMono = webClient.get()
            .uri("/api/data")
            .retrieve()
            .bodyToMono(String.class);
    // 注意：没有 .block()！
    
    System.out.println("立即返回，不等待");
    return resultMono;
}

// 使用：
nonBlockingCall().subscribe(result -> {
    System.out.println("结果来了: " + result); // 回调执行
});

// 执行过程：
// 线程：[发送请求] -> [立即返回] -> [处理其他任务]
// ✅ 结果到达时自动回调，线程不浪费
```

## 4. 什么时候用阻塞？什么时候用非阻塞？

### 用阻塞的情况（简单直接）
```java
// 场景1：获取二维码（你的代码）
public BilibiliPassportLoginRes getWebLoginQrcode() {
    return webClient.get()
            .uri(urlconfig.getLogin())
            .retrieve()
            .bodyToMono(BilibiliPassportLoginRes.class)
            .block(); // ✅ 需要立即给用户显示二维码
}

// 适合阻塞的场景：
// - 用户需要立即看到结果
// - 请求频次低
// - 学习阶段，代码简单
```

### 用非阻塞的情况（性能优先）
```java
// 场景2：查询登录状态（建议修改）
public Mono<BilibiliQrcodePollRes> getWebLoginQrcodeStatus(String qrcodeKey) {
    return webClient.get()
            .uri(urlconfig.getLoginStatus(), qrcodeKey)
            .retrieve()
            .bodyToMono(BilibiliQrcodePollRes.class);
    // ✅ 去掉.block()，前端会频繁轮询这个接口
}

// 适合非阻塞的场景：
// - 高并发（很多人同时访问）
// - 慢IO操作（网络请求、数据库查询慢）
// - 需要流式输出（像AI逐字显示）
```

## 5. WebFlux 是什么？

### 简单理解
**WebFlux** 就是让整个 Web 应用都支持非阻塞编程的框架。

```java
// 传统 Spring MVC - 阻塞式
@RestController
public class TraditionalController {
    @GetMapping("/data")
    public String getData() {
        return service.getData(); // 阻塞
    }
}

// Spring WebFlux - 非阻塞式  
@RestController
public class ReactiveController {
    @GetMapping("/data")
    public Mono<String> getData() {  // 返回 Mono
        return service.getDataReactive();
    }
    
    // 流式输出（Spring AI 用的就是这个！）
    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> getStream() {
        return Flux.interval(Duration.ofSeconds(1))
                  .map(i -> "数据块 " + i); // 每秒推送一个数据
    }
}
```

### WebFlux 的流式输出（重点！）
```java
// 这就是你在 Spring AI 看到的效果！
@GetMapping(value = "/ai/answer", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<String> getAIAnswer(String question) {
    return aiService.generateStream(question);
    // 前端效果：AI像打字机一样逐字输出
    // 第1秒: "你"
    // 第2秒: "你好"  
    // 第3秒: "你好，"
    // 第4秒: "你好，我"
    // ... 用户体验更好！
}
```

## 6. 实际使用指南

### 基础 WebClient 使用
```java
@Service
public class MyService {
    private final WebClient webClient;
    
    public MyService(WebClient webClient) {
        this.webClient = webClient;
    }
    
    // GET 请求
    public Mono<String> getData(String id) {
        return webClient.get()
                .uri("/api/data/{id}", id)
                .retrieve()
                .bodyToMono(String.class);
    }
    
    // POST 请求  
    public Mono<String> postData(User user) {
        return webClient.post()
                .uri("/api/users")
                .bodyValue(user)
                .retrieve()
                .bodyToMono(String.class);
    }
}
```

### Controller 中使用
```java
@RestController
public class MyController {
    
    // 阻塞方式 - 返回具体对象
    @GetMapping("/blocking")
    public MyData getDataBlocking() {
        return myService.getDataBlocking();
    }
    
    // 非阻塞方式 - 返回 Mono
    @GetMapping("/non-blocking") 
    public Mono<MyData> getDataNonBlocking() {
        return myService.getDataNonBlocking();
    }
}
```

## 7. 给新手的建议

### 学习路径：
1. **先掌握阻塞方式** - 理解基础概念
2. **再学习非阻塞** - 了解 Mono/Flux 基本操作
3. **最后学 WebFlux** - 构建完整响应式应用

### 实际选择：
```java
// 阶段1：学习期 → 用阻塞，简单易懂
public String simpleMethod() {
    return webClient.get()...bodyToMono(String.class).block();
}

// 阶段2：进阶期 → 尝试非阻塞  
public Mono<String> advancedMethod() {
    return webClient.get()...bodyToMono(String.class);
}

// 阶段3：生产期 → 根据实际情况选择
// - 高并发用非阻塞
// - 简单业务用阻塞
```

## 总结

**记住这几点：**
1. **WebClient** = 发HTTP请求的工具
2. **Mono/Flux** = 装异步结果的容器
3. **阻塞** = 线程傻等，简单但性能差
4. **非阻塞** = 线程聪明等，复杂但性能好
5. **WebFlux** = 让整个应用支持非阻塞的框架

**你的二维码项目建议：**
- 获取二维码：保持阻塞（需要立即显示）
- 查询状态：改为非阻塞（会被频繁调用）

这样既保证功能正常，又学习了两种编程模式！