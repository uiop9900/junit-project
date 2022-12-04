package site.metacoding.junitproject.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSenderStub;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookServiceTest {
    //Service -  기능들이 트랜잭션을 잘 타는지

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void 책등록하기_테스트() {
        // given
        BookSaveReqDto dto = new BookSaveReqDto();
        dto.setTitle("junit");
        dto.setAuthor("메타코딩");

        // stub
        MailSenderStub stub = new MailSenderStub();
        // 가짜로 bookRepository 만들기

        // when
        BookService bookService = new BookService(bookRepository, stub);
        BookResponseDto bookResponseDto = bookService.책등록하기(dto);

        // then
        assertEquals(dto.getTitle(), bookResponseDto.getTitle());
        assertEquals(dto.getAuthor(), bookResponseDto.getAuthor());
    }
}