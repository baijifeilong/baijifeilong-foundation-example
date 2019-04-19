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
