package com.example.repository;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Chat;
import com.example.entity.ChatRoom;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, String> {
    
    public ChatRoom findByRoomid(String roomid);

    // 카테고리 기반 조회
    public List<ChatRoom> findByroomidContaining(String userid, PageRequest pageable);

    // 카테고리 기반 개수
    public int countByroomidContaining( String userid);
}
