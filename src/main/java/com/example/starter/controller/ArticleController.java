package com.example.starter.controller;

import com.example.starter.dto.ArticleForm;
import com.example.starter.entity.Article;
import com.example.starter.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
public class ArticleController {

    @Autowired // 스프링 부트가 미리 생성해놓은 객체를 주입
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        // DTO --> Entity
        Article article = form.toEntity();
        log.info(article.toString());

        // Save an entity to database using Repository
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        // 상세 화면으로 리다이렉트
        return "redirect:/articles/" + saved.getId() ;
    }


    @GetMapping("/articles/{id}")
    public String showArticle(@PathVariable Long id, Model model) {
        log.info("showArticle Id is " + id);

        // 데이터를 가져온다.
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 데이터를 모델에 등록한다.
        model.addAttribute("article", articleEntity);

        // 페이지 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {
        // 데이터를 가져온다.
        List<Article> articles = (List<Article>)articleRepository.findAll();

        // 데이터를 모델에 등록한다.
        model.addAttribute("articles", articles);

        // 페이지 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String editArticle(@PathVariable Long id, Model model) {
        // 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);

        // 데이터를 모델에 등록한다.
        model.addAttribute("article", articleEntity);

        // 페이지 설정
        return "articles/edit";
    }

    @PostMapping("/articles/update")
    public String updateArticle(ArticleForm form) {
        System.out.println(form.toString());

        // DTO --> Entity
        Article article = form.toEntity();
        System.out.println(article.toString());

        // Save an entity to database using Repository
        Article articleEntity = articleRepository.findById(article.getId()).orElse(null);
        if (articleEntity != null) {
            Article saved = articleRepository.save(article);
            System.out.println(saved.toString());
        }

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")
    public String deleteArticle(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("delete !" + id);

        // 데이터 가져오기
        Article articleEntity = articleRepository.findById(id).orElse(null);
        log.info(articleEntity.toString());

        // 데이터 삭제 요청
        if (articleEntity != null) {
            articleRepository.delete(articleEntity);
            rttr.addFlashAttribute("message", "삭제가 완료되었습니다.");
        }

        // 페이지 설정
        return "redirect:/articles";
    }
}
