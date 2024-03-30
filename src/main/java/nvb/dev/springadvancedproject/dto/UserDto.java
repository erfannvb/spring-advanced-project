package nvb.dev.springadvancedproject.dto;

import lombok.Data;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.model.UserRole;

import java.util.Set;

@Data
public class UserDto {

    private String id;
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private UserRole userRole;
    private Set<BookEntity> books;

}
