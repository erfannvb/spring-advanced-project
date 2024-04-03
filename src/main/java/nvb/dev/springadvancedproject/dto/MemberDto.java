package nvb.dev.springadvancedproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.Address;
import nvb.dev.springadvancedproject.model.BookEntity;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class MemberDto implements Serializable {

    private Long id;

    @NotNull(message = "fullName cannot be null.")
    @NotBlank(message = "fullName cannot be blank.")
    private String fullName;

    @NotNull(message = "email cannot be null.")
    @NotBlank(message = "email cannot be blank.")
    @Email
    private String email;

    private Address address;

    private List<BookEntity> borrowedBooks;

}
