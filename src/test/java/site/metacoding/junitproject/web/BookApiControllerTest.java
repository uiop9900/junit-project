package site.metacoding.junitproject.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;

// 통합테스트(모든 레이어를 한번에 테스트 하는 테스트) -> 추후 서버 배포 전에 통합 서비스 하나만 돌리고 확인 후 서버에 올리기 위해 통합테스트 작성한다.
// 컨트롤러만 하고 싶으면 Mocking 해야한다.
@SpringBootTest
class BookApiControllerTest {
    // Controller - 클라이언트와 테스트

    @Autowired
    private BookService bookService;

    @Autowired
    private TestRestTemplate restTemplate;

    private static ObjectMapper om;
    private static HttpHeaders headers;

    @BeforeAll
    public static void init() {
        om = new ObjectMapper();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void saveBook_test() throws Exception{
        // given
        BookSaveReqDto bookSaveReqDto = new BookSaveReqDto();
        bookSaveReqDto.setTitle("spring");
        bookSaveReqDto.setAuthor("jiaLEE");

        String body = om.writeValueAsString(bookSaveReqDto); // json으로 값을 보내기 위해 ObjectMapper 이용.

        // when
        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange("/api/v1/book", HttpMethod.POST, request, String.class);
        System.out.println(response.getBody());

        // then
        DocumentContext dc = JsonPath.parse(response.getBody());
        String title = dc.read("$.body.title");
        String author = dc.read("$.body.author");

        // then
        org.assertj.core.api.Assertions.assertThat(title).isEqualTo("spring");
        org.assertj.core.api.Assertions.assertThat(author).isEqualTo("jiaLEE");
    }


}