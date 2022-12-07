package site.metacoding.junitproject.web.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import site.metacoding.junitproject.domain.Book;

@NoArgsConstructor
@Getter
public class BookResponseDto {
	private Long id;
	private String title;
	private String author;

	@Builder
	public BookResponseDto(Long id, String title, String author) {
		this.id = id;
		this.title = title;
		this.author = author;
	}


//	public BookResponseDto toDto (Book book) {
//		this.id = book.getId();
//		this.title = book.getTitle();
//		this.author = book.getAuthor();
//		return this;
//	}

}
