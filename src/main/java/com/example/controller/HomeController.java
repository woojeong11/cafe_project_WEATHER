package com.example.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.dto.ProductDTO;
import com.example.entity.Member;
import com.example.entity.Minfo;
import com.example.entity.Token;
import com.example.jwt.JwtUtil;
import com.example.service.GetUserInfoService;
import com.example.service.KakaotokenService;
import com.example.service.MemberService;
import com.example.service.ProductImageService;
import com.example.service.ProductService;
import com.example.service.SecurityLogService;
import com.example.service.review.ReviewService;

@Controller
public class HomeController {
    
    @Autowired
    KakaotokenService kService;

    @Autowired
    MemberService mService;

    @Autowired
    SecurityLogService securityLogService;

    @Autowired
    PasswordEncoder bcpe;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    ProductService pService;

    @Autowired
    ProductImageService piService;

    @Autowired
    ReviewService rService;

    @Value("${serve.path}")
    String severpath;

    // 홈
    // http://127.0.0.1:8085/QOOT1/home.do
    @GetMapping(value = {"/home.do", "/" })
    public String homeGET(
        HttpServletRequest request,
        Model model
    ){  

        System.out.println(request.getContextPath());

        // 베스트용 음료 리스트
        Map<String, Object> map1 = new HashMap<>();

        map1.put("start", 1);
        map1.put("end", 8);
        map1.put("category", "drink");

        List<ProductDTO> coffeelist = pService.mainSoldProduct(map1);

        for(ProductDTO coffee : coffeelist){

            Long no =piService.selectImageNoOne2(coffee.getNo()).getNo();

            coffee.setImage("/productimage/image?no="+ no);
        }

        // 베스트용 디저트 리스트
        Map<String, Object> map2 = new HashMap<>();

        map2.put("start", 1);
        map2.put("end", 8);
        map2.put("category", "dessert");

        List<ProductDTO> dessertlist = pService.mainSoldProduct(map2);

        for(ProductDTO dessert : dessertlist){

            Long no =piService.selectImageNoOne2(dessert.getNo()).getNo();

            dessert.setImage("/productimage/image?no="+ no);
        }

        // 베스트용 상품 리스트
        Map<String, Object> map3 = new HashMap<>();

        map3.put("start", 1);
        map3.put("end", 8);
        map3.put("category", "goods");

        List<ProductDTO> itemlist = pService.mainSoldProduct(map3);

        for(ProductDTO item : itemlist){

            Long no =piService.selectImageNoOne2(item.getNo()).getNo();

            item.setImage("/productimage/image?no="+ no);
        }

        model.addAttribute("coffeelist", coffeelist );
        model.addAttribute("dessertlist", dessertlist );
        model.addAttribute("itemlist", itemlist );
        
        // 베스트 리뷰 값 던지기
        Map<String, Object> map4 = new HashMap<>();
        map4.put("start", 1);
        map4.put("end", 5);

        List<Map<String, Object>> bestlist = rService.selectBest(map4);
        for(Map<String, Object> obj : bestlist){
            
            // 프사 삽입을 위한 유저아이디 기반 url 가져오기 (MINFO에 있음)
            String profileurl = mService.memberinfoselectone(obj.get("USERID").toString()).getUrl();
            obj.put("profileurl", profileurl);
        }
        // System.out.println(bestlist.toString());
        model.addAttribute("reviewlist", bestlist );
        
        return "home";
    }

