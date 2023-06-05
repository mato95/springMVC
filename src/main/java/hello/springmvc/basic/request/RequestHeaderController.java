package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;
/*
   Author : 박강훈
   Date :   2023/06/05
   Content : 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술(HTTP 요청 - 기본, 헤더 조회)
*/


//다음 코드를 자동으로 생성해서 로그를 생성해줌. 개발자는 편리하게 log라고 사용하면 된다.
@Slf4j
@RestController
public class RequestHeaderController {
    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          //HttpMethod -> Http 메서드를 조회한다.
                          HttpMethod httpMethod,
                          //Locale -> Locale 정보를 조회한다.
                          Locale locale,
                          //RequestHeader
                          //MultiValueMap은 Map과 비슷한 기능을 하지만 하나의 키에 여러 개의 값을 가질 수 있는 Map이다.
                          // 예) keyA=valueA&keyA=value2
                          @RequestHeader MultiValueMap<String, String> headerMap,
                          // 특정 HTTP 헤더를 조회한다.
                          // 속성
                          // 1. 필수 값 여부 : required
                          // 2. 기본 값 속성 : defaultValue
                          @RequestHeader("host") String host,
                          // CookieValue : 특정 쿠키를 조회한다.
                          // 속성
                          // 1. 필수 값 여부 : required
                          // 2. 기본 값 : defaultValue
                          @CookieValue(value = "myCookie", required = false) String cookie
                          ) {
        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);
        return "OK";


    }
}
