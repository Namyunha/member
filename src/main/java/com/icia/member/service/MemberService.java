package com.icia.member.service;

import com.icia.member.dto.MemberDTO;
import com.icia.member.entity.MemberEntity;
import com.icia.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

//    public void save(MemberDTO memberDTO) {
//        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
//        System.out.println(memberEntity);
//        memberRepository.save(memberEntity);
//        System.out.println("회원가입성공");
//    }


    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        return memberRepository.save(memberEntity).getId();
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


//    public MemberDTO findById(Long id) {
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
//        if (optionalMemberEntity.isPresent()) {
//            System.out.println("있다");
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
//            return memberDTO;
//        } else {
//            System.out.println("없다");
//            return null;
//        }
//    }


    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(()-> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
//        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            return MemberDTO.toDTO(memberEntity);
//        } else {
//            return null;
//        }
    }


//    내꺼
//    public void delete(Long id) {
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
//        if (optionalMemberEntity.isPresent()) {
//            System.out.println("삭제할 Entity 있음");
//            MemberEntity memberEntity = optionalMemberEntity.get();
//            System.out.println(memberEntity.getMemberName());
//            memberRepository.delete(memberEntity);
//        } else {
//            System.out.println("삭제할 Entity 없음");
//        }
//    }


    //    선생님꺼
    public void delete(Long id) {
        memberRepository.deleteById(id);
    }


    //    내꺼
    public MemberDTO login(MemberDTO memberDTO) {
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

    //    선생님꺼
    public boolean login2(MemberDTO memberDTO) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if (optionalMemberEntity.isPresent()) {
            return true;
        } else {
            return false;
        }
    }


    public MemberDTO findByEmail(String loginEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(loginEmail);
        if (optionalMemberEntity.isPresent()) {
            System.out.println("이메일 있다");
            MemberEntity memberEntity = optionalMemberEntity.get();
            MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
            return memberDTO;
        } else {
            System.out.println("이메일 없다");
            return null;
        }
    }

    public void loginAxios(MemberDTO memberDTO) {
//        chaining method (체이닝 메서드)
        memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                .orElseThrow(() -> new NoSuchElementException("이메일 또는 비밀번호가 틀립니다"));
//                Optional 객체에 값이 없을 때, 예외를 만들어주고 그상황에서 뭔가를 나타냄
    }


}
