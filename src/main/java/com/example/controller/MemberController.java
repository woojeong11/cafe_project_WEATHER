package com.example.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.example.config.GlobalProperties;
import com.example.dto.MemberDTO;
import com.example.entity.Member;
import com.example.entity.Memberprofileimage;
import com.example.entity.Minfo;
import com.example.entity.Token;
import com.example.jwt.JwtUtil;
import com.example.service.MemberService;
import com.example.service.ResisterMail;
import com.example.service.SecurityLogService;
import com.example.service.TokenService;



@Controller
@RequestMapping(value = "/member")
public class MemberController {
    
    @Autowired
    TokenService tService;

    @Autowired
    MemberService mService;

    @Autowired
    SecurityLogService securityLogService;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    PasswordEncoder bcpe;

    @Autowired
    ResourceLoader resourceLoader;

    @Autowired
    ResisterMail resisterMail;
  

    @GetMapping(value= "/login.do")
    public String loginGET(){

        return "member/login";
    }
    
    // 로그아웃
    // http://127.0.0.1:8085/QOOT1/member/logout.do
    @GetMapping(value = "/logout.do")
    public String logoutGET(
        HttpServletRequest request
    ){

        HttpSession session = request.getSession();

        String token = (String) session.getAttribute("TOKEN");
        tService.deleteToken(token);

        session.removeAttribute("LOGIN");
        session.removeAttribute("url");
        session.removeAttribute("TOKEN");
        
        return "redirect:/home.do";
    }

    // upsert 회원가입( 네이버로그인 api인 경우 )
    // http://127.0.0.1:8085/QOOT1/member/upsert.do
    @PostMapping(value = "/upsert.do", produces="text/html; charset=utf-8")
    @ResponseBody
    public String upsertPOST(
        HttpServletRequest request, HttpServletResponse response
    ){

        HttpSession session = request.getSession();

        String jsonString =  request.getParameter("jsonString");
        
        JSONObject json = new JSONObject(jsonString);
        // 세션이 로그인 상태가 아닐 때만 실행
        if( session.getAttribute("LOGIN") ==  null){

            System.out.println(json.toString());
            
            // 비밀번호는 오는 userid를 변형시켜 등록해줌.
            String hashpw = bcpe.encode(json.getString("userid"));
    
            // 필수 정보 upsert
            Member member = new Member();
            member.setUserpw(hashpw);
            member.setName(json.getString("name"));
            member.setNickname(json.getString("nickname"));
            member.setPhone(json.getString("phone"));
            member.setUserid(json.getString("userid"));
            member.setEmail(json.getString("email"));
            
            int memret = mService.upsertMember(member);
            
            System.out.println(member);
    


            // 선택정보 upsert
            Minfo minfo = new Minfo();
            if( json.has("profile") == true){
                    minfo.setUrl(json.get("profile").toString());
                    session.setAttribute("url", json.get("profile").toString());
                }
           
            minfo.setUserid(json.getString("userid")); 
            minfo.setAge(json.getString("age"));
            minfo.setGender(json.getString("gender"));
            System.out.println("------------------------------------minfo---------------------------------");
            System.out.println(minfo.toString());
            System.out.println("------------------------------------minfo---------------------------------");
            int minret = mService.upsertMinfo(minfo); 
    
            // 토큰 upsert
            securityLogService.loadUserByUsername(member.getUserid());
        
            String token = jwtUtil.generateToken(json.getString("userid"), "CUSTOMER");
            System.out.println(token);
            // TOKENTBL에 insert 또는 update
            Token obj = new Token();
            obj.setToken(token);
            obj.setMember(member);
            int tokret = mService.upsertToken(obj);
            
            System.out.println(json.has("profile"));

            // 세션에서 url꺼내기(intercepter)
            String CurrentUrl = (String) session.getAttribute("CURRENT_URL");

            System.out.println(CurrentUrl);
    

            if( memret <= 0 || minret <= 0 || tokret <=0 ){
                json.put("result", "no");    
            }
            else{
                // session.setAttribute("url", json.getString("profile") );
                session.setAttribute("LOGIN", "네이버회원");
                // if( json.has("profile") == true){
                //     session.setAttribute("url", json.get("profile").toString());
                // }
                session.setAttribute("TOKEN", token);
            }
            
            return request.getContextPath() + CurrentUrl;
        }
        json.put("result", "접속중");
        String jsonResult = json.toString();
        return jsonResult;
    }


