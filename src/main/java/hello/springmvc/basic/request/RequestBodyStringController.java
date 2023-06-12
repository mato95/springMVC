package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);

        response.getWriter().write("OK");
    }
    
    @PostMapping("/request-body-string-v2")
    //V1과 다르게 InputStream으로 HTTP 요청 메시지 바디의 내용을 직접 조회할 수 있다.
    //OutputStream(Writer) : HTTP 응답 메시지의 바디에 직접 결과 출력
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException{

        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("OK");
    }

    @PostMapping("/request-body-string-v3")
    // HttpEntity<String> 형태로 지정하면 스프링에서 알아서 문자를 인식하고 Body부의 문자를 가져오기 위해
    // HTTP 메시지 컨버터를 동작시킴
    // return 형식으로 HttpEntity를 반환한다.
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException{

        //메시지 바디 뿐 아니라 헤더 부분도 받아올 수 있음
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        return new HttpEntity<>("OK");
    }

    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException{
        log.info("messageBody={}", messageBody);
        return "OK";

    }
}
