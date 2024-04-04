package nvb.dev.springadvancedproject.mapper;

import nvb.dev.springadvancedproject.dto.LoanDto;
import nvb.dev.springadvancedproject.model.LoanEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {LoanMapperImpl.class})
class LoanMapperTest {

    @Autowired
    LoanMapper loanMapper;

    @Test
    void toLoanDto() {
        LoanDto loanDto = loanMapper.toLoanDto(anyValidLoanEntity());
        assertEquals(anyValidLoanEntity().getId(), loanDto.getId());
        assertEquals(anyValidLoanEntity().getLoanDate(), loanDto.getLoanDate());
        assertEquals(anyValidLoanEntity().getDueDate(), loanDto.getDueDate());
        assertEquals(anyValidLoanEntity().getReturnDate(), loanDto.getReturnDate());
        assertEquals(anyValidLoanEntity().getMember(), loanDto.getMember());
        assertEquals(anyValidLoanEntity().getBook(), loanDto.getBook());
    }

    @Test
    void toLoanDto_Null_Fields() {
        LoanDto loanDto = loanMapper.toLoanDto(anyInvalidLoanEntity());
        assertNull(loanDto.getId());
        assertNull(loanDto.getLoanDate());
        assertNull(loanDto.getDueDate());
        assertNull(loanDto.getReturnDate());
        assertNull(loanDto.getMember());
        assertNull(loanDto.getBook());
    }

    @Test
    void toLoanDto_Null() {
        LoanDto loanDto = loanMapper.toLoanDto(null);
        assertNull(loanDto);
    }

    @Test
    void toLoanEntity() {
        LoanEntity loanEntity = loanMapper.toLoanEntity(anyValidLoanDto());
        assertEquals(anyValidLoanDto().getId(), loanEntity.getId());
        assertEquals(anyValidLoanDto().getLoanDate(), loanEntity.getLoanDate());
        assertEquals(anyValidLoanDto().getDueDate(), loanEntity.getDueDate());
        assertEquals(anyValidLoanDto().getReturnDate(), loanEntity.getReturnDate());
        assertEquals(anyValidLoanDto().getMember(), loanEntity.getMember());
        assertEquals(anyValidLoanDto().getBook(), loanEntity.getBook());
    }

    @Test
    void toLoanEntity_Null_Fields() {
        LoanEntity loanEntity = loanMapper.toLoanEntity(anyInvalidLoanDto());
        assertNull(loanEntity.getId());
        assertNull(loanEntity.getLoanDate());
        assertNull(loanEntity.getDueDate());
        assertNull(loanEntity.getReturnDate());
        assertNull(loanEntity.getMember());
        assertNull(loanEntity.getBook());
    }

    @Test
    void toLoanEntity_Null() {
        LoanEntity loanEntity = loanMapper.toLoanEntity(null);
        assertNull(loanEntity);
    }
}