    // 토큰 저장
    // http://127.0.0.1:8085/QOOT1/member/tokeninsert.do
    @PostMapping(value = "/tokeninsert.do")
    public String tokenPOST( @RequestBody Token token ){
        
        System.out.println(token);

        tService.insertToken(token);

        return "redirect:/home.do";
    }

    // 회원가입 화면
    // http://127.0.0.1:8085/QOOT1/member/join.do
    @GetMapping(value= "/join.do")
    public String JoinGET(){

        return "member/join";
    }

    // 회원가입 실행
    // http://127.0.0.1:8085/QOOT1/member/join.do
    @PostMapping(value="/join.do")
    public String joinPOST(
        @ModelAttribute MemberDTO memberinfo
    ) throws IOException{
        System.out.println("-------------------------------------------------------");
        System.out.println(memberinfo.toString());
        System.out.println("-------------------------------------------------------");

        // 비밀번호 인코더
        String hashpw = bcpe.encode(memberinfo.getUserpw());

        // 필수정보 엔티티
        Member member = new Member();
        member.setUserid(memberinfo.getUserid());
        member.setUserpw(hashpw);
        member.setName(memberinfo.getName());
        member.setNickname(memberinfo.getNickname());
        member.setPhone(memberinfo.getPhone());
        member.setEmail(memberinfo.getEmail());

        System.out.println(member);


        // 선택정보 엔티티
        Minfo minfo = new Minfo();
        
        minfo.setUserid(memberinfo.getUserid());
        minfo.setAge(String.valueOf(memberinfo.getAge()));
        minfo.setGender(memberinfo.getGender());
        // minfo.setUrl("http://127.0.0.1:8085" + GlobalProperties.servepath + "/member/imageselect?userid=" + memberinfo.getUserid() );
        minfo.setUrl("/member/imageselect?userid=" + memberinfo.getUserid() );
        System.out.println(minfo);
    
        // 회원가입 실행 서비스
        int retmem = mService.Insertmember(member);
        int retmin = mService.Insertmemberinfo(minfo);

        if(retmem == 1 && retmin == 1){
            return "redirect:/home.do";
        }
        
        return "redirect:/member/join.do";
    }
    
    //  일반회원 로그인
    @PostMapping(value="/login.do")
    public String LoginPOST(
        @ModelAttribute Member memberinfo,
        HttpServletRequest request,
        Model model
        ) {
            memberinfo.setRole("CUSTOMER");
            
            Member member = mService.memberSelectone(memberinfo.getUserid());       
            
            HttpSession session = request.getSession();
        if(member == null){
            String CurrentUrl = (String) session.getAttribute("CURRENT_URL");
            model.addAttribute("msg", "로그인 정보가 일치하지 않습니다.");
            model.addAttribute("url", 
            request.getContextPath() + CurrentUrl );

            return "alert/alert";
        }
        // 입력한 비밀번호가 일치하는지 검증 후
        if(bcpe.matches(memberinfo.getUserpw(), member.getUserpw())){
            // securityLogService에서 검증
            securityLogService.loadUserByUsername(memberinfo.getUserid());
            
            String token = jwtUtil.generateToken( member.getUserid(), member.getRole());
            System.out.println(token);
            // TOKENTBL에 insert 또는 update
            Token obj = new Token();
            obj.setToken(token);
            obj.setMember(member);
            mService.upsertToken(obj);           

            // 로그인 시 springboot 세션에 정보를 넣기
            session.setAttribute("LOGIN", "일반회원");
            session.setAttribute("TOKEN", token);
            
            // 세션에서 url꺼내기(intercepter)
            String CurrentUrl = (String) session.getAttribute("CURRENT_URL");

            System.out.println(CurrentUrl);
    
            return "redirect:" + CurrentUrl;
        }
        String CurrentUrl = (String) session.getAttribute("CURRENT_URL");
        model.addAttribute("msg", "로그인 정보가 일치하지 않습니다.");
        model.addAttribute("url", 
        request.getContextPath() + CurrentUrl );

        return "alert/alert";
    }

    //  점주(admin) 회원가입

