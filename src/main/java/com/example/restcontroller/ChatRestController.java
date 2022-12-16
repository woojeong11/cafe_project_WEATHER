package com.example.restcontroller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Chat;
import com.example.entity.Member;
import com.example.repository.chat.ChatRepository;

@RestController
@RequestMapping(value = "/rest/chat")
public class ChatRestController {
    
    @Autowired
    ChatRepository chatRepository;

    // http://127.0.0.1:8085/TEST202/rest/chat/insert.json
    @PostMapping(value = "/insert.json")
    public String insertPOST(
        @RequestParam(name = "content") String content,
        @RequestParam(name = "userid") String userid,
        @RequestParam(name = "roomid") String roomid
    ){

        System.out.println( content );
        System.out.println( userid );

        Chat chat = new Chat();
        chat.setContent(content);
        chat.setRoomid(roomid.substring(1, roomid.length()-1));
        chat.setUserid(userid);

        chatRepository.save(chat);
        
        return "200";
    }
}
