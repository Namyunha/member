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
    public ResponseEntity saveParam(@RequestBody MemberDTO memberDTO) {
        System.out.println("save: memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public String memberDetail(@PathVariable Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        System.out.println("memberDTO = " + memberDTO);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberDetail";
    }

    @PutMapping("/{id}")
    public ResponseEntity putUpdate(@RequestBody MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/login")
    public String memberLogin(@RequestParam(value = "redirectURI", defaultValue = "/member/mypage") String redirectURI, Model model) {
        model.addAttribute("redirectURI", redirectURI);
        return "memberPages/memberLogin";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "memberPages/memberMain";
    }

//    @PostMapping("/login/axios")
//    public ResponseEntity memberLoginAxios(@RequestBody MemberDTO memberDTO, HttpSession session) throws Exception {
//        System.out.println("MemberDTO: " + memberDTO);
//        memberService.loginAxios(memberDTO);
//
//        session.setAttribute("loginEmail", memberDTO.getMemberEmail());
//        return new ResponseEntity<>(HttpStatus.OK);
//    }


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
    public String loginParam(@ModelAttribute MemberDTO memberDTO, HttpSession session, @RequestParam("redirectURI") String redirectURI) {
        System.out.println(memberDTO.getMemberEmail());
        boolean loginDTO = memberService.login2(memberDTO);
        if (loginDTO) {
            session.setAttribute("loginDTO", memberDTO.getMemberEmail());
//            return "redirect:memberMain";
//            로그인 성공하면 사용자가 직전에 요청한 주소로 redirect
//            인터셉터에 걸리지 않고 처음부터 로그인하는 사용자였다면
//            redirect: /member/mypage로 요청되며, memberMain 화면으로 전환됨.
            return "redirect:" + redirectURI;
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

    @GetMapping("/update")
    public String memberUpdate(HttpSession session, Model model) {
        String loginDTO = (String) session.getAttribute("loginEmail");
        System.out.println("update: loginDTO" + loginDTO);
        MemberDTO memberDTO = memberService.findByEmail(loginDTO);
        System.out.println("update: memberDTO = " + memberDTO);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
    }

    //    @PostMapping("/update")
//    public ResponseEntity updateParam(@RequestBody MemberDTO memberDTO) {
//        System.out.println("memberDTO = " + memberDTO);
//        memberService.update(memberDTO);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
    //    @PostMapping("/update")
//    public String updateParam(@ModelAttribute MemberDTO memberDTO) {
//        System.out.println("controller: memberDTO = " + memberDTO);
//        memberService.update(memberDTO);
//        return "redirect:list";
//    }
    @GetMapping("/delete/{id}")
    public String deleteParam(@PathVariable Long id) {
        memberService.delete(id);
        return "redirect:/member/list";
    }

//    @GetMapping("/memberMain")
//    public String memberMain(HttpSession session, Model model) {
//        String loginEmail = (String) session.getAttribute("loginDTO");
//        MemberDTO memberDTO = memberService.findByEmail(loginEmail);
//        model.addAttribute("memberDTO", memberDTO);
//        return "memberPages/memberMain";
//    }


    @GetMapping("/axios/{id}")
    public ResponseEntity detailAxios(@PathVariable Long id) throws Exception {
        System.out.println("axios: id = " + id);
        MemberDTO memberDTO = memberService.findById(id);
        return new ResponseEntity<>(memberDTO, HttpStatus.OK);
    }

    @PostMapping("/dup-check")
    public ResponseEntity duCheck(@RequestBody MemberDTO memberDTO) {
        MemberDTO memberDTO2 = memberService.findByEmail(memberDTO.getMemberEmail());
        if (memberDTO2 == null) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


}