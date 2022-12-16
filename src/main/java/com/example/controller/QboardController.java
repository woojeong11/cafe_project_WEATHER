package com.example.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.Session;
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
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.QboardDTO;
import com.example.dto.QboardImageDTO;
import com.example.entity.Minfo;
import com.example.service.MemberService;
import com.example.service.qboard.QboardImageService;
import com.example.service.qboard.QboardService;


@Controller
@RequestMapping(value = "/qboard")
public class QboardController {
    @Autowired QboardService qService;
    @Autowired QboardImageService iService;
    @Autowired MemberService mService;

    // 127.0.0.1:8085/QOOT2/qboard/home.do
    @GetMapping(value = "/home.do")
    public String  homeGet(Model model,HttpServletRequest request,
    @RequestParam(name="page", defaultValue = "1") int page, 
    @RequestParam(name="text", defaultValue = "", required=false) String text,
    @RequestParam(name="select", defaultValue = "titlecontent", required=false) String select
    ){
        // System.out.println(select);
        // String sessionid = request.getRemoteAddr();
        HttpSession session = request.getSession();
        session.setAttribute("qboardhit", -1L);
        System.out.println(session.getAttribute("qboardhit"));

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
        System.out.println(total+"totla");
        model.addAttribute("total", total); 
        
        //페이지네이션
        model.addAttribute("pages", (total-1)/12+1); 

        return "qboard/home";
    }

    // 127.0.0.1:8085/QOOT2/qboard/insert.do
    @GetMapping(value = "/insert.do")
    public String  insertGet() {
        return "qboard/insert";
    }

    // 문의 등록하기
    // 127.0.0.1:8085/QOOT2/qboard/insert.do
    @PostMapping(value = "/insert.do")
    public String  insertPOST(@ModelAttribute QboardDTO help,HttpServletRequest request,
    @RequestParam(name = "file") MultipartFile[] file)throws Exception{
        // System.out.println("--------------------");
        // System.out.println(help.toString());
        // System.out.println("--------------------");
        Long no = qService.qboardSeq();
        help.setNo(no);

        String userid = (String)request.getAttribute("userid");
        help.setUserid(userid);

        qService.insertQboard(help);

        List<QboardImageDTO> list = new ArrayList<>();
        for(int i=0; i<file.length; i++){
            QboardImageDTO image = new QboardImageDTO();
            image.setQboardno(no);
            image.setImagetype(file[i].getContentType());
            image.setImagename(file[i].getOriginalFilename());
            image.setImagesize(file[i].getSize());
            image.setImagedata(file[i].getBytes());
            list.add(image);
            if(file[i].getSize()!=0){
                iService.insertImage(image);
            }
        }

        return "redirect:/qboard/home.do";
    }

    // 로그인안한사용자 셀렉원
    // 127.0.0.1:8085/QOOT2/qboard/selectone.do
    @GetMapping(value = "/guestselectone.do")
    public String guestGet(@RequestParam(name = "no")Long no,Model model,HttpServletRequest request){
        // String sessionid = request.getRemoteAddr();
        //     session.getAttribute("TOKEN"); 
        //     session.getAttribute("LOGIN");
        
        HttpSession session = request.getSession();
        System.out.println(session.getAttribute("qboardhit"));
        if( !session.getAttribute("qboardhit" ).equals(no) ){
            System.out.println("dddddddddddddddddddddddddddddddddddddddddd");
            qService.updatedHit(no); //조회수
            session.setAttribute("qboardhit", no);
        }

        QboardDTO board = qService.selectOne(no);

        List<QboardImageDTO> image = iService.selectimageList(no);
        model.addAttribute("image", image);

        // System.out.println(board.toString());
        model.addAttribute("board", board);
        return "qboard/selectone";
    }

    //로그인한사용자 셀렉원
    // 127.0.0.1:8085/QOOT2/qboard/selectone.do
    @GetMapping(value = "/selectone.do")
    public String oneGet(@RequestParam(name = "no")Long no,Model model,HttpServletRequest request){
        // String sessionid = request.getRemoteAddr();
        
        HttpSession session = request.getSession();

        System.out.println("selectone" + session.getAttribute("qboardhit"));

        if( !session.getAttribute("qboardhit" ).equals(no)){
            System.out.println("dddddddddddddddddddddddddddddddddddddddddd");
            qService.updatedHit(no); //조회수
            session.setAttribute("qboardhit", no);
        }

        String userid = (String)request.getAttribute("userid");
        System.out.println("userid"+userid);

        QboardDTO board = qService.selectOne(no);

        List<QboardImageDTO> image = iService.selectimageList(no);
        model.addAttribute("image", image);

        // System.out.println(board.toString());
        model.addAttribute("board", board);
        return "qboard/selectone";
    }

    // 127.0.0.1:8085/QOOT2/qboard/repwrite.do
    @GetMapping(value = "/repwrite.do")
    public String repGet(@RequestParam(name = "no")Long no,Model model) {
        // System.out.println(no);
        QboardDTO board = qService.selectOne(no);
        model.addAttribute("board", board);

        return "qboard/repwrite";
    }

    // 답글등록하기
    // 127.0.0.1:8085/QOOT2/qboard/insertrep.do
    @PostMapping(value = "/insertrep.do")
    public String insertrepPOST(@ModelAttribute QboardDTO board,HttpServletRequest request) {
        System.out.println("--------------------");
        System.out.println(board.toString());
        System.out.println("--------------------");
        qService.insertRep(board);
    
        return "redirect:/qboard/home.do";
    }

    //수정 화면 
    @GetMapping(value = "/update.do")
    public String updateget(@RequestParam(name = "no")Long no,Model model,HttpServletRequest request){
        // String sessionid = request.getRemoteAddr();
        // HttpSession session = request.getSession();
        //     session.getAttribute("TOKEN"); 
        //     session.getAttribute("LOGIN");
        QboardDTO board123 = qService.selectOne(no);
        List<QboardImageDTO> image = iService.selectimageList(no);
        
        // System.out.println(board123.toString());
        model.addAttribute("board123", board123);
        model.addAttribute("image", image);
      
        return "qboard/update";
    }

    @PostMapping(value="/update.do")
    public String updateQboard(@ModelAttribute QboardDTO board,@RequestParam(name = "no")Long no,
        @RequestParam(name = "file") MultipartFile[] file) throws Exception{
        // System.out.println(board.toString());

        QboardDTO board2 = qService.selectOne(no);
        board2.setTitle(board.getTitle());
        board2.setContent(board.getContent());
        board2.setPassword(board.getPassword());
          
        List<QboardImageDTO> list = new ArrayList<>();
        for(int i=0; i<file.length; i++){
            QboardImageDTO image = new QboardImageDTO();
            image.setQboardno(no);
            image.setImagetype(file[i].getContentType());
            image.setImagename(file[i].getOriginalFilename());
            image.setImagesize(file[i].getSize());
            image.setImagedata(file[i].getBytes());
            list.add(image);
            if(file[i].getSize()!=0){
                iService.insertImage(image);
            }
        }

        qService.updateQboard(board2);
        return "redirect:/qboard/home.do";
    }

    @PostMapping(value="/delete.do")
    public String deleteQboard(@RequestParam(name = "no")Long no) {
        // System.out.println(grp);

        //글번호로 이미지모두삭제
        iService.deleteQboardImage(no);
        //글삭제
        qService.deleteQboard(no);
        
        return "redirect:/qboard/home.do";
    }


}
