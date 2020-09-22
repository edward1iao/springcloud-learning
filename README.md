# SpringCloud学习

## 项目介绍

基于macrozheng教程项目进行SpringCloud学习 https://github.com/macrozheng/springcloud-learning

## SpringCloud版本

| SpringCloud Version | SpringBoot Version |
| ------------------- | ------------------ |
| Hoxton              | 2.2.x              |
| Greenwich           | 2.1.x              |
| Finchley            | 2.0.x              |
| Edgware             | 1.5.x              |
| Dalston             | 1.5.x              |

| Component              | Edgware.SR6    | Greenwich.SR2 |
| ---------------------- | -------------- | ------------- |
| spring-cloud-bus       | 1.3.4.RELEASE  | 2.1.2.RELEASE |
| spring-cloud-commons   | 1.3.6.RELEASE  | 2.1.2.RELEASE |
| spring-cloud-config    | 1.4.7.RELEASE  | 2.1.3.RELEASE |
| spring-cloud-netflix   | 1.4.7.RELEASE  | 2.1.2.RELEASE |
| spring-cloud-security  | 1.2.4.RELEASE  | 2.1.3.RELEASE |
| spring-cloud-consul    | 1.3.6.RELEASE  | 2.1.2.RELEASE |
| spring-cloud-sleuth    | 1.3.6.RELEASE  | 2.1.1.RELEASE |
| spring-cloud-stream    | Ditmars.SR5    | Fishtown.SR3  |
| spring-cloud-zookeeper | 1.2.3.RELEASE  | 2.1.2.RELEASE |
| spring-boot            | 1.5.21.RELEASE | 2.1.5.RELEASE |
| spring-cloud-task      | 1.2.4.RELEASE  | 2.1.2.RELEASE |
| spring-cloud-gateway   | 1.0.3.RELEASE  | 2.1.2.RELEASE |
| spring-cloud-openfeign | 暂无           | 2.1.2.RELEASE |

## 项目依赖管理

```xml
<properties>
    <java.version>1.8</java.version>
    <spring-boot.version>2.3.3.RELEASE</spring-boot.version>
    <spring-cloud.version>Hoxton.SR8</spring-cloud.version>
</properties>

<dependencyManagement>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>${spring-boot.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>${spring-cloud.version}</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
</dependencyManagement>
```



## SpringCloud Netflix

### Eureka （服务治理）

服务治理组件，包括服务端的注册中心和客户端的服务发现机制

#### 项目相关

```
eureka-server 服务器 通过server1、server2配置文件启动
eureka-client 客户端 通过server1、server2配置文件启动
eureka-security-server 带安全验证的服务器
```

#### 依赖

##### 服务器依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
</dependency>

<!--开启注册认证需要以下依赖：注意引入该依赖则默认开启安全认证 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

##### 客户端依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```



#### 注意点

eureka.client.service-url.defaultZone 服务注册地址

@EnableEurekaServer 开启eureka服务器功能

@EnableDiscoveryClient 开启eureka客户端发现功能

各服务通过spring.application.name配置注册到eureka服务器

eureka服务器集群需要注册自身，单机则不需要配置

eureka.client.fetch-registry #指定是否要从注册中心获取服务，默认true，服务器需设置false

eureka.client.register-with-eureka #指定是否要注册到注册中心，默认true，服务器需设置false

eureka服务器本地测试需要通过host设置不同域名，不然会识别为同一个服务器

eureka普通注册与安全验证注册可以并存

eureka服务端引入安全认证依赖需要同时引入hystrix的依赖，不然会报错，同时服务端如果引入了安全认证依赖，则默认开起了安全认证

eureka安全认证服务端需要配置spring.security.user.name|password,eureka安全认证客户端认证地址则为http://[name]:[password]@host:port/eureka/





### Ribbon (负载均衡)

负载均衡的服务调用组件，具有多种负载均衡调用策略(通过修改依赖注入的IRule实现)；

#### 项目相关

```
eureka-server 服务器 通过server1、server2配置文件启动
eureka-security-server 带安全验证的服务器
data-service 数据服务 通过server1、server2配置文件启动
ribbon-service 负载均衡服务
```

#### 依赖

##### 服务器依赖（同上服务器依赖）

