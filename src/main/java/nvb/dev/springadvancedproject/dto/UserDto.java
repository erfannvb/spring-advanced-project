package nvb.dev.springadvancedproject.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.UserRole;

@Data
@Builder
@JsonRootName(value = "user")
public class UserDto {

    private String id;

    @NotNull(message = "firstName cannot be null.")
    @NotBlank(message = "firstName cannot be blank.")
    private String firstName;

    @NotNull(message = "lastName cannot be null.")
    @NotBlank(message = "lastName cannot be blank.")
    private String lastName;

    @NotNull(message = "username cannot be null.")
    @NotBlank(message = "username cannot be blank.")
    private String username;

    @NotNull(message = "email cannot be null.")
    @NotBlank(message = "email cannot be blank.")
    @Email
    private String email;

    @NotNull(message = "password cannot be null.")
    @NotBlank(message = "password cannot be blank.")
    @Size(min = 3, message = "password must at least contain 3 characters.")
    private String password;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
