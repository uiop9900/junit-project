package site.metacoding.junitproject.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//@DataJpaTest
@ExtendWith(MockitoExtension.class) // 가짜 메모리 환경
class BookServiceTest {
    //Service -  기능들이 트랜잭션을 잘 타는지
    @InjectMocks // @Mock으로 생성된 가짜 객체들이 BookSerivce에 주입된다.
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;

    @Mock
    private MailSender mailSender;

    // service를 테스트하고 싶은데 repository 레어어도 같이 테스트된다. -> 독립적인 테스트가 되지 못한다.
    @Test
    public void 책등록하기_테스트() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("메타코딩");

        // stub - 행동정의(가설) : 아래의 객체모두 가짜
        when(bookRepository.save(any())).thenReturn(dto.toEntity()); // 실제 엔티티 넣으면 해시값이 달라서 오류난다.
        when(mailSender.send()).thenReturn(true);

        // when
        BookResponseDto bookResponseDto = bookService.책등록하기(dto);

        // then
        //assertEquals(dto.getTitle(), bookResponseDto.getTitle()); 어떤게 기대값인지, 결과값인지 보기 어려워서 비추함.
        //assertEquals(dto.getAuthor(), bookResponseDto.getAuthor());
        Assertions.assertThat(dto.getTitle()).isEqualTo(bookResponseDto.getTitle());
        Assertions.assertThat(dto.getAuthor()).isEqualTo(bookResponseDto.getAuthor());
    }

    @Test
    public void 책목록보기_테스트() {
        // given (파라미터로 들어올 데이터)

        // stub (가설)
        List<Book> books = new ArrayList<>();
        books.add(new Book(1L, "junit", "메타코딩"));
        books.add(new Book(2L, "Spring", "겟인데어"));
        when(bookRepository.findAll()).thenReturn(books);

        // when (실행)
        List<BookResponseDto> dtos = bookService.책목록보기();

        //print
        dtos.stream().forEach(
                dto -> {
                    System.out.println("============test");
                    System.out.println(dto.getId());
                    System.out.println(dto.getTitle());
                }
        );

        // then (검증)
        Assertions.assertThat(dtos.get(0).getTitle()).isEqualTo(books.get(0).getTitle());
        Assertions.assertThat(dtos.get(0).getAuthor()).isEqualTo(books.get(0).getAuthor());
        Assertions.assertThat(dtos.get(1).getTitle()).isEqualTo(books.get(1).getTitle());
        Assertions.assertThat(dtos.get(1).getAuthor()).isEqualTo(books.get(1).getAuthor());
    }
}