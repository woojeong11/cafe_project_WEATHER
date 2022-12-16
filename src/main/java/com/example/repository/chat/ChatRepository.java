package com.example.repository.chat;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.entity.Chat;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
    
    public List<Chat> findByRoomid(String roomid);
}