##### 数据服务依赖（同上客户端依赖）

##### 负载均衡服务依赖

spring-cloud-starter-netflix-eureka-client依赖中关联了spring-cloud-starter-netflix-ribbon依赖，所以这里不需要重复引用，即默认eureka客户端即有负载均衡功能，只是未开启。

#### 注意点

@LoadBalanced 通过该注解使注入的RestTemplate具有负载均衡的功能，通过修改注入的IRule修改负载均衡的策略，默认为轮询访问服务。

常用负载均衡有 轮询、随机、权重等，可通过查询IRule实现类查看，也可以基于IRule实现自己的负载均衡，通过声明注入到服务中。





### Hystrix (**服务容错保护:服务降级、服务熔断、线程隔离、请求缓存、请求合并及服务监控**)

服务容错组件，实现了断路器模式，为依赖服务的出错和延迟提供了容错能力；

在微服务架构中，服务与服务之间通过远程调用的方式进行通信，一旦某个被调用的服务发生了故障，其依赖服务也会发生故障，此时就会发生故障的蔓延，最终导致系统瘫痪。Hystrix实现了断路器模式，当某个服务发生故障时，通过断路器的监控，给调用方返回一个错误响应，而不是长时间的等待，这样就不会使得调用方由于长时间得不到响应而占用线程，从而防止故障的蔓延。Hystrix具备**服务降级、服务熔断、线程隔离、请求缓存、请求合并及服务监控**等强大功能。

Hystrix提供了Hystrix Dashboard来实时监控HystrixCommand方法的执行情况。 Hystrix Dashboard可以有效地反映出每个Hystrix实例的运行情况，帮助我们快速发现系统中的问题，从而采取对应措施。

#### 项目相关

```
#服务容错保护相关
eureka-server 服务器 通过server1、server2配置文件启动
eureka-security-server 带安全验证的服务器
data-service 数据服务 通过server1、server2配置文件启动
hystrix-service 断路器服务 通过server1、server2配置文件启动
#监控相关
turbine-service 断路器集群流聚合服务
hystrix-dashboard 断路器面板（服务监控）
```

#### 依赖

##### 服务器依赖（同上服务器依赖）

##### 数据服务依赖（同上数据服务依赖）

##### 断路器服务依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

##### 断路器集群流聚合服务依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-turbine</artifactId>
</dependency>
```

##### 断路器面板依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix-dashboard</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### 注意点

@EnableCircuitBreaker 用来开启服务熔断功能

@SpringCloudApplication 复合注解，相当于@SpringBootApplication+@EnableDiscoveryClient+@EnableCircuitBreaker =开启ribbon以及hystrix的客户端

@HystrixCommand 用来实现hystrix各种功能，包括服务监控也必须是声明了该注解的方法才能被补获到，通过注解内各属性，可以实现hystrix各功能

在使用hystrix实现缓存过程中，我们需要在每次使用缓存的请求前后对HystrixRequestContext进行初始化和关闭,不然会抛异常，hystrix-service.com.edward1iao.springcloud.hystrixservice.filter.HystrixRequestContextFilter通过注册过滤器回调函数对HystrixRequestContext进行初始化。

使用hystrix dashboard监控Hystrix实例的运行情况需要在对应服务中注册UrlMapping(/actuator/hystrix.stream) ，用来暴露监控端点，如下

```java
/**
 * hystrix dashboard面板监控时才需要以下配置
 * 暴露hystrix监控端点
 * 如果不配置会导致监控面板无数据且出现Unable to connect to Command Metric Stream
 * @return
 */
