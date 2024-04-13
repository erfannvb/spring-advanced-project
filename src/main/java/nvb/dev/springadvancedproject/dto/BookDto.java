package nvb.dev.springadvancedproject.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.AuthorEntity;
import nvb.dev.springadvancedproject.model.LoanEntity;
import nvb.dev.springadvancedproject.model.MemberEntity;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
//@JsonRootName(value = "book")
public class BookDto implements Serializable {

    private Long id;

    @NotNull(message = "title cannot be null.")
    @NotBlank(message = "title cannot be blank.")
    private String title;

    @NotNull(message = "isbn cannot be null.")
    @NotBlank(message = "isbn cannot be blank.")
    private String isbn;

    private int pages;

    private float rating;

    private List<String> genres;

    @Temporal(TemporalType.DATE)
    private LocalDate publishedYear;

    private AuthorEntity author;

    private MemberEntity member;

    private List<LoanEntity> loans;

}
