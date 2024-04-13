package nvb.dev.springadvancedproject.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.model.MemberEntity;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
//@JsonRootName(value = "loan")
public class LoanDto implements Serializable {

    private Long id;

    @Temporal(TemporalType.DATE)
    private LocalDate loanDate;

    @Temporal(TemporalType.DATE)
    private LocalDate dueDate;

    @Temporal(TemporalType.DATE)
    private LocalDate returnDate;

    private MemberEntity member;

    private BookEntity book;

}
