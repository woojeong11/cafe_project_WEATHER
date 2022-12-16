package com.example.restcontroller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.config.GlobalProperties;
import com.example.dto.weatherListDTO;
import com.example.entity.Weather;
import com.example.service.ProductImageService;
import com.example.service.RecommendService;
import com.example.service.WeatherCategoryService;
import com.example.service.weatherService;
import com.fasterxml.jackson.databind.ObjectMapper;


@RestController
@RequestMapping(value = "/api")
public class ApiRestController {

    @Autowired
    weatherService wService;

    @Autowired
    WeatherCategoryService wcService;
    
    @Autowired
    RecommendService rService;

    @Autowired
    ProductImageService pImageService;

    // 127.0.0.1:8085/QOOT1/api/test1.json
    @Scheduled(cron = "0 1 7 * * *")
    @GetMapping(value = "test1.json")
    public Map<String, Object> testGET() throws URISyntaxException{
        // 현재날짜 구하기
        LocalDate now = LocalDate.now();

        // 포멧 정의
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // 포맷 적용
        String today = now.format(formatter);

        System.out.println(today);

        String serviceKey = "serviceKey=nVhOnXku3h%2BeR4sJ%2FaQ7BHaiFaHZJjc9JHlTZEa4hlp6CmZP9rJkSoySma0tnO6bZPDTwcIJ%2FK4o4TZEVPy0Dw%3D%3D";

        String pageNo = "pageNo=1";

        String numOfRows = "numOfRows=1000";

        String dataType = "dataType=JSON";

        String basedate = "base_date=" + today;

        String basetime = "base_time=0500";

        String nx = "nx=99";

        String ny = "ny=75";

        String htmlUrl = "https://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst?"
                        + serviceKey + "&" + pageNo + "&" + numOfRows + "&" + dataType + "&" + basedate + "&" + basetime + "&" + nx + "&" + ny; 
        
    	
        // 인코딩 문제로 인해 URI로 변환시켜 줌.
        URI uri = new URI(htmlUrl); 

        Map<String, Object> map = new HashMap<>();

        try {

            RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

            String response = restTemplate.getForObject(uri, String.class);

            System.out.println(response);

            JSONObject jobc = new JSONObject(response);

            // System.out.println(jobc.toString());
            JSONArray jarr = jobc.getJSONObject("response").getJSONObject("body").getJSONObject("items").getJSONArray("item");

            // System.out.println(jarr.toString());
            weatherListDTO weatherlist = new weatherListDTO();

            List<Weather> list = new ArrayList<>();

            ObjectMapper mapper = new ObjectMapper();

            // System.out.println(jarr.toString());

            for(int i=0; i<jarr.length(); i++){
                
                JSONObject obj = jarr.getJSONObject(i); 

                // System.out.println(obj.toString());
                Weather test = mapper.convertValue(obj.toMap(), Weather.class);
                
                list.add(test);

                // System.out.println(test.toString());
            }

            weatherlist.setList(list);

            // System.out.println(weatherlist);

            wService.insertbatch(weatherlist);

            System.out.println(today + "일자 날짜 날씨 data 등록했습니다");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    // 127.0.0.1:8085/QOOT1/api/delete.json
    @Scheduled(cron = "0 0 7 * * *")
    @DeleteMapping(value = "/delete.json")
    public Map<String, Object> weatherDELETE(){

        Map<String, Object> map = new HashMap<>();
        
        int ret = wService.deleteWeatherData();
        
        if(ret== 1){
            map.put("status", 200);
            System.out.println("weather data 삭제 완료했습니다.");
        }
        else{
            map.put("status", 0);
        }

        return map;
    }

    //인증서 역할
    //throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException
    public HttpComponentsClientHttpRequestFactory clientHttpRequestFactory()  {
		try{
            TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;

            SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
            
            SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
            CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
            
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            requestFactory.setConnectTimeout(10 * 1000);    
            requestFactory.setReadTimeout(10 * 1000);
            
            return requestFactory;
        }
        catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    // 가지고 온 데이터 기반 조회
    // http://127.0.0.1:8085/QOOT1/api/select.json
    @GetMapping(value = "/select.json")
    public Map<String, Object> selectGET(){
        Map<String, Object> map = new HashMap<>();
        try {
//            // 현재날짜 구하기
//            DateFormat now = new SimpleDateFormat("yyyyMMdd");
//            DateFormat now1 = new SimpleDateFormat("HH");
 
        	// 현재날짜 구하기
            LocalDateTime now  = LocalDateTime.now();
//
            // 포멧 정의
            DateTimeFormatter formatter  = DateTimeFormatter.ofPattern("yyyyMMdd");
            
            DateTimeFormatter formatterT = DateTimeFormatter.ofPattern("HH");
            
            String today = now.format(formatter);
            String tim = now.format(formatterT);
            
            
            String time  = "";
            if(Integer.parseInt( tim) + 9 >= 7 && Integer.parseInt( tim) + 9 <12 ){
                time  = "0900";
            }
            else if(Integer.parseInt( tim) + 9 >=12 && Integer.parseInt( tim) + 9 < 18){
                time  = "1500";
            }
            else if(Integer.parseInt( tim) + 9 >=18 && Integer.parseInt(tim) + 9 < 24){
                time  = "2000";
            }
            else{
                time  = "0000";
            }
            
         // 맑음 흐림 구름많음 구분용 category SKY
            Map<String, Object> map1 = new HashMap<>();
    
            map1.put("time", time);
            map1.put("date", today);
            map1.put("category", "SKY");
            String retmap1 = wService.selectWeatherListDTO(map1).toString();
            
            // 비 눈 보통 구분용 category PTY
            map1.put("category", "PTY");
            String retmap2 = wService.selectWeatherListDTO(map1).toString();
    
            // 온도 category TMP
            map1.put("category", "TMP");
            String retmap3 = wService.selectWeatherListDTO(map1).toString();
    
            if(retmap2.equals("3")  ){
                map.put("weather", "눈");
                map.put("result", "D");
            }
            else if(
                retmap2.equals("1") ||
                retmap2.equals("2") ||
                retmap2.equals("4")
            ){
                map.put("weather", "비");
                map.put("result", "C");
            }
            else{
                if(Integer.parseInt(retmap3) >= 28 ){
                    map.put("weather", "더움");
                    map.put("result", "E");
                }
                else if(Integer.parseInt(retmap3) < 10){
                    map.put("weather", "추움");
                    map.put("result", "F");
                }
                else{
                    if(retmap1.equals("1")){
                        map.put("weather", "맑음");
                        map.put("result", "A");
                    }
                    else if(
                        retmap1.equals("3") ||
                        retmap1.equals("4")
                    ){
                        map.put("weather", "흐림");
                        map.put("result", "B");
                    }
                }
            }
            
            String wc = (String) map.get("result");
            
            System.out.println("api 확인용@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            map.put("backurl", GlobalProperties.servepath +"/weather/imageselect?code="+wc);
            map.put("status", 200);
            return map;
            
            
        } 
        catch (Exception e) {
            e.printStackTrace();
            map.put("status", 0);
            map.put("result", e);
            map.put("backurl", GlobalProperties.servepath + "/img/aboutUs/serviceTime.png");
            return map;
        }
    };
}
