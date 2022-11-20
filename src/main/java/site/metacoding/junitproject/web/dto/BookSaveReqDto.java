package site.metacoding.junitproject.web.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.junitproject.domain.Book;

@Data
@Getter
@Setter // Controller에서 값을 받아서 이 dto에 넣어준다.
public class BookSaveReqDto {
    private String title;
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
