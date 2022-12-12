package site.metacoding.junitproject.web;

import java.util.List;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.HateoasPageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import site.metacoding.junitproject.service.BookService;
import site.metacoding.junitproject.web.dto.response.BookListResponseDto;
import site.metacoding.junitproject.web.dto.response.BookResponseDto;
import site.metacoding.junitproject.web.dto.request.BookSaveReqDto;
import site.metacoding.junitproject.web.dto.response.CMRespDto;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
public class BookApiController {

    private final BookService bookService;

    // 1. 책 등록
    @PostMapping("/api/v1/book")
    public ResponseEntity<?> saveBook(@Valid @RequestBody BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            System.out.println("==========================");
            System.out.println(errorMap.toString());
            System.out.println("==========================");
            throw new RuntimeException(errorMap.toString());
        }

        BookResponseDto bookResponseDto = bookService.책등록하기(bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build(), HttpStatus.CREATED); // 201 -> insert
    }


    // 1. 책 등록
    @PostMapping("/api/v2/book")
    public ResponseEntity<?> saveBookV2(@RequestBody BookSaveReqDto bookSaveReqDto) {

        BookResponseDto bookResponseDto = bookService.책등록하기(bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 저장 성공").body(bookResponseDto).build(), HttpStatus.CREATED); // 201 -> insert
    }

    // 2. 책 목록보기
    @GetMapping("/api/v1/book")
    public ResponseEntity<?> getBookList() {
        final BookListResponseDto bookList = bookService.책목록보기();
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 목록보기 성공").body(bookList).build(), HttpStatus.OK); // 200 -> ok
    }

    // 3.책 한건 보기
    @GetMapping("/api/v1/book/{id}")
    public ResponseEntity<?> getBookOne(@PathVariable Long id) {
        BookResponseDto responseDto = bookService.책한건보기(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 한건보기 성공").body(responseDto).build(), HttpStatus.OK); // 200 -> ok
    }

    // 4. 책 삭제하기
    @DeleteMapping("/api/v1/book/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable Long id) {
        bookService.책삭제하기(id);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 삭제 성공").body(null).build(), HttpStatus.OK); // 응답에 body가 있어서 OK함.
    }

    // 5. 책 수정하기
    @PutMapping("/api/v1/book/{id}")
    public ResponseEntity<?> updateBook(@PathVariable Long id, @RequestBody @Valid BookSaveReqDto bookSaveReqDto, BindingResult bindingResult) { // json으로 받기 위해 requestBody 사용한다.

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for(FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }

            throw new RuntimeException(errorMap.toString());
        }
        BookResponseDto bookResponseDto = bookService.책수정하기(id, bookSaveReqDto);
        return new ResponseEntity<>(CMRespDto.builder().code(1).msg("글 수정하기 성공").body(null).build(), HttpStatus.OK); // 응답에 body가 있어서 OK함.
    }

}