    //  점주(admin) 로그인
    @PostMapping(value="/adminlogin.do")
    public String adminLoginPOST(
        @ModelAttribute Member memberinfo,
        HttpServletRequest request,
        Model model
        ) {
            memberinfo.setRole("ADMIN");
            
            Member member = mService.memberSelectone(memberinfo.getUserid());       
            
            HttpSession session = request.getSession();
        if(member == null){
            String CurrentUrl = (String) session.getAttribute("CURRENT_URL");
            model.addAttribute("msg", "로그인 정보가 일치하지 않습니다.");
            model.addAttribute("url", 
            request.getContextPath() + CurrentUrl );

            return "alert/alert";
        }
        // 입력한 비밀번호가 일치하는지 검증 후
        if(memberinfo.getUserpw().equals(member.getUserpw())){
            
            // securityLogService에서 검증
            securityLogService.loadUserByUsername(memberinfo.getUserid());
            
            String token = jwtUtil.generateToken( member.getUserid(), member.getRole());
            System.out.println(token);
            // TOKENTBL에 insert 또는 update
            Token obj = new Token();
            obj.setToken(token);
            obj.setMember(member);
            mService.upsertToken(obj);           
            
            // 로그인 시 springboot 세션에 정보를 넣기
            session.setAttribute("LOGIN", "점주");
            session.setAttribute("TOKEN", token);
            
            return "redirect:/cafe/admin/home.do";
        }
        String CurrentUrl = (String) session.getAttribute("CURRENT_URL");
        model.addAttribute("msg", "로그인 정보가 일치하지 않습니다.");
        model.addAttribute("url", 
        request.getContextPath() + CurrentUrl );

        return "alert/alert";
    }

    // MyPage 화면
    // http://127.0.0.1:8085/QOOT1/member/mypage.do
    @GetMapping(value= "/mypage.do")
    public String mypageGET(
        HttpServletRequest request,
        Model model
    ){

        HttpSession session = request.getSession();

        System.out.println(session.getAttribute("LOGIN"));
        System.out.println(session.getAttribute("url"));

        String userid = (String) request.getAttribute("userid");

        // 필수 입력 정보
        Member member = mService.memberSelectone(userid );

        // 선택 입력 정보
        Minfo minfo   = mService.memberinfoselectone(userid); 

        System.out.println(member);
        System.out.println(minfo);
        model.addAttribute("userinfo", member);
        model.addAttribute("minfo", minfo);

        if( session.getAttribute("LOGIN").equals("일반회원")  ){
            

            // 프로필 이미지 조회
            Memberprofileimage img = mService.selectoneprofileimage(member);
            if(img != null){
                session.setAttribute("url", "http://3.38.209.149:8080" + GlobalProperties.servepath + "/member/imageselect?userid=" + userid );
                // model.addAttribute("profile", img);
            }
            else{
                session.setAttribute("url", GlobalProperties.servepath + "/img/image.png");
            }
        }
        return "member/mypage";
    }
    

    // 프로필 이미지 등록
    // http://127.0.0.1:8085/QOOT1/member/profileimageupsert.do
    @PostMapping(value = "/profileimageinsert.do")
    public String profileimgPOST(
        HttpServletRequest request,       
        @RequestParam(name = "file") MultipartFile file
    ) throws IOException{

        String userid = (String) request.getAttribute("userid");
        System.out.println(userid);

        
        Member member = new Member();     
        member.setUserid(userid);
        
        Memberprofileimage profile = mService.selectoneprofileimage(member);
        if(profile == null){
            Memberprofileimage image = new Memberprofileimage();
            image.setImagedata(file.getBytes());
            image.setImagename(file.getOriginalFilename());
            image.setImagesize(file.getSize());
            image.setImagetype(file.getContentType());
            image.setMember(member);
            
            mService.insertprofileimage(image);

        }
        else{
            Memberprofileimage image = new Memberprofileimage();
            image.setImagedata(file.getBytes());
            image.setImagename(file.getOriginalFilename());
            image.setImagesize(file.getSize());
            image.setImagetype(file.getContentType());
            image.setMember(member);
            image.setNo(profile.getNo());

            mService.updateprofileimage(image);
        }
        
        return "redirect:/member/mypage.do";
    }

