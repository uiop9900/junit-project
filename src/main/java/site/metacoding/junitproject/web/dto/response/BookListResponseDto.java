package site.metacoding.junitproject.web.dto.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import site.metacoding.junitproject.domain.Book;

@Getter
public class BookListResponseDto {
	List<BookResponseDto> items;

	@Builder
	public BookListResponseDto(List<BookResponseDto> bookList) {
		this.items = bookList;
	}

}
