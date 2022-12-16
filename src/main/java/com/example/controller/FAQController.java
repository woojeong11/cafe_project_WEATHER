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

import com.example.dto.FAQDTO;
import com.example.service.FAQService;

@Controller//컨트롤러 선언
@RequestMapping(value = "/faq")//원래 여기서 리퀘스트맵핑으로 주소설정하는부분인데 오류나서 일단 안함
public class FAQController {
    
    @Autowired
    FAQService faqService;

    //등록 GET
    // 127.0.0.1:8085/QOOT3/insert.do 
    @GetMapping(value = "/insert.do")
    public String  insertGet() { 

        return "FAQ/insert";
    }

    //등록 POST
    //127.0.0.1:8085/QOOT3/insert.do
    @PostMapping(value="/insert.do")
    public String insertPost(@ModelAttribute FAQDTO faqdto, Model model) {

        // System.out.println("===================================");
        // System.out.println(faqdto.toString());
        // System.out.println("===================================");

        faqService.insertFAQ(faqdto);
        
        return "redirect:/faq/selectlist.do";//등록되고나면 리스트로(post는 주소로 입력, get은 html로 입력)
    }

    //검색, 페이지네이션
    //http://127.0.0.1:8085/QOOT3/faq/selectlist.do
    @GetMapping(value = "/selectlist.do")
    public String  selectlistGet(
        Model model,
        @RequestParam (name="page",defaultValue = "1" , required=false)int page,
        @RequestParam (name="text",defaultValue = "" , required=false)String text,
        @RequestParam (name="type",defaultValue = "1" , required=false)int type,
        HttpServletRequest request
    ) {

        //=============조회수 새로고침 증가 방지
        HttpSession session = request.getSession();

        session.setAttribute("hit", -1L);
        //====================================
        
        if(type == 1){
            Map<String, Object> map = new HashMap<>();
            map.put("text", text);
            map.put("start",  page * 10 -9);
            map.put("end", page * 10);  

            long cnt = faqService.FAQcount(text);

            model.addAttribute("pages", (cnt-1)/10+1); 
            List<FAQDTO> list = faqService.FAQSearchPagenationList(map);
            model.addAttribute("obj", list);
        }
        if(type == 2){
            Map<String, Object> map = new HashMap<>();
            map.put("text", text);
            map.put("start",  page * 10 -9);
            map.put("end", page * 10);  

            long cnt = faqService.FAQcount(text);

            model.addAttribute("pages", (cnt-1)/10+1); 

            List<FAQDTO> list1 = faqService.FAQSearchPagenationhitList(map);
            model.addAttribute("obj", list1);
        }

        return "FAQ/selectlist";//템플릿/FAQ/selectlist(selectlist.html)구동시켜
    }

    //한개조회
    @GetMapping(value = "/selectone.do")
    public String  FAQselectOneGet(
        Model model,
        HttpServletRequest request,
        @RequestParam (name="no", defaultValue = "0")long no,
        @RequestParam (name="type",defaultValue = "1" , required=false)int type
    ) {

        HttpSession session = request.getSession();
        
            if(no == -1){
            
                model.addAttribute("msg", "마지막 페이지입니다.");
                model.addAttribute("url", 
                request.getContextPath() + "/faq/selectlist.do" );
                return "alert/alert";
            }

        if(type == 1){
            System.out.println("--------------------------type1--------------------------");
            FAQDTO ret = faqService.FAQselectOne(no);

            long prev = faqService.prevpageFAQno(no);
            
            long next = faqService.nextpageFAQno(no);
            
            System.out.println(prev);
            System.out.println(next);
            
            model.addAttribute("obj", ret);
            model.addAttribute("prev", prev);
            model.addAttribute("next", next);
            
        }

        if(type == 2){
            System.out.println("--------------------------type2--------------------------");
            FAQDTO ret = faqService.FAQselectOne(no);

            Long rown  = ret.getRown();
            long prev = faqService.prevpageFAQhit(rown);
            
            long next = faqService.nextpageFAQhit(rown);
            
            System.out.println(prev);
            System.out.println(next);
            

            model.addAttribute("obj", ret);
            model.addAttribute("prev", prev);
            model.addAttribute("next", next);

        }
         
        if( (Long) session.getAttribute("hit" ) != no ){
            faqService.updatedHitFAQ(no);//조회수
            session.setAttribute("hit", no);
        }

        if(no == 0){  //오류시 0 출력                                       
            return "redirect:/faq/selectlist.do";               
        }
        return "FAQ/selectone";//템플릿/FAQ/selectlist(selectlist.html)구동시켜
    }
 

    //admin 페이지 =========================================================================

    //등록 GET
    // 127.0.0.1:8085/QOOT3/admininsert.do
    @GetMapping(value = "/admininsert.do")
    public String  admininsertGet() {

        return "FAQ/admin/insert";
    }

