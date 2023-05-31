package com.icia.member.controller;


import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/save")
    public String memberSave() {
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String memberSaveParam(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "redirect:login";
    }

    @GetMapping("/login")
    public String memberLogin() {
        return "memberPages/memberLogin";
    }

    @GetMapping("/list")
    public String memberList(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("list", memberDTOList);
        return "memberPages/memberList";
    }

    @GetMapping("/{id}")
    public String memberDetail(@PathVariable Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        System.out.println("memberDTO = " + memberDTO);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberDetail";
    }

    @GetMapping("/update/{id}")
    public String memberUpdate(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        System.out.println("memberDTO = " + memberDTO);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
    }

    @PostMapping("/update")
    public String updateParam(@ModelAttribute MemberDTO memberDTO){
        System.out.println("controller: memberDTO = " + memberDTO);
        memberService.update(memberDTO);
        return "redirect:list";
    }
}
