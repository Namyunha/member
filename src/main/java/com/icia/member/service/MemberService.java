package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        System.out.println(memberEntity);
        memberRepository.save(memberEntity);
        System.out.println("회원가입성공");
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUdateEntity(memberDTO);
        System.out.println("service: memberUpdateEntity = " + memberEntity);
        memberRepository.save(memberEntity);
        System.out.println("수정성공");
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            System.out.println("있다");
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        } else {
            System.out.println("없다");
            return null;
        }
    }

    public void delete(Long id) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
        if (optionalMemberEntity.isPresent()) {
            System.out.println("삭제할 Entity 있음");
            MemberEntity memberEntity = optionalMemberEntity.get();
            System.out.println(memberEntity.getMemberName());
            memberRepository.delete(memberEntity);
        } else {
            System.out.println("삭제할 Entity 없음");
        }
    }

    public MemberDTO login(MemberDTO memberDTO) {
        System.out.println(memberDTO.getMemberEmail());
        System.out.println(memberDTO.getMemberPassword());
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if (optionalMemberEntity.isPresent()) {
            System.out.println("로그인 성공");
            MemberEntity memberEntity = optionalMemberEntity.get();
            return MemberDTO.toDTO(memberEntity);
        } else {
            System.out.println("로그인 실패");
            return null;
        }
    }

    public String findByEmail(String email) {
        System.out.println("service: email = " + email);
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(email);
        MemberEntity memberEntity = optionalMemberEntity.get();
        System.out.println("memberEntity = " + memberEntity.getMemberEmail());
        String memberEM = memberEntity.getMemberEmail();
        if (memberEM.equals(email)) {
            System.out.println("중복된 이메일이 있음");
            return null;
        } else {
            System.out.println("해당 이메일 사용가능");
            String memberEmail = memberEntity.getMemberEmail();
            return memberEmail;
        }
    }
}
