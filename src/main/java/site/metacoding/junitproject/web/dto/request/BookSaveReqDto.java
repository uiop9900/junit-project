package site.metacoding.junitproject.web.dto.request;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import site.metacoding.junitproject.domain.Book;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
@Setter // Controller에서 값을 받아서 이 dto에 넣어준다.
public class BookSaveReqDto {
    @Size(min = 1, max = 50)
    @NotBlank
    private String title;
    @Size(min = 2, max = 20)
    @NotBlank
    private String author;

    public Book toEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .build();
    }
}