    //등록 POST
    //127.0.0.1:8085/QOOT3/insert.do
    @PostMapping(value="/admininsert.do")
    public String admininsertPost(@ModelAttribute FAQDTO faqdto, Model model) {

        System.out.println("===================================");
        System.out.println(faqdto.toString());
        System.out.println("===================================");

        faqService.insertFAQ(faqdto);
        
        return "redirect:/faq/adminselectlist.do";
    }

    
    //한개조회
    @GetMapping(value = "/adminselectone.do")
    public String  adminFAQselectOneGet(
        Model model,
        HttpServletRequest request,
        @RequestParam (name="no", defaultValue = "0")long no,
        @RequestParam (name="type",defaultValue = "1" , required=false)int type
        ) {
    
            HttpSession session = request.getSession();
    
            System.out.println(session.getAttribute("hit"));
            
            if(no == -1){

                model.addAttribute("msg", "마지막 페이지입니다.");
                model.addAttribute("url", 
                request.getContextPath() + "/faq/adminselectlist.do" );
                return "alert/alert";
            }

            if(type == 1){
                FAQDTO ret = faqService.FAQselectOne(no);
    
                long prev = faqService.prevpageFAQno(no);
                
                long next = faqService.nextpageFAQno(no);
                
                model.addAttribute("obj", ret);
                model.addAttribute("prev", prev);
                model.addAttribute("next", next);
            
            }
    
            if(type == 2){
    
                FAQDTO ret = faqService.FAQselectOne(no);
    
                Long rown  = ret.getRown();
                System.out.println(rown);
                long prev = faqService.prevpageFAQhit(rown);
                System.out.println("ROWN=====================================");
                System.out.println("이전글 번호==========="+prev);
                
                long next = faqService.nextpageFAQhit(rown);
                System.out.println("다음글 번호========="+next);
                System.out.println("ROWN=====================================");
                model.addAttribute("obj", ret);
                model.addAttribute("prev", prev);
                model.addAttribute("next", next);
                System.out.println(ret+"==================================");
            
            }

            if( (Long) session.getAttribute("hit" ) != no ){
                faqService.updatedHitFAQ(no);//조회수
                session.setAttribute("hit", no);
            }

        if(no == 0){  //오류시 0 출력                                       
            return "redirect:/faq/adminselectlist.do";               
       }
        return "FAQ/admin/selectone";//템플릿/FAQ/selectlist(selectlist.html)구동시켜
    }




    //수정용 Get
    @GetMapping(value = "/adminupdate.do")
    public String boardupdateGET(Model model, 
        @RequestParam(name = "no", defaultValue = "0" )long no){

            FAQDTO ret = faqService.FAQselectOne(no);                

            model.addAttribute("obj", ret);                       

        return "FAQ/admin/update"; // boardlist.html파일
    }

    //수정 POST
    //127.0.0.1:8085/QOOT3/insert.do
    @PostMapping(value="/adminupdate.do")
    public String adminupdatePost(
        @ModelAttribute FAQDTO faqdto, 
        @RequestParam(name="no")long no,
        Model model) {

            FAQDTO faq = faqService.FAQselectOne(no);
            faq.setTitle(faqdto.getTitle());
            faq.setContent(faqdto.getContent());

            System.out.println(faq+"====================================");

            faqService.updateFAQ(faq);
        
        return "redirect:/faq/adminupdate.do?no="+faqdto.getNo();//등록되고나면 리스트로(post는 주소로 입력, get은 html로 입력)
    }

    //검색, 페이지네이션
    //127.0.0.1:8085/QOOT3/faq/adminselectlist.do
    @GetMapping(value = "/adminselectlist.do")
    public String  adminselectlistGet(
        Model model,
        @RequestParam (name="page",defaultValue = "1" , required=false)int page,
        @RequestParam (name="text",defaultValue = "" , required=false)String text,
        @RequestParam (name="type",defaultValue = "1" , required=false)int type,
        HttpServletRequest request
    ) {

        //=============조회수 새로고침 증가 방지
        HttpSession session = request.getSession();

        session.setAttribute("hit", -1L);
        //====================================

        if(type == 1){
            Map<String, Object> map = new HashMap<>();
            map.put("text", text);
            map.put("start",  page * 10 -9);
            map.put("end", page * 10);  

            long cnt = faqService.FAQcount(text);

            List<FAQDTO> list = faqService.FAQSearchPagenationList(map);
            model.addAttribute("obj", list);

            model.addAttribute("pages", (cnt-1)/10+1); 

        }
        if(type == 2){
            Map<String, Object> map = new HashMap<>();
            map.put("text", text);
            map.put("start",  page * 10 -9);
            map.put("end", page * 10);  

            long cnt = faqService.FAQcount(text);

            model.addAttribute("pages", (cnt-1)/10+1); 

            List<FAQDTO> list1 = faqService.FAQSearchPagenationhitList(map);
            model.addAttribute("obj", list1);
        }

        return "FAQ/admin/selectlist";//템플릿/FAQ/selectlist(selectlist.html)구동시켜
    }


    @PostMapping(value="/admindelete.do")
    public String deletePost(
        @RequestParam(name="no")long no
    ){

        faqService.deleteFAQ(no);
      
        return "redirect:/faq/adminselectlist.do";
    }
    
}
