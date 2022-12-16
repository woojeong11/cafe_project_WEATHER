package com.example.controller.RecommendWeather;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.dto.ProductDTO;
import com.example.dto.ProductImageDTO;
import com.example.entity.Product;
import com.example.entity.Recommend;
import com.example.entity.WeatherCategory;
import com.example.service.ProductImageService;
import com.example.service.ProductService;
import com.example.service.RecommendService;
import com.example.service.WeatherCategoryService;


@Controller
@RequestMapping(value = "/admin/recommend")
public class AdminRecommendController {
    
    @Autowired
    ProductService pService;

    @Autowired
    RecommendService rService;

    @Autowired
    ProductImageService iService;

    @Autowired
    WeatherCategoryService wcService;

    @Autowired
    ResourceLoader resourceLoader;

    // 등록화면
    // http://127.0.0.1:8085/QOOT1/admin/recommend/insert.do
    @GetMapping(value="/insert.do")
    public String recommendGET( 
        Model model 
    )
    {
        // 음료의 상품 목록(커피)
        List<ProductDTO> beverage = pService.selectCategory("drink");

        // 디저트의 상품 목록
        List<ProductDTO> dessert  = pService.selectCategory("dessert");

        model.addAttribute("beverage", beverage);
        model.addAttribute("dessert", dessert);

        return "recommend/recommendinsert";
    }
    
    // 등록 동작
    // http://127.0.0.1:8085/QOOT1/admin/recommend/insert.do
    @PostMapping(value="/insert.do")
    public String recommendPOST0(
        @RequestParam(name = "product1") Long proudct1,
        @RequestParam(name = "product2") Long proudct2,
        @RequestParam(name = "category") String category
    ){
        
        // System.out.println(proudct1);
        // System.out.println(proudct2);
        // System.out.println(category);
        
        WeatherCategory wCategory = wcService.selectWeatherCategory(category);
        // System.out.println(wCategory.toString());
        // 음료
        if( proudct1 != 0 ){
            Product product = new Product();
            product.setNo(proudct1);
            product.setCategory("drink");

            Recommend recommend = new Recommend();
            recommend.setCategory(wCategory);
            recommend.setProduct(product);
            rService.recommendinsert(recommend);
        }

        // 디저트
        if( proudct2 != 0 ){
            Product product = new Product();
            product.setNo(proudct2);
            product.setCategory("dessert");

            Recommend recommend = new Recommend();
            recommend.setCategory(wCategory);
            recommend.setProduct(product);
            rService.recommendinsert(recommend);
        }

        return "redirect:/admin/recommend/category/selectlist.do";
    }

    // 목록 화면
    // http://127.0.0.1:8085/QOOT1/admin/recommend/category/selectlist.do
    @GetMapping(value="/category/selectlist.do")
    public String recommendlistGET( 
        Model model,
        @RequestParam(name = "category", defaultValue = "") String category,
        @RequestParam(name = "page", defaultValue = "1") int page,
        @RequestParam(name = "tab", defaultValue = "1") int tab
    )
    {
        // 총 개수
        int count = rService.countBycategory(category);
        
        List<Recommend> list = rService.recommendselectcate(category, page, count);

        model.addAttribute("list", list);
        model.addAttribute("tab", tab);
        model.addAttribute("count", (count-1)/5 + 1);
        
        for(Recommend recommend : list){
            Long obj =iService.selectImageNoOne(recommend.getProduct().getNo());

            recommend.setProductimageurl("/productimage/image?no="+obj);
        }        

        return "recommend/recommendlist";
    }

    // 일괄 삭제( 체크한 것 기반 )
    // http://127.0.0.1:8085/QOOT1/admin/recommend/deletemany.do
    @PostMapping(value = "/deletemany.do")
    public String deleteManyPOST(
        HttpServletRequest request,
        @RequestParam(name = "chkno") List<Long> chklist
    ){

        System.out.println(chklist);

        HttpSession session = request.getSession();

        String CurrentUrl = (String) session.getAttribute("CURRENT_URL");
        System.out.println(CurrentUrl);

        rService.deleterecommend(chklist);

        return "redirect:" + CurrentUrl;
    }

    // 추천 배경이미지(날씨에 따라 변경될) 수정
    // http://127.0.0.1:8085/QOOT1/admin/recommend/backgroundimage.do
    @PostMapping(value = "/backgroundimage.do")
    public String backgroundimageUPDATE(
        @RequestParam(name = "file") MultipartFile file,
        @RequestParam(name = "code") String code
    ) throws IOException{

        WeatherCategory weatherCategory = new WeatherCategory();
        weatherCategory.setCode(code);
        weatherCategory.setImagedata(file.getBytes());
        weatherCategory.setImagename(file.getOriginalFilename());
        weatherCategory.setImagesize(file.getSize());
        weatherCategory.setImagetype(file.getContentType());

        wcService.updateWeatherCategory(weatherCategory);

        return "redirect:/admin/recommend/category/selectlist.do?tab=2";
    };


    // 배경 이미지 조회
    // 프로필 이미지 1개 조회( 유저아이디 기준)
    // http://127.0.0.1:8085/QOOT1/admin/recommend/imageselect?code=
    @GetMapping(value = "/imageselect")
    public ResponseEntity<byte[]> imageselectGET(
        @RequestParam(name="code") String code
    ) throws IOException{
        ResponseEntity<byte[]> response = null;

        WeatherCategory image = wcService.selectWeatherCategory(code);
        
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
}