    // 프로필 이미지 1개 조회( 유저아이디 기준)
    // http://127.0.0.1:8085/QOOT1/member/imageselect?userid=
    @GetMapping(value = "/imageselect")
    public ResponseEntity<byte[]> imageselectGET(
        HttpServletRequest request,
        @RequestParam(name="userid") String userid
    ) throws IOException{
        ResponseEntity<byte[]> response = null;

        Member member = new Member();
        member.setUserid(userid);

        Memberprofileimage image = mService.selectoneprofileimage(member);
        
        HttpHeaders headers = new HttpHeaders();
        if( image != null ){
            headers.setContentType(MediaType.parseMediaType(image.getImagetype()));
            response = new ResponseEntity<byte[]>(image.getImagedata(), headers, HttpStatus.OK);
        }
        else{
            // resources/static/imgs 에 있는 이미지 파일을 불러온다. @Autowired로 불러온 리소스로더를 이용.
            InputStream stream = resourceLoader.getResource("classpath:/static/img/image.png").getInputStream();
            headers.setContentType(MediaType.IMAGE_PNG);
            response = new ResponseEntity<byte[]>(stream.readAllBytes(), headers, HttpStatus.OK);
        }

        return response;
    }

    // 일반회원 정보 수정( 필수정보 , 선택정보 통합 )
    // http://127.0.0.1:8085/QOOT1/member/updateuserinfo.do
    @PostMapping(value="/updateuserinfo.do")
    public String updateuserinfo(
        HttpServletRequest request,
        Model model,
        @ModelAttribute MemberDTO memberinfo
    ){
        System.out.println("-------------------------------------------------------");
        System.out.println(memberinfo.toString());
        System.out.println("-------------------------------------------------------");
        
        String userid = (String) request.getAttribute("userid");

        HttpSession session = request.getSession();

        // 필수정보 엔티티
        Member member = new Member();
        member.setUserid(userid);
        member.setName(memberinfo.getName());
        member.setNickname(memberinfo.getNickname());
        member.setPhone(memberinfo.getPhone());
        member.setEmail(memberinfo.getEmail());

        System.out.println(member);


        // 선택정보 엔티티
        Minfo minfo = new Minfo();
        
        minfo.setUserid(userid);
        minfo.setAge(String.valueOf(memberinfo.getAge()));
        minfo.setGender(memberinfo.getGender());
        System.out.println(minfo);
    
        int retmem = mService.updateMember(member);
        int retmin = mService.updateMinfo(minfo);

        if(retmem == 1 && retmin == 1){
            return "redirect:/member/mypage.do";
        }

        String CurrentUrl = (String) session.getAttribute("CURRENT_URL");
        model.addAttribute("msg", "회원 정보수정에 실패했습니다.");
        model.addAttribute("url", 
        request.getContextPath() + CurrentUrl );

        return "alert/alert";
    }

    // 암호변경
    // http://127.0.0.1:8085/QOOT1/member/passwordupdate.do
    @PostMapping(value="/passwordupdate.do")
    public String passwordupdate(
        HttpServletRequest request,
        Model model,
        @RequestParam(name = "password") String password,
        @RequestParam(name = "password1") String password1
    ){
        System.out.println("-------------------------------------------------------");
        System.out.println(password.toString());
        System.out.println(password1.toString());
        System.out.println("-------------------------------------------------------");
        
        String userid = (String) request.getAttribute("userid");

        // 회원 정보 조회
        Member member = mService.memberSelectone(userid);
        
        if(bcpe.matches(password, member.getUserpw())){
            // 현재 비밀번호가 일치하면, 암호변경 실행
            Map<String, Object> map = new HashMap<>();
            map.put("userid", userid);
            map.put("userpw", bcpe.encode(password1));

            mService.updatePassword(map);

            model.addAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
            model.addAttribute("url", 
            request.getContextPath() + "/member/mypage.do" );

            return "alert/alert";
        }
        model.addAttribute("msg", "현재 비밀번호가 일치하지 않습니다.");
        model.addAttribute("url", 
        request.getContextPath() + "/member/mypage.do" );

        return "alert/alert";
    }

