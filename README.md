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
            <version>1.2.1.RELEASE</version>
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
bj@localhost$ http get :8080/
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Date: Fri, 19 Apr 2019 07:26:55 GMT
Transfer-Encoding: chunked

{
    "data": "Hey, lucky man"
}

bj@localhost$ http get :8080/
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

### 4. 处理404异常

```yaml
spring:
  resources:
    add-mappings: false
  mvc:
    throw-exception-if-no-handler-found: true
```

## 协议

GPL-3.0