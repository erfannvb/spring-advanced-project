package nvb.dev.springadvancedproject.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.AuthorEntity;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class BookDto {

    private Long id;

    @NotNull(message = "title cannot be null.")
    @NotBlank(message = "title cannot be blank.")
    private String title;

    @NotNull(message = "isbn cannot be null.")
    @NotBlank(message = "isbn cannot be blank.")
    private String isbn;

    private int pages;

    @Size(max = 5)
    private float rating;

    private List<String> genres;

    @Temporal(TemporalType.DATE)
    private LocalDate publishedYear;

    private AuthorEntity author;

}
