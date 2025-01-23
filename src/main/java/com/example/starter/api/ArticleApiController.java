package com.example.starter.api;

import com.example.starter.dto.ArticleForm;
import com.example.starter.entity.Article;
import com.example.starter.service.ArticleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class ArticleApiController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 주입
    private ArticleService articleService;

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello Spring !";
    }

    @GetMapping("/api/articles")
    public List<Article> getArticles() {
        return articleService.index();
    }

    @GetMapping("/api/articles/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleService.show(id);
    }

    @PostMapping("/api/articles")
    public ResponseEntity<Article> createArticle(@RequestBody ArticleForm dto) {
        Article created = articleService.create(dto);
        return (created != null)
                ? ResponseEntity.status(HttpStatus.OK).body(created)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article updated = articleService.update(id, dto);
        return (updated != null)
                ? ResponseEntity.status(HttpStatus.OK).body(updated)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article deleted = articleService.delete(id);
        return (deleted != null)
                ? ResponseEntity.status(HttpStatus.NO_CONTENT).build()
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @PostMapping("/api/articles/all")
    public ResponseEntity<List<Article>> createAllArticle(@RequestBody List<ArticleForm> dtos) {
        List<Article> created = articleService.createArticles(dtos);
        return (created != null)
                ? ResponseEntity.status(HttpStatus.OK).body(created)
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}