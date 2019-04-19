package io.github.baijifeilong.foundation.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by BaiJiFeiLong@gmail.com at 2019-04-19 13:07
 */
@RestController
public class HomeController extends BaseController {

    @RequestMapping("/")
    public Object index() {
        if (ThreadLocalRandom.current().nextBoolean()) {
            throw new RuntimeException("Dam it");
        }
        return successOf("Hey, lucky man");
    }
}
