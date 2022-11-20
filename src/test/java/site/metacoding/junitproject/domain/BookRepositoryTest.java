package site.metacoding.junitproject.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest //db와 관련된 컴포넌트들만 메모리에 로딩 -> Controller, Service 관련된건 메모리에 올라가지 않는다. -> 단위테스트
class BookRepositoryTest { // Repository - DB와 관련되 테스트를 진행한다.

    @Autowired // DI
    private BookRepository bookRepository;

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

    }
    // 2. 책 목록 보기

    // 3. 책 한 건 보기

    // 4. 책 수정

    // 5. 책 석제
}