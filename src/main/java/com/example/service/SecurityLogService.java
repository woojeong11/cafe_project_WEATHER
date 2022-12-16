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

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SecurityLogService implements UserDetailsService {
    
    final String format  = "SECURITY => {}";

    @Autowired
    MemberService mService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        System.out.println("security---------------------username---------------");
        log.info(format, username);
        System.out.println("security---------------------username---------------");

        // 아이디가 전송되면 UserDetails 타입으로 변환해서 리턴

        Member member = mService.memberSelectone(username);
        
        System.out.println("security---------------------member---------------");
        log.info(format, member);
        System.out.println("security---------------------member---------------");

        // 차단(block) 당한 사람은 접속 못하게 하는
        
        if(member != null && member.getBlock() != 0){
            System.out.println("A");
            // 한 사람이 권한을 여러 개 가질 수도 있으므로, String이 아닌 컬렉션으로 처리
            
            String[] strRole = { member.getRole() };
            
            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);

            CustomUser user = new CustomUser(member.getUserid(), member.getUserpw(), role, member.getPhone());
            System.out.println(user.toString());
            
            log.info(format, user); 

            return user;
        }
        else{
            System.out.println("B");
            String[] strRole = {"_"};

            Collection<GrantedAuthority> role = AuthorityUtils.createAuthorityList(strRole);

            return new User(username, "_", role);
        }

    }
}
