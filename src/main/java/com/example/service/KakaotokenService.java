package com.example.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.config.GlobalProperties;

@Service
public class KakaotokenService {

    private final String GRANT_TYPE= "authorization_code";
    private final String CLIENT_ID = "c51973de317cdceb44e5847d161665bd";
    private final String REDIRECT_URI= "http://3.38.209.149:8080" + GlobalProperties.servepath + "/kakao/home.do";
    private final String TOKEN_URL = "https://kauth.kakao.com/oauth/token";
    
    public String getKakaoOAuthToken(String code){

        System.out.println(REDIRECT_URI);

        RestTemplate restTemplate = new RestTemplate();

        try{

            //HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<String> request = new HttpEntity<String>(headers);

            // URI 빌더 사용
            UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(TOKEN_URL)
            .queryParam("grant_type", GRANT_TYPE)
            .queryParam("client_id", CLIENT_ID)
            .queryParam("redirect_uri", REDIRECT_URI)
            .queryParam("code", code);

            // 요청 URI과 헤더를 같이 전송
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    uriComponentsBuilder.toUriString(),
                    HttpMethod.POST,
                    request,
                    String.class
            );

            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                return responseEntity.getBody();
            }
            
            return "error";
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
