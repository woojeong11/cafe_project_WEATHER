package com.example.controller.chat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.entity.Chat;
import com.example.entity.ChatRoom;
import com.example.entity.Member;
import com.example.repository.ChatRoomRepository;
import com.example.repository.chat.ChatRepository;
import com.example.service.MemberService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/chat")
@Slf4j
@RequiredArgsConstructor
public class ChatRoomController {
    

    private final ChatRoomRepository chatRoomRepository;

    private final ChatRepository chatRepository;

    private final MemberService mService;

    //채팅방 목록 조회
    // http://127.0.0.1:8085/TEST202/chat/admin/rooms
    @GetMapping(value = "/admin/rooms")
    public ModelAndView rooms(
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "userid", defaultValue = "") String userid
    ){

        DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 페이지 네이션 설정( 0부터 )
        PageRequest pageRequest = PageRequest.of(page-1, 10);

        List<ChatRoom> list = chatRoomRepository.findByroomidContaining(userid, pageRequest);
        for(ChatRoom obj : list){
            
            obj.setDate(obj.getRegdate().toString().substring(0, 10));
        }

        int pages = chatRoomRepository.countByroomidContaining(userid);

        ModelAndView mv = new ModelAndView("chat/rooms");

        mv.addObject("list", list);
        mv.addObject("pages", (pages-1)/10 + 1);

        return mv;
    }

    // 채팅방 개설(조회 후 존재한다면, 원래 방으로 이동)
    // http://127.0.0.1:8085/TEST202/chat/room
    @PostMapping(value = "/room")
    public String create( 
        RedirectAttributes rttr,
        HttpServletRequest request
    ){
        if(!chatRoomRepository.findById(request.getAttribute("userid").toString()).isPresent()){
            ChatRoom model = new ChatRoom();
            model.setRoomid(request.getAttribute("userid").toString());
            chatRoomRepository.save(model);
            rttr.addFlashAttribute("roomName", model);
            return "redirect:/chat/room?roomid=" + request.getAttribute("userid").toString() ;
        }
        else{
            return "redirect:/chat/room?roomid=" + request.getAttribute("userid").toString() ;
        }

    }

    //채팅방 조회
    // http://127.0.0.1:8085/TEST202/chat/room?roomid=?
    @GetMapping("/room")
    public void getRoom(
        String roomid,
        Model model,
        HttpServletRequest request
    ){

        System.out.println( chatRepository.findByRoomid(roomid));
        System.out.println( chatRoomRepository.findByRoomid(roomid)); 
        System.out.println( mService.memberSelectone(request.getAttribute("userid").toString()));

        model.addAttribute("room", chatRoomRepository.findByRoomid(roomid));
        model.addAttribute("chat", chatRepository.findByRoomid(roomid));
        model.addAttribute("member", mService.memberSelectone(request.getAttribute("userid").toString()));
    }

    // 채팅방 조회(관리자 입장)
    // http://127.0.0.1:8085/TEST202/chat/admin/room?roomid=?
    @GetMapping("/admin/room")
    public String getRoomByADMIN(
        String roomid,
        Model model,
        HttpServletRequest request
    ){

        System.out.println( chatRepository.findByRoomid(roomid));

        model.addAttribute("room", chatRoomRepository.findByRoomid(roomid));
        model.addAttribute("chat", chatRepository.findByRoomid(roomid));
        model.addAttribute("member", mService.memberSelectone(request.getAttribute("userid").toString()));

        return "chat/adminroom";
    }
}
