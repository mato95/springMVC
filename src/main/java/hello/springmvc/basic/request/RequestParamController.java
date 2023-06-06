/**
 * Author : 박강훈
 * Date : 2023/06/06
 * Content : 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술(HTTP 요청 - 기본, 헤더 조회)
 * Line : 1 ~ 39
 */

package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;


@Slf4j
@Controller
public class RequestParamController {

    /**
     * 반환 타입이 없으면서 이렇게 응답에 값을 직접 집어넣으면, view 조회X
     */

    @RequestMapping("/request-param-v1")
    //request와 response를 받아옵니다.
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //username 세팅
        String username = request.getParameter("username");
        //age 세팅, request.getParameter는 String으로 받아오기 때문에 int형으로 파싱해줍니다.
        int age = Integer.parseInt(request.getParameter("age"));
        // username과 age를 로그로 불러와 콘솔창에 출력해줍니다.
        log.info("username={}", username);
        log.info("age={}", age);

        //response를 작성해줍니다. return 값은 원래 void이지만 response를 작성해서 OK라는 문자가 뷰에 렌더링 됩니다.
        response.getWriter().write("OK");
    }

    /**
     * @RequestParam 사용
     * - 파라미터 이름으로 바인딩
     * @ResponseBody 추가
     * - View 조회를 무시하고, HTTP message body에 직접 해당 내용 입력 */

    /**
     * Author : 박강훈
     * Date : 2023/06/06
     * Content : 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술(HTTP 요청 파라미터 - @RequestParam)
     * Line : 51 ~
     */
    //해당 컨트롤러는 RESTController가 아니지만 @ResponseBody를 사용해 뷰 이름 검색이 아닌 문자열로 return으로 반환해줄 수 있다.
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            //RequestParam으로 파라미터를 설정해준다
            @RequestParam("username") String memberName,
            @RequestParam("age") int memberAge){

        log.info("username={}, age={}", memberName, memberAge);
        return "OK";
    }
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            //파라미터 이름이 변수명과 같으면 @RequestParam("이 부분") 생략 가능하다.
            @RequestParam String username,
            @RequestParam int age){

        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-v4")
    //String , int , Integer 등의 단순 타입이면 @RequestParam 도 생략 가능하다.
    public String requestParamV4(String username, int age){
        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(
            //@RequestParam의 required 옵션을 통해 해당 값이 필수 값인지 아닌지 구별 할 수 있음.
            // 요청 파라미터 스펙이 안맞기 때문에
            // True면 해당 값이 없을때 오류를 뱉음(Bad Request = 400)
            // False면 없으면 500에러가 나오는데 이는 스펙 오류가 아닌 서버 오류이다.
            // int는 기본형이기 때문에 null을 넣을 수 없기 때문에 0이라도 들어가야 한다.
            // 따라서 int를 Integer로 바꿔주면 null이 허용되기 떄문에 해당 값이 쿼리 파라미터로 들어가지 않아도 잘 잘동한다.
            // null과 ""는 다르다(빈 문자로 인식하기 때문에 오류 안뱉음, 조심해야함)
            @RequestParam(required = true) String username,
            @RequestParam(required = false) Integer age){
            //Integer는 기본형이 아니고 객체형이기 때문에 null이 가능하다
            //아래 주석을 풀어서 확인해보자
            //null 불가
            //int a = null;
            //null 가능
            //Integer b = null;
        log.info("username={}, age={}", username, age);
        return "OK";

    }
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(
            //@RequestParam 옵션 중에 defaultValue가 있는데 만약 값이 없으면 기본으로 값을 지정해주는 옵션이다.
            //빈 문자의 경우에도 알아서 인식을 해 기본값 설정을 해준다.
            @RequestParam(required = true, defaultValue = "guest") String username,
            //defaultValue가 존재하기 때문에 Integer로 사용할 필요가 없다.
            @RequestParam(required = false, defaultValue = "-1")  int age){

        log.info("username={}, age={}", username, age);
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    //파라미터로 받아올 Map 설정
    //파라미터를 Map 뿐만 아니라 MultiValueMap으로도 조회할 수 있다.
    public String requestParamMap(@RequestParam Map<String, Object> paramMap){
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "OK";
    }

    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    // @RequestParam을 @ModelAttribute로 바꿀 수 있음
    // 훨씬 간편해짐 Model 객체가 생성되고 파라미터 값도 모두 들어가있음
    /*
    스프링 MVC는 @ModelAttribute가 있으면 다음을 실행한다.
    1. HelloData 객체를 생성한다.
    2. 요청 파라미터의 이름으로 HelloData 객체의 프로퍼티를 찾는다. 그리고 해당 프로퍼티의 setter를 호출해서 파라미터의 값을
       입력(바인딩)한다.
       예) 파라미터 이름이 username이면 setUsername() 메서드를 찾아서 호출하고 값을 입력한다.
    프로퍼티 : 객체에 getUsername(), setUsername() 메서드를 가지고 있으면 username이라는 프로퍼티를 가지고 있다.
       예) getXXX → XXX, setXXX → XXX get과 set을 뺀 값을 자동으로 프로퍼티로..
    BindingException → 숫자가 들어가야하는데 문자가 들어가는 경우 바인딩 익셉션 발생(타입이 안맞을 경우)
    → 검증에서 다룸
    @ModelAttribute 안에 name도 넣을 수 있다.
     */
    public String modelAttributeV1(@ModelAttribute HelloData helloData){
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        //@Data에  @ToSpring이 내장되어있음 객체를 찍었을 때 String으로 자동 변환
        log.info("helloData={}", helloData);
        return "OK";
    }

    /*
    @ModelAttribute 생략 가능하다.
    @RequestParam도 생략할 수 있으므로 혼란이 발생할 수 있다.
    스프링은 해당 생략 시 다음과 같은 규칙을 적용한다.
    String, int, Integer와 같은 단순 타입 → @RequestParam
    나머지 → @ModelAttribute (argument resolver로 지정해둔 타입 외)
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "OK";
    }
}
