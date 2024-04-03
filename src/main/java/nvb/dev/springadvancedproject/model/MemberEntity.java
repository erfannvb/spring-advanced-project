package nvb.dev.springadvancedproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_members")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "fullName cannot be null.")
    @NotBlank(message = "fullName cannot be blank.")
    private String fullName;

    @NotNull(message = "email cannot be null.")
    @NotBlank(message = "email cannot be blank.")
    @Email
    private String email;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "member", fetch = FetchType.EAGER)
    @JsonIgnore
    @Builder.Default
    private List<BookEntity> borrowedBooks = new ArrayList<>();

}