    // 회원탈퇴
    // http://127.0.0.1:8085/QOOT1/member/delete.do
    @PostMapping(value="/delete.do")
    public String delete(
        HttpServletRequest request,
        Model model,
        @RequestParam(name = "userpw") String userpw
    ){
        System.out.println("-------------------------------------------------------");
        System.out.println(userpw.toString());
        System.out.println("-------------------------------------------------------");
        
        HttpSession session = request.getSession();

        // session의 LOGIN을 불러와 일반 회원일 경우에만 진행
        if( session.getAttribute("LOGIN") == "일반회원" ){

            String userid = (String) request.getAttribute("userid");
    
            // 회원 정보 조회
            Member member = mService.memberSelectone(userid);
            
            // 비밀번호가 일치하는지 확인
            if(bcpe.matches(userpw, member.getUserpw())){
                // 일치하면 탈퇴처리 진행
    
                // 선택 입력정보 삭제
                int ret1 = mService.deleteMinfo(userid);
    
                // 프로필이미지 삭제
                int ret2 = mService.deleteProfileImage(userid);
    
                // 필수 입력정보 수정
                int ret3 = mService.updeleteMember(userid);
    
    
                // 정상적으로 진행되었다면 알림 후 로그아웃 주소로 보냄
                if( ret1 == 1 && ret2 == 1 && ret3 == 1){
                    model.addAttribute("msg", "회원탈퇴 되었습니다.");
                    model.addAttribute("url", 
                    request.getContextPath() + "/member/logout.do" );
    
                    return "alert/alert";        
                }
                model.addAttribute("msg", "회원탈퇴 실패했습니다.");
                model.addAttribute("url", 
                request.getContextPath() + "/member/mypage.do" );
        
                return "alert/alert";
            }
            
            model.addAttribute("msg", "현재 비밀번호가 일치하지 않습니다.");
            model.addAttribute("url", 
            request.getContextPath() + "/member/mypage.do" );
    
            return "alert/alert";
        }
        else{
            // 일반회원이 아님(카카오회원 , 네이버회원)

            model.addAttribute("msg", "일반회원만 가능합니다.");
            model.addAttribute("url", 
            request.getContextPath() + "/member/mypage.do" );
    
            return "alert/alert";
        }

    }

    // 아이디/비밀번호 찾기 페이지화면
    // http://127.0.0.1:8085/QOOT1/member/findidpw.do
    @GetMapping(value = "/findidpw.do")
    public String certificationGET(){
        return "member/findidpw";
    }

    // 아이디 찾기
    // http://127.0.0.1:8085/QOOT1/member/findid.do
    @GetMapping(value = "/findid.do")
    public String findidGET(
        @ModelAttribute Member member,
        Model model,
        HttpServletRequest request
    ){

        System.out.println(member);

        Map<String, Object> map = new HashMap<>();

        map.put("phone", member.getPhone());
        map.put("email", member.getEmail());

        Member findmember = mService.Email_Phone_selectMember(map);

        System.out.println(findmember);
        if(findmember == null){
            model.addAttribute("msg", "입력하신 정보에 맞는 아이디가 없습니다.");
            model.addAttribute("url", 
            request.getContextPath() + "/member/findidpw.do" );
    
            return "alert/alert";
        }

        model.addAttribute("id", findmember.getUserid());

        return "member/findidpw";
    }

    // 비밀번호 찾기 유저가 입력한 찾을 비밀번호의 아이디를 기반으로 시작
    // http://127.0.0.1:8085/QOOT1/member/findpw.do
    @GetMapping(value = "/findpw.do")
    public String findpwGET(
        @RequestParam(name = "userid") String userid,
        HttpServletRequest request,
        Model model
    ) throws Exception{

        Member member = mService.memberSelectone(userid);
        System.out.println(member);


        if(member == null){
            model.addAttribute("msg", "아이디가 존재하지 않습니다.");
            model.addAttribute("url", 
            request.getContextPath() + "/member/findidpw.do" );
    
            return "alert/alert";
        }

        
        System.out.println(member);

        String Email  = member.getEmail();

        String Code   = resisterMail.sendSimpleMessage(Email);
        System.out.println("------------------code--------------------------------");
        System.out.println(Code); 
        System.out.println("------------------code--------------------------------");

        model.addAttribute("pwcode", Code);

        return "member/findidpw";
    }
    
    // 암호변경
    // http://127.0.0.1:8085/QOOT1/member/findpw/passwordupdate.do
    @PostMapping(value="findpw/passwordupdate.do")
    public String pwfindpasswordupdate(
        HttpServletRequest request,
        Model model,
        @RequestParam(name = "userid") String userid,
        @RequestParam(name = "password") String password
    ){
        System.out.println("-------------------------------------------------------");
        System.out.println(password.toString());
        System.out.println("-------------------------------------------------------");

        
        Map<String, Object> map = new HashMap<>();

        map.put("userid", userid);
        map.put("userpw", bcpe.encode(password));

        mService.updatePassword(map);

        model.addAttribute("msg", "비밀번호가 성공적으로 변경되었습니다.");
        model.addAttribute("url", 
        request.getContextPath() + "/home.do" );

        return "alert/alert";
    }
}