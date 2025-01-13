package com.example.starter.controller;

import com.example.starter.dto.ArticleForm;
import com.example.starter.entity.Article;
import com.example.starter.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 주입
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());

        // DTO --> Entity
        Article article = form.toEntity();
        System.out.println(article.toString());

        // Save an entity to database using Repository
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());

        return "";
    }
}
