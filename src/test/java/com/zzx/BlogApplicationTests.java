package com.zzx;

import com.zzx.dao.BlogDao;
import com.zzx.pojo.Blog;
import com.zzx.service.BlogService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private BlogService blogService;

    @Autowired
    private BlogDao blogDao;

    @Test
    void contextLoads() {


    }

}
