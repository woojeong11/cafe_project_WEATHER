package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.QboardDTO;
import com.example.dto.QboardImageDTO;
import com.example.service.MemberService;
import com.example.service.qboard.QboardImageService;
import com.example.service.qboard.QboardService;

@Controller
@RequestMapping(value = "/admin/qboard")
public class AdminQboardController {
    @Autowired QboardService qService;
    @Autowired QboardImageService iService;
    @Autowired MemberService mService;
    
    // 127.0.0.1:8085/QOOT3/admin/qboard/home.do
    @GetMapping(value = "/home.do")
    public String  homeGet(Model model,HttpServletRequest request,
    @RequestParam(name="page", defaultValue = "1") int page, 
    @RequestParam(name="text", defaultValue = "", required=false) String text,
    @RequestParam(name="select", defaultValue = "titlecontent", required=false) String select
    ){
        // String sessionid = request.getRemoteAddr();
        System.out.println(page);
        System.out.println(text);
        System.out.println(select);
        HttpSession session = request.getSession();
        session.getAttribute("TOKEN"); 
        session.getAttribute("LOGIN");

        Map<String, Object> map = new HashMap<>();
        map.put("start", page*12-11);
        map.put("end", page*12);
        map.put("text", text);
        map.put("select", select);

        //문의 전체리스트
        List<QboardDTO> list = qService.qboardList(map);
        for(QboardDTO obj : list){
            obj.setNickname(mService.memberSelectone(obj.getUserid()).getNickname());
        }
        model.addAttribute("list", list); 
        //글 전체개수
        long total = qService.countBoardListLikePagenation(map);
            System.out.println(total);        
        model.addAttribute("total", total); 

        model.addAttribute("pages", (total-1)/12+1); 

        return "qboard/admin/home";
    }

    // 127.0.0.1:8085/QOOT3/qboard/selectone.do
    @GetMapping(value = "/selectone.do")
    public String oneGet(@RequestParam(name = "no")Long no,Model model,HttpServletRequest request){
        // String sessionid = request.getRemoteAddr();
        HttpSession session = request.getSession();
        //     session.getAttribute("TOKEN"); 
        //     session.getAttribute("LOGIN");
        
        qService.updatedHit(no); //조회수

        QboardDTO board = qService.selectOne(no);

        List<QboardImageDTO> image = iService.selectimageList(no);
        model.addAttribute("image", image);

        System.out.println(board.toString());
        model.addAttribute("board", board);
        return "qboard/admin/selectone";
    }

    // 127.0.0.1:8085/QOOT3/qboard/repwrite.do
    @GetMapping(value = "/repwrite.do")
    public String repGet(@RequestParam(name = "no")Long no,Model model) {
        // System.out.println(no);
        QboardDTO board = qService.selectOne(no);
        model.addAttribute("board", board);

        return "qboard/admin/repwrite";
    }

    // 답글등록하기
    // 127.0.0.1:8085/QOOT3/qboard/repwrite.do
    @PostMapping(value = "/repwrite.do")
    public String insertrepPOST(@ModelAttribute QboardDTO board,HttpServletRequest request) {
        
        String userid = (String)request.getAttribute("userid");
        board.setUserid(userid);
        // System.out.println("--------------------");
        // System.out.println(board.toString());
        // System.out.println("--------------------");

        qService.insertRep(board);
        
        return "redirect:/admin/qboard/home.do";
    }

    //글삭제
    @PostMapping(value="/delete.do")
    public String deleteQboard(@RequestParam(name = "no")Long no) {
        //글번호로 이미지모두삭제
        iService.deleteQboardImage(no);
        //글삭제
        qService.deleteQboard(no);
        return "redirect:/admin/qboard/home.do";
    }

}
