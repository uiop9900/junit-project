package site.metacoding.junitproject.web;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.CMRespDto;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(BookSaveReqDto bookSaveReqDto) {
        BookResponseDto bookResponseDto = bookService.책등록하기(bookSaveReqDto);
        CMRespDto<?> cmRespDto = CMRespDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build();
        return new ResponseEntity<>(cmRespDto, HttpStatus.CREATED); // 201 -> insert 가 성공적으로 되었을때 뜬다.
    }

    // 2. 책 목록보기
    public ResponseEntity<?> getBookList() {
        return null;
    }

    // 3. 책 한건 보기
    public ResponseEntity<?> getBookOne() {
        return null;
    }

    // 4. 책 삭제하기
    public ResponseEntity<?> deleteBook() {
        return null;
    }

    // 5. 책 수정하기
    public ResponseEntity<?> updateBook() {
        return null;
    }

}
