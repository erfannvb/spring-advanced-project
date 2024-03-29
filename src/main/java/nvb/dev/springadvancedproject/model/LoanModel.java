package nvb.dev.springadvancedproject.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "tbl_loans")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanModel extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "loan_date")
    @Temporal(TemporalType.DATE)
    private LocalDate loanDate;

    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private LocalDate dueDate;

    @Column(name = "return_date")
    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private BookModel bookModel;

}
