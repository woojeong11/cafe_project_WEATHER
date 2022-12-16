package com.example.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.dto.CustomUser;
import com.example.entity.Member;
import com.example.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

//컨트롤러역할을하는 securityconfig에서 서비스로 옴
//implements UserDetailsService 하고나서 오버라이드 함 

@Service
@Slf4j
public class CustomerDetailsService implements UserDetailsService{
    final String format = "SECURITY=> {}";
    @Autowired MemberMapper mMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // username가 오면 UserDetails 타입으로 반환한다
        //아이디가존재하는지? 확인 후에 UserDetails으로 변환해서리턴
        log.info(format,username);
        Member member = mMapper.selectoneMember(username);

        if(member != null && member.getBlock() == 1){ //member.getBlock() == 1 차단상태면 로그인불가 
            //권한 권한을컬렉션으로바꾼다
            //스트링으로넣으면 편한데 왜이렇게했냐면  컬렉션:여러개  이기때문에, 
            //한사람이 여러가지의 권한을 가질 수 있기때문에(권한여러개처리를위해)
            String[] strRole = {member.getRole()};
            
            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole); //변환
            
            //아이디,암호,권한을컬렉션타입으로변환 후 넘겨야 한다
            return new CustomUser(
             member.getUserid(), member.getUserpw(), role

                );
        }
        else {
            String[] strRole = {"_"};
            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole); //변환
            
            //아이디,암호,권한을컬렉션타입으로변환 후 넘겨야 한다
            return new User(username, "", role);
        }
    }
    
}
