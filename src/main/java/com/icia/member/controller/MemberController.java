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

//    @PostMapping("/save")
//    public String memberSaveParam(@ModelAttribute MemberDTO memberDTO) {
//        System.out.println("memberDTO = " + memberDTO);
//        memberService.save(memberDTO);
//        return "redirect:login";
//    }

    //    @PostMapping("/save")
//    public String save(@ModelAttribute MemberDTO memberDTO) {
//        memberService.save(memberDTO);
//        return "redirect:login";
//    }
    @PostMapping("/save")
    public ResponseEntity saveParam(@RequestBody MemberDTO memberDTO) {
        System.out.println("save: memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login")

    public String memberLogin() {
        return "memberPages/memberLogin";
    }

//    @PostMapping("login")
//    public String loginParam(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
//        System.out.println(memberDTO.getMemberEmail());
//        MemberDTO loginDTO = memberService.login(memberDTO);
//        if (loginDTO == null) {
//            return "redirect:login";
//        } else {
//            session.setAttribute("loginDTO", loginDTO.getMemberEmail());
//            return "redirect:/";
//        }
//    }

    @PostMapping("login")
    public String loginParam(@ModelAttribute MemberDTO memberDTO, HttpSession session) {
        System.out.println(memberDTO.getMemberEmail());
        boolean loginDTO = memberService.login2(memberDTO);
        if (loginDTO) {
            session.setAttribute("loginDTO", memberDTO.getMemberEmail());
            return "redirect:memberMain";
        } else {
            return "redirect:login";
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

    //    @PostMapping("/update")
//    public String updateParam(@ModelAttribute MemberDTO memberDTO) {
//        System.out.println("controller: memberDTO = " + memberDTO);
//        memberService.update(memberDTO);
//        return "redirect:list";
//    }

    @PostMapping("/update/{id}")
    public ResponseEntity updateParam(@RequestBody MemberDTO memberDTO, @PathVariable Long id) {
        System.out.println("memberDTO = " + memberDTO);
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/delete/{id}")
    public String deleteParam(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/list";
    }

    @GetMapping("/memberMain")
    public String memberMain(HttpSession session, Model model) {

        String loginEmail = (String) session.getAttribute("loginDTO");
        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
        model.addAttribute("memberDTO", memberDTO);
        return "memberPages/memberMain";
    }

    @PostMapping("/login/axios")
    public ResponseEntity memberLoginAxios(@RequestBody MemberDTO memberDTO, HttpSession session) throws Exception {
        memberService.loginAxios(memberDTO);
        session.setAttribute("loginEmail", memberDTO.getMemberEmail());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable Long id) throws Exception {
        System.out.println("axios: id = " + id);
        MemberDTO memberDTO = memberService.findById(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PostMapping("/emailCheck")
    public ResponseEntity duCheck(@RequestBody MemberDTO memberDTO) {
        String email = memberDTO.getMemberEmail();
        MemberDTO memberDTO2 = memberService.findByEmail(email);
        if (memberDTO2 == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}