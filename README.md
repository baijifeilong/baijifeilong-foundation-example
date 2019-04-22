# BaijifeilongFoundationExample

[BaijifeilongFoundation](https://github.com/baijifeilong/baijifeilong-foundation)使用示例

## 最佳实践步骤

### 1. 使用SpringBootCLI初始化项目

`spring init --groupId io.github.baijifeilong --artifactId baijifeilong-foundation-example --package-name io.github.baijifeilong.foundation.example --dependencies lombok --force --name "BaijifeilongFoundationExample" --description "Baijifeilong foundation example" baijifeilong-foundation-example`

### 2. 添加BaijifeilongFoundation依赖

```xml
<project>
    <dependencies>
        <dependency>
            <groupId>io.github.baijifeilong</groupId>
            <artifactId>baijifeilong-foundation</artifactId>
            <version>1.3.0.RELEASE</version>
        </dependency>
    </dependencies>

    <repositories>
        <repository>
            <id>jitpack.io</id>
            <url>https://jitpack.io</url>
        </repository>
    </repositories>
</project>
```

### 3. Hello Foundation

```java
package io.github.baijifeilong.foundation.example;

import io.github.baijifeilong.foundation.web.AbstractBaseController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@SpringBootApplication
@RestController
public class BaijifeilongFoundationExampleApplication extends AbstractBaseController {

    public static void main(String[] args) {
        SpringApplication.run(BaijifeilongFoundationExampleApplication.class, args);
    }

    @RequestMapping("/")
    public Object index() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            throw new RuntimeException("Dam it");
        }
        return successOf("Hey, lucky man");
    }
}
```

运行`main`函数启动此项目，访问根接口(`GET /`)，可以随机生成状态码`200`与`500`的两种响应。示例:

```bash
http get :8080/

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Date: Fri, 19 Apr 2019 07:26:55 GMT
Transfer-Encoding: chunked

{
    "data": "Hey, lucky man"
}

http get :8080/

HTTP/1.1 500
Connection: close
Content-Type: application/json;charset=UTF-8
Date: Fri, 19 Apr 2019 07:26:57 GMT
Transfer-Encoding: chunked

{
    "code": 10000,
    "message": "未知错误: Dam it"
}
```

可见，接口封装和全局异常处理都已生效

### 4. REST客户端演示

```java
package io.github.baijifeilong.foundation.example.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.collect.ImmutableMap;
import io.github.baijifeilong.foundation.http.RestHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019-04-19 15:57
 */
@org.springframework.web.bind.annotation.RestController
@Slf4j
public class RestController extends BaseController {

    private final RestHelper restHelper;

    public RestController(RestHelper restHelper) {
        this.restHelper = restHelper;
    }

    @RequestMapping("/rest")
    public Object doIt() {
        JsonNode jsonNode = restHelper.doGet("https://httpbin.org/get", ImmutableMap.of("hello", "world"));
        log.info("{}", jsonNode);
        return successOf(jsonNode);
    }
}
```

请求与响应示例:

```bash
http get :8080/rest

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Date: Fri, 19 Apr 2019 08:05:40 GMT
Transfer-Encoding: chunked

{
    "data": {
        "args": {
            "hello": "world"
        },
        "headers": {
            "Accept-Encoding": "gzip",
            "Host": "httpbin.org",
            "User-Agent": "okhttp/3.14.1"
        },
        "origin": "201.19.27.241, 201.19.27.241",
        "url": "https://httpbin.org/get?hello=world"
    }
}
```

### 5. 缓存服务演示

```java
package io.github.baijifeilong.foundation.example.controller;

import io.github.baijifeilong.foundation.cache.CacheHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019-04-19 16:00
 */
@RestController
public class CacheController extends BaseController {

    private final CacheHelper cacheHelper;

    public CacheController(CacheHelper cacheHelper) {
        this.cacheHelper = cacheHelper;
    }

    @RequestMapping("/cache")
    public Object service() {
        Point point = cacheHelper.takeOrPutWithTimeOut("point", () -> new Point(11, 11), 1, TimeUnit.MINUTES);
        return successOf(point);
    }
}
```

请求与响应示例:

```bash
http get :8080/cache

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Date: Fri, 19 Apr 2019 08:05:56 GMT
Transfer-Encoding: chunked

{
    "data": {
        "x": 11.0,
        "y": 11.0
    }
}
```

## 协议

GPL-3.0