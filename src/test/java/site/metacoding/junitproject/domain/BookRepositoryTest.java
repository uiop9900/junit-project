package site.metacoding.junitproject.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //db와 관련된 컴포넌트들만 메모리에 로딩 -> Controller, Service 관련된건 메모리에 올라가지 않는다. -> 단위테스트
class BookRepositoryTest { // Repository - DB와 관련되 테스트를 진행한다.

    @Autowired // DI
    private BookRepository bookRepository;

    // @BeforeAll // 테스트 시작전에 한번만 실행
    @BeforeEach // 각 테스트 시작 전에 한번씩 실행
    public void 데이터준비() {
        String title = "junit";
        String author = "겟인데어";
        Book book = Book.builder()
                .title(title)
                .author(author)
                .build();
        bookRepository.save(book);
    }
    // 매 메소드마다 트랙잭션이 끝난다. 즉, 하나의 Tranx에 데이터준비 + 메소드1 -> 그리고 roll back

    // 1. 책 등록
    @Test
    public void 책등록_test() {
        // given (데이터 준비)
        String title = "junit5";
        String author = "메타코딩";
        Book book = Book.builder()  // 어차피 repo에서는 엔티티를 받아서 저장하는 것이기에 굳이 dto를 사용하지 않아도 된다.
                .title(title)
                .author(author)
                .build();

        // when (테스트 실행)
        Book bookPS = bookRepository.save(book);// db에 book이 저장되고 저장된 book이 반환된다. (영속화된 db)

        // then (검증)
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    } // 트랜잭션 종료 (저장된 데이터를 초기화한다.)


    // 2. 책 목록 보기
    @Test
    public void 책목록보기_test() {
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        List<Book> booksPS = bookRepository.findAll();
        System.out.println("size : =================================== " + booksPS.size());
        // then
        Assertions.assertEquals(title, booksPS.get(0).getTitle());
        Assertions.assertEquals(author, booksPS.get(0).getAuthor());
    }
    // 3. 책 한 건 보기
    @Sql("classpath:db/tableInit.sql")
    @Test
    public void 책한건보기_test() {
        // given
        String title = "junit";
        String author = "겟인데어";

        // when
        Book bookPS = bookRepository.findById(1L).get();

        // then
        assertEquals(title, bookPS.getTitle());
        assertEquals(author, bookPS.getAuthor());
    }
    // 4. 책 수정

    // 5. 책 석제

    @Sql("classpath:db/tableInit.sql") // 이 테스트 실행전 resource 폴더안에 sql 문 실행한다.
    @Test
    public void 책삭졔_test() {
        // given
        Long id = 1L;

        // when
        bookRepository.deleteById(id);

        // then
        assertFalse(bookRepository.findById(id).isPresent()); // 값이 없으니까 false -> 성공
    }
}