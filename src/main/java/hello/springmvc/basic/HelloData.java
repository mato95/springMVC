/**
 * Author : 박강훈
 * Date : 2023/06/06
 * Content : 스프링 MVC 1편 - HTTP 요청 파라미터 - @ModelAttribute
 * Line : 1 ~ 39
 */

package hello.springmvc.basic;

import lombok.Data;

/*@Data
* @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequireArgsConstructor
* 를 자동으로 적용해준다.
*/

@Data
public class HelloData {
    private String username;
    private int age;
}
