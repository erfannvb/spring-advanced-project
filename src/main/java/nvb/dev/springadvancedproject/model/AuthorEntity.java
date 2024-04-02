package nvb.dev.springadvancedproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_authors")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "firstName cannot be null.")
    @NotBlank(message = "firstName cannot be blank.")
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull(message = "lastName cannot be null.")
    @NotBlank(message = "lastName cannot be blank.")
    @Column(name = "last_name", nullable = false)
    private String lastName;

    private Integer age;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore
    private List<BookEntity> books = new ArrayList<>();

}
