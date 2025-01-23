package com.example.starter.service;

import com.example.starter.dto.ArticleForm;
import com.example.starter.entity.Article;
import com.example.starter.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service // (서비스 객체를 스프링 부트에 생성)
public class ArticleService {
    @Autowired // DI,
    private ArticleRepository articleRepository;


    public List<Article> index() {
        return (List<Article>)articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();
        if (article.getId() != null) {
            return null;
        }
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {
        Article article = dto.toEntity();
        Article target = articleRepository.findById(id).orElse(null);
        if (target == null || id != article.getId()) {
            return null;
        }

        return articleRepository.save(article);
    }

    public Article delete(Long id) {
        Article article = articleRepository.findById(id).orElse(null);
        if(article == null) {
            return null;
        }

        articleRepository.delete(article);
        return article;
    }

    @Transactional // 해당 메소드를 트랜잭션으로 묶는다.
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // Dtos --> Entities
        List<Article> articleList = dtos.stream()
                .map(dto -> dto.toEntity())
                .collect(Collectors.toList());

        // Save the database
        articleList.stream()
                .forEach(article -> articleRepository.save(article));

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(
                () -> new IllegalArgumentException("Fail !")
        );

        return articleList;
    }
}
