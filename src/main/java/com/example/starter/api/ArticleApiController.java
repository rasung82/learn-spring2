package com.example.starter.api;

import com.example.starter.dto.ArticleForm;
import com.example.starter.entity.Article;
import com.example.starter.repository.ArticleRepository;
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
    private ArticleRepository articleRepository;

    @GetMapping("/api/hello")
    public String hello() {
        return "Hello Spring !";
    }

    @GetMapping("/api/articles")
    public List<Article> getArticles() {
        return (List<Article>) articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article getArticle(@PathVariable Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    @PostMapping("/api/articles")
    public Article createArticle(@RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }

    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable Long id, @RequestBody ArticleForm dto) {
        Article article = dto.toEntity();
        log.info("{}, {}", id, article.toString());

        Article target = articleRepository.findById(id).orElse(null);
        if (target == null || id != article.getId()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        Article updated = articleRepository.save(article);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }

        articleRepository.delete(article);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}