    // 카카오 로그인 시 홈
    // http://127.0.0.1:8085/QOOT1/kakao/home.do
    @GetMapping(value = "/kakao/home.do" )
    public String kakaohomeGET(
        @RequestParam("code") String code, 
        Model model,
        HttpServletRequest request
    ){
        System.out.println("----------------------------------------");
        System.out.println(code);
        System.out.println("----------------------------------------");

        HttpSession session = request.getSession();

        KakaotokenService restJsonService = new KakaotokenService();

        //access_token이 포함된 JSON String을 받아온다.
        String accessTokenJsonData = restJsonService.getKakaoOAuthToken(code);
        if(accessTokenJsonData=="error") return "error";

        System.out.println(accessTokenJsonData);

        //JSON String -> JSON Object
        JSONObject accessTokenJsonObject = new JSONObject(accessTokenJsonData);

        //access_token 추출
        String accessToken = accessTokenJsonObject.get("access_token").toString();

        //유저 정보가 포함된 JSON String을 받아온다.
        GetUserInfoService getUserInfoService = new GetUserInfoService();
        String userInfo = getUserInfoService.getUserInfo(accessToken);

        System.out.println("accessToken==================================================>" + accessToken);

        //JSON String -> JSON Object
        JSONObject userInfoJsonObject = new JSONObject(userInfo);

        //유저의 Email 추출
        JSONObject kakaoAccountJsonObject = (JSONObject)userInfoJsonObject.get("kakao_account");
        String nickname = kakaoAccountJsonObject.getJSONObject("profile").getString("nickname");
        String phone    = "010-0000-0000";
        String userid   =  userInfoJsonObject.get("id").toString();
        
        System.out.println(userInfoJsonObject.toString());
        
        String hashpw   = bcpe.encode(userid); 

        // 필수 정보 upsert
        Member member = new Member();
        member.setName( "kakao" + nickname);
        member.setNickname(nickname);
        member.setPhone(phone);
        member.setUserid(userid);
        member.setUserpw(hashpw);
        member.setEmail(kakaoAccountJsonObject.get("email").toString()); 
        
        int memret = mService.upsertMember(member);
        
        System.out.println(member);
        // 선택정보 upsert
        Minfo minfo = new Minfo();
        if(kakaoAccountJsonObject.getJSONObject("profile").has("profile_image_url") == true){
            session.setAttribute("url", kakaoAccountJsonObject.getJSONObject("profile").getString("profile_image_url"));
            minfo.setUrl(kakaoAccountJsonObject.getJSONObject("profile").getString("profile_image_url"));
        }
        System.out.println(kakaoAccountJsonObject.has("gender"));

        if(kakaoAccountJsonObject.has("gender") == false){
            kakaoAccountJsonObject.put("gender", "null");
        }
        if(kakaoAccountJsonObject.has("age_range") == false){
            kakaoAccountJsonObject.put("age_range", "null");
        }
        if(kakaoAccountJsonObject.has("email") == false){
            kakaoAccountJsonObject.put("email", "null");
        }
        minfo.setUserid(userid);
        minfo.setGender(kakaoAccountJsonObject.get("gender").toString());
        minfo.setAge(kakaoAccountJsonObject.get("age_range").toString());

        int minret = mService.upsertMinfo(minfo);

        // 토큰 upsert
        securityLogService.loadUserByUsername(member.getUserid());
    
        String token = jwtUtil.generateToken(member.getUserid(), "CUSTOMER");
        System.out.println(token);
        // TOKENTBL에 insert 또는 update
        Token obj = new Token();
        obj.setToken(token);
        obj.setMember(member);
        int tokret = mService.upsertToken(obj);

        System.out.println(kakaoAccountJsonObject.getJSONObject("profile").getString("profile_image_url"));

        // 세션에서 url꺼내기(intercepter)
        String CurrentUrl = (String) session.getAttribute("CURRENT_URL");

        System.out.println(CurrentUrl);

        // if(kakaoAccountJsonObject.getJSONObject("profile").has("profile_image_url") == true){
        //     session.setAttribute("url", kakaoAccountJsonObject.getJSONObject("profile").getString("profile_image_url"));
        // }

        if( memret <= 0 || minret <= 0 || tokret <=0 ){
            return "home";
        }
        else{
            session.setAttribute("LOGIN", "카카오회원");
            session.setAttribute("TOKEN", token);
            return "redirect:" + CurrentUrl;
        }

    }

    // 네이버 로그인 시에 콜백주소
    // http://127.0.0.1:8085/QOOT1/naver/home.do
    @GetMapping(value = "/naver/home.do" )
    public String NaverhomeGET(
        HttpServletRequest request,
        Model model
    ){
        HttpSession session = request.getSession();

        System.out.println(session.getAttribute("LOGIN"));
        System.out.println(session.getAttribute("url"));

        // model.addAttribute(model);
        return "member/navercallback";

    }

    @GetMapping(value="/page403.do") 
    public String errorGET(){
        return "404";
    }

    // 127.0.0.1:8085/QOOT1/list.do
    @GetMapping(value = "/list.do" )
    public String ProductGET(){
        return "product";
    }

}
