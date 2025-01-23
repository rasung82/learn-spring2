package com.example.starter.service;

import com.example.starter.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링과 연동되어 테스팅된다.
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 예상
        Article a1 = new Article(1L, "홍길동", "안녕하세요.");
        Article a2 = new Article(2L, "김철수", "안녕하세요.");
        Article a3 = new Article(3L, "이영희", "안녕하세요.");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a1,a2,a3));

        // 실제
        List<Article> articles = articleService.index();

        // 비교
        assertEquals(expected.toString(), articles.toString());
    }
}