@Bean
public ServletRegistrationBean servletRegistrationBean(){
    ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new HystrixMetricsStreamServlet());
    servletRegistrationBean.addUrlMappings("/actuator/hystrix.stream");
    return servletRegistrationBean;
}
```

@EnableTurbine 用来聚合hystrix-service服务的监控信息，使hystrix dashboard能监控到集群服务

@EnableHystrixDashboard 开启断路器面板功能

断路器面板需配置hystrix.dashboard.proxy-stream-allow-list: "*"  #用于防止代理流不在被允许范围 http://localhost:8401/actuator/hystrix.stream is not in the allowed list of proxy host names. If it should be allowed add it to hystrix.dashboard.proxyStreamAllowList.





### Feign  (声明式服务调用)

基于Ribbon和Hystrix的声明式服务调用组件,具备两者功能；

Feign是声明式的服务调用工具，我们只需创建一个接口并用注解的方式来配置它，就可以实现对某个服务接口的调用，简化了直接使用RestTemplate来调用服务接口的开发量。Feign具备可插拔的注解支持，同时支持Feign注解、JAX-RS注解及SpringMvc注解。当使用Feign时，Spring Cloud集成了Ribbon和Eureka以提供负载均衡的服务调用及基于Hystrix的服务容错保护功能。

#### 项目相关

```
eureka-server 服务器 通过server1、server2配置文件启动
eureka-security-server 带安全验证的服务器
data-service 数据服务 通过server1、server2配置文件启动
feign-service feign服务
```

#### 依赖

##### 服务器依赖（同上服务器依赖）

##### 数据服务依赖（同上数据服务依赖）

##### feign服务依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

#### 注意点

@EnableFeignClients 开启feign功能，通过编写接口，声明@FeignClient，value对应服务名称，实现RestTemplate的调用方式

feign实现ribbon功能可通过@FeignClient注解实现，hystrix功能与之前一致，不过实现hystrix功能需要在配置中开启，默认是关闭的，feign.hystrix.enabled=true 

feign可以对请求进行压缩，通过在配置中配置开关实现gzip压缩功能



### Zuul (服务网关【路由与过滤】)

API网关组件，对请求提供路由及过滤功能

API网关为微服务架构中的服务提供了统一的访问入口，客户端通过API网关访问相关服务。API网关的定义类似于设计模式中的门面模式，它相当于整个微服务架构中的门面，所有客户端的访问都通过它来进行路由及过滤。它实现了请求路由、负载均衡、校验过滤、服务容错、服务聚合等功能。

Zuul自动集成了Ribbon和Hystrix，所以Zuul天生就有负载均衡和服务容错能力，我们可以通过Ribbon和Hystrix的配置来配置Zuul中的相应功能

#### 项目相关

```
eureka-server 服务器 通过server1、server2配置文件启动
eureka-security-server 带安全验证的服务器
data-service 数据服务 通过server1、server2配置文件启动
feign-service feign服务
zuul-service 网关服务
```

#### 依赖

##### 服务器依赖（同上服务器依赖）

##### 数据服务依赖（同上数据服务依赖）

##### feign服务依赖（同上feign服务依赖）

##### 网关服务依赖

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
<!--zuul 依赖了spring-boot-starter-web-->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
</dependency>
<!-- 非必须：通过SpringBoot Actuator来查看Zuul中的路由信息 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-actuator</artifactId>
</dependency>
```

#### 注意点

@EnableZuulProxy 开启zuul网关功能，默认以应用名称作为服务请求路由，可以自行配置zuul.routes:xxx:path:/xx/**，但是默认路由不会覆盖，不需要的路由通过配置zuul.ignored-services:xxxx,xxxx 关闭对应的路由，

zuul.prefix配置网关路由前缀

zuul.sensitive-headers:Cookie,Set-Cookie,Authorization 默认配置的过滤敏感请求头信息，如果需要可以将该项设置为空

Zuul在请求路由时，不会设置最初的host头信息，可以设置zuul.add-host-header=true来添加host请求头

引入actuator依赖后配置management.endpoints.web.exposure.include='routes'，通过/acatuator/routes查看路由的端点,通过/actuator/routes/details查看详细信息

自定义组件继承zuulFilter，通过设置filterType类型（过滤器类型，有pre、routing、post、error四种），可以实现自定义过滤器





## SpringCloud Config (配置管理)

集中配置管理工具，分布式系统中统一的外部配置管理，默认使用Git来存储配置，可以支持客户端配置的刷新及加密、解密操作。

## SpringCloud Bus (消息总线)

用于传播集群状态变化的消息总线，使用轻量级消息代理链接分布式系统中的节点，可以用来动态刷新集群中的服务配置。



## 其他

### RestTemplate

RestTemplate是一个HTTP客户端，使用它我们可以方便的调用HTTP接口，支持GET、POST、PUT、DELETE等方法。

```java
org.springframework.web.client.RestTemplate;
```

