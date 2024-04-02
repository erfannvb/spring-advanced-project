package nvb.dev.springadvancedproject.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_books")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookEntity extends AuditEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "title cannot be null.")
    @NotBlank(message = "title cannot be blank.")
    @Column(unique = true)
    private String title;

    @NotNull(message = "isbn cannot be null.")
    @NotBlank(message = "isbn cannot be blank.")
    private String isbn;

    private int pages;

    private float rating;

    @ElementCollection
    @CollectionTable(name = "book_genres", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genres")
    private List<String> genres;

    @Column(name = "published_year")
    @Temporal(TemporalType.DATE)
    private LocalDate publishedYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonIgnore
    private AuthorEntity author;

}
