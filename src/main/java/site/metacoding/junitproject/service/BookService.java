package site.metacoding.junitproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@Service
@RequiredArgsConstructor // 생성자로 주입
public class BookService {
	private final BookRepository bookRepository; // final 로 선언되면 객체가 생성되는 시점에 무조건 값이 있어야 한다.

	// 1. 책 등록
	@Transactional(rollbackFor = RuntimeException.class) // RuntimeExeption이 터지면 롤백한다.
	public BookResponseDto 책등록하기(BookSaveReqDto dto) {
		final Book bookPS = bookRepository.save(dto.toEntity());
		//return bookPS; // 엔티티들끼리 연관관계가 있을경우, 저장된 객체를 바로 Controller단으로 보내는 건 위험하다 -> Service단에서 다 막는다. -> 무조건 dto로 감싼다.
		return new BookResponseDto().toDto(bookPS);
	}

	// 2. 책 목록보기

	// 3. 책 한 건 보기

	// 4. 책 삭제

	// 5. 책 수정
}
