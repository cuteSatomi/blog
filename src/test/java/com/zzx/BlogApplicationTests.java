package com.zzx;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class BlogApplicationTests {

    @Test
    void contextLoads() {

        String str = "1";
        List list = new ArrayList<Long>();
        for (String tagId : str.split(",")) {
            list.add(Long.valueOf(tagId));
        }

        System.out.println(list);

    }

}
