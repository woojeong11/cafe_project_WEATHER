package com.example.service;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.entity.Member;
import com.example.entity.Memberprofileimage;
import com.example.entity.Minfo;
import com.example.entity.Token;
import com.example.mapper.MemberMapper;
import com.example.repository.MemberRepository;
import com.example.repository.MinfoRepository;
import com.example.repository.ProfileImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService {

    final MemberRepository mRepository;

    final MinfoRepository  minfoRepository;

    final MemberMapper mMapper;

    final ProfileImageRepository proimage;


    // 회원 필수 정보 1개 selectone
    @Override
    public Member memberSelectone(String userid) {
        try {

            Member member = mMapper.selectoneMember(userid);
            if(member != null){
                
                return member;
            }
            return null;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 회원 필수 정보 upsert
    @Override
    public int upsertMember(Member member) {
        try {

            mMapper.upsertMember(member);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원 선택 정보 upsert
    @Override
    public int upsertMinfo(Minfo minfo) {
        try {

            mMapper.upsertMinfo(minfo);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 토큰 upsert
    @Override
    public int upsertToken(Token token) {
        try {

            mMapper.upsertToken(token);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 일반회원 필수 정보 등록
    @Override
    public int Insertmember(Member member) {
        try {
            
            mRepository.save(member);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    // 선택회원 정보 등록
    @Override
    public int Insertmemberinfo(Minfo minfo) {
        try {
            

            mMapper.insertMemberinfo(minfo);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    // 프로필 이미지 등록
    @Override
    public int insertprofileimage(Memberprofileimage image) {
        try {
            
            proimage.save(image);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
    // 프로필이미지 1개 조회
    @Override
    public Memberprofileimage selectoneprofileimage(Member member) {
        try {

            Memberprofileimage image = proimage.findByMember(member);

            return image;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 프로필 이미지 1개 수정
    @Override
    public int updateprofileimage(Memberprofileimage image) {
        try {
            
            Memberprofileimage profile = proimage.findById(image.getNo()).orElse(null);

            profile.setImagedata(image.getImagedata());
            profile.setImagename(image.getImagename());
            profile.setImagesize(image.getImagesize());
            profile.setImagetype(image.getImagetype());

            proimage.save(profile);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    // 회원 선택정보 조회
    @Override
    public Minfo memberinfoselectone(String userid) {
        try {

            Minfo minfo = mMapper.selectoneMemberinfo(userid);

            return minfo;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 회원 필수정보 수정
    @Override
    public int updateMember(Member member) {
        try {

            mMapper.UpdateMember(member);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원 선택입력정보 수정
    @Override
    public int updateMinfo(Minfo minfo) {
        try {

            mMapper.UpdateMinfo(minfo);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원 비밀번호 수정
    @Override
    public int updatePassword(Map<String, Object> map) {
        try {

            mMapper.UpdatePassword(map);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원 선택입력정보 탈퇴처리
    @Override
    public int deleteMinfo(String userid) {
        try {

            mMapper.deleteMinfo(userid);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // 회원 필수입력정보 탈퇴처리
    @Override
    public int updeleteMember(String userid) {
        try {

            mMapper.updeleteMember(userid);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    // 프로필이미지 삭제(회원탈퇴)
    @Override
    public int deleteProfileImage(String userid) {
        try {

            Member member = new Member();
            member.setUserid(userid);
            proimage.deleteByMember(member);

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    // idcheck
    @Override
    public int idcheck(String userid) {
        try {

            Member member = mMapper.selectoneMember(userid);
            System.out.println("---------------------------------------------service--------------------------------");
            System.out.println(member);
            System.out.println("---------------------------------------------service--------------------------------");
            if(member == null){
                return 0;
            }

            return 1;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public Member Email_Phone_selectMember(Map<String, Object> map) {
        try {

            Member member = mMapper.Email_Phone_selectMember(map);
            
            if(member != null){
                return member;
            }

            return null;
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 프로필url 수정
    @Override
    public int updateprofileurl(Map<String, Object> map) {
        try {
            return mMapper.updateprofileurl(map);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
    
}