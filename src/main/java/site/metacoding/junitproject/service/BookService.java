package site.metacoding.junitproject.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.aspectj.bridge.Message;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.metacoding.junitproject.domain.Book;
import site.metacoding.junitproject.domain.BookRepository;
import site.metacoding.junitproject.util.MailSender;
import site.metacoding.junitproject.web.dto.BookResponseDto;
import site.metacoding.junitproject.web.dto.BookSaveReqDto;

@Service
@RequiredArgsConstructor // 생성자로 주입
public class BookService {
	private final BookRepository bookRepository; // final 로 선언되면 객체가 생성되는 시점에 무조건 값이 있어야 한다.
	private final MailSender mailSender;

	// 1. 책 등록
	@Transactional(rollbackFor = RuntimeException.class) // RuntimeExeption이 터지면 롤백한다.
	public BookResponseDto 책등록하기(BookSaveReqDto dto) {
		final Book bookPS = bookRepository.save(dto.toEntity());
		//return bookPS; // 엔티티들끼리 연관관계가 있을경우, 저장된 객체를 바로 Controller단으로 보내는 건 위험하다 -> Service단에서 다 막는다. -> 무조건 dto로 감싼다.
		if (bookPS != null) {
			if (!mailSender.send()) {
				throw new RuntimeException("메일이 전송되지 않습니다.");
			}
		}
		return bookPS.toDto();
	}

	// 2. 책 목록보기
	public List<BookResponseDto> 책목록보기() {
		List<BookResponseDto> dtos = bookRepository.findAll().stream()
				//.map(new BookResponseDto()::toDto) -> 매번 new를 하는게 아니라 toDto가 매번되는 것이기때문에 여러개 객체가 new 되는게 아니라 한 개만 들어간다.
				.map(book -> book.toDto())
				.collect(Collectors.toList());

		//print
		dtos.stream().forEach(
				dto -> {
					System.out.println("============본코드");
					System.out.println(dto.getId());
					System.out.println(dto.getTitle());
				}
		);

		return dtos;
	}

	// 3. 책 한 건 보기
	public BookResponseDto 책한건보기(Long id) {
		final Optional<Book> bookOP = bookRepository.findById(id);
		if (bookOP.isPresent()) {
			return bookOP.get().toDto();
		} else {
			throw new RuntimeException();
		}
	}

	// 4. 책 삭제
	@Transactional(rollbackFor = RuntimeException.class)
	public void 책삭제하기(Long id) { // Controller에서 null체크함, 없는 id가 들어와도 굳이 Exception 날릴 필요 없다.
		bookRepository.deleteById(id);
	}

	// 5. 책 수정
	@Transactional(rollbackFor = RuntimeException.class)
	public void 책수정하기(Long id, BookSaveReqDto dto) {
		final Optional<Book> bookOP = bookRepository.findById(id);
		if (bookOP.isPresent()) { // 찾으면 수정
			final Book book = bookOP.get();
			book.update(dto.getTitle(), dto.getAuthor());
		} else { // 수정할 book이 존재하지 않는다.
				throw new RuntimeException();
		}
		// 메소드 종료시에 더티체킹으로 update된다.
	}
}
