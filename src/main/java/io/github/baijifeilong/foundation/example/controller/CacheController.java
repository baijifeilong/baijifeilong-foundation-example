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
