package nvb.dev.springadvancedproject.dto;

import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.model.UserEntity;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
public class LoanDto implements Serializable {

    private Long id;

    @Temporal(TemporalType.DATE)
    private LocalDate loanDate;

    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    private UserEntity user;

    private BookEntity book;

}
