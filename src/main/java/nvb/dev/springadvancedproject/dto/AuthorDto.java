package nvb.dev.springadvancedproject.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.BookEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@JsonRootName(value = "author")
public class AuthorDto implements Serializable {

    private Long id;

    @NotNull(message = "firstName cannot be null.")
    @NotBlank(message = "firstName cannot be blank.")
    private String firstName;

    @NotNull(message = "lastName cannot be null.")
    @NotBlank(message = "lastName cannot be blank.")
    private String lastName;

    private Integer age;

    private List<BookEntity> books;

}
