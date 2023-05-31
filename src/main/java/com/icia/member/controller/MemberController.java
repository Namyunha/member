package com.icia.member.controller;


import com.icia.member.dto.MemberDTO;
import com.icia.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
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

    @PostMapping("login")
    public String loginParam(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        System.out.println(memberDTO.getMemberEmail());

        MemberDTO loginDTO = memberService.login(memberDTO);
        if (loginDTO == null) {
            return "redirect:login";
        } else {
            session.setAttribute("loginDTO", loginDTO.getMemberEmail());
            return "redirect:/";
        }
    }

    @GetMapping("/list")
    public String memberList(Model model) {
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("list", memberDTOList);
        return "memberPages/memberList";
    }


    @GetMapping("/{id}")
    public String memberDetail(@PathVariable Long id, Model model) {
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
    public String updateParam(@ModelAttribute MemberDTO memberDTO) {
        System.out.println("controller: memberDTO = " + memberDTO);
        memberService.update(memberDTO);
        return "redirect:list";
    }

    @GetMapping("/delete/{id}")
    public String deleteParam(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/list";
    }

    @PostMapping("/emailCheck")
    public ResponseEntity emailCheck(@RequestParam("memberEmail") String email) {
        System.out.println("email: " + email);
        String memberEmail = memberService.findByEmail(email);
        System.out.println("memberEmail: " + memberEmail);
        if (memberEmail == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}