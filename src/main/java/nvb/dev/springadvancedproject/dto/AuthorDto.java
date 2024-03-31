package nvb.dev.springadvancedproject.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import nvb.dev.springadvancedproject.model.BookEntity;

import java.util.Set;

@Data
public class AuthorDto {

    private Long id;

    @NotNull(message = "firstName cannot be null.")
    @NotBlank(message = "firstName cannot be blank.")
    private String firstName;

    @NotNull(message = "lastName cannot be null.")
    @NotBlank(message = "lastName cannot be blank.")
    private String lastName;

    private Integer age;
    private Set<BookEntity> books;

}