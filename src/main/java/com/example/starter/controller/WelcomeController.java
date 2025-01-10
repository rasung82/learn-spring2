package com.example.starter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {

    @GetMapping("/greetings")
    public String niceToMeetYou(Model model) {
        model.addAttribute("userName", "우");
        return "greetings"; // templates/greeting.mustache
    }
}
