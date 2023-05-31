package com.icia.member.controller;

import com.icia.member.dto.MemberDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class HomeController {
    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        String dto = (String) session.getAttribute("loginDTO");
        model.addAttribute("memberEmail", dto);
        return "index";
    }
}
