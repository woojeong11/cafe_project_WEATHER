package com.example.service;

import java.io.UnsupportedEncodingException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.config.GlobalProperties;

@Service
public class ResisterMail implements MailServiceInter {

    @Autowired
    JavaMailSender emailsender;

    private String ePw; // 인증번호

    // 메일 내용 작성
    @Override
    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = emailsender.createMimeMessage();

		System.out.println("--------------------------------A----------------------------------------");
		message.addRecipients(RecipientType.TO, to);// 보내는 대상
		message.setSubject("cafe weather 비밀번호 찾기 이메일 인증");// 제목
		
		System.out.println("--------------------------------B----------------------------------------");
		String msgg = "";
		msgg += "<div style='margin:100px;'>";
		msgg += "<img src=\"" + GlobalProperties.servepath + "/img/aboutUs/Logo.png\" style=\"width: 20%\" />";
		msgg += "<h1> 안녕하세요</h1>";
		msgg += "<h1> cafe weather 입니다</h1>";
		msgg += "<br>";
		msgg += "<p>아래 코드를 비밀번호 찾기 창으로 돌아가 입력해주세요<p>";
		msgg += "<br>";
		msgg += "<p>항상 양질의 서비스를 제공하겠습니다. 감사합니다!<p>";
		msgg += "<br>";
		msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
		msgg += "<h3 style='color:blue;'>비밀번호 인증 코드입니다.</h3>";
		msgg += "<div style='font-size:130%'>";
		msgg += "CODE : <strong>";
		msgg += ePw + "</strong><div><br/> "; // 메일에 인증번호 넣기
		msgg += "</div>";
		message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
		// 보내는 사람의 이메일 주소, 보내는 사람 이름
		message.setFrom(new InternetAddress("ff5979@naver.com", "cafe_weather_Admin"));// 보내는 사람
		
		System.out.println("--------------------------------C----------------------------------------");
		return message;
    }

    // 랜덤 인증 코드 작성
    @Override
    public String createKey() {
        StringBuffer key = new StringBuffer();
		Random rnd = new Random();

		for (int i = 0; i < 8; i++) { // 인증코드 8자리
			int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

			switch (index) {
			case 0:
				key.append((char) ((int) (rnd.nextInt(26)) + 97));
				// a~z (ex. 1+97=98 => (char)98 = 'b')
				break;
			case 1:
				key.append((char) ((int) (rnd.nextInt(26)) + 65));
				// A~Z
				break;
			case 2:
				key.append((rnd.nextInt(10)));
				// 0~9
				break;
			}
		}

		return key.toString();
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
	// MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
	// 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!
    @Override
    public String sendSimpleMessage(String to) throws Exception {
        
        ePw = createKey(); // 랜덤 인증번호 생성

		MimeMessage message = createMessage(to); // 메일 발송

		System.out.println(to);

		try {// 예외처리
			System.out.println("--------------------------success----------------------------------");
			emailsender.send(message);
		} 
        catch (MailException es) {
			es.printStackTrace();
		}

		return ePw; // 메일로 보냈던 인증 코드를 서버로 반환
    }
    
}
