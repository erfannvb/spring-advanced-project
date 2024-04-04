package nvb.dev.springadvancedproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.LoanDto;
import nvb.dev.springadvancedproject.mapper.LoanMapper;
import nvb.dev.springadvancedproject.model.LoanEntity;
import nvb.dev.springadvancedproject.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/loan")
@RequiredArgsConstructor
@Validated
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper loanMapper;

    @PostMapping(path = "/submit/member/{memberId}/book/{bookId}")
    public ResponseEntity<LoanDto> loanBookToMember(@PathVariable long memberId,
                                                    @PathVariable long bookId,
                                                    @RequestBody @Valid LoanDto loanDto) {
        LoanEntity loanEntity = loanMapper.toLoanEntity(loanDto);
        LoanEntity loanedBookToMember = loanService.loanBookToMember(memberId, bookId, loanEntity);
        return new ResponseEntity<>(loanMapper.toLoanDto(loanedBookToMember), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/delete/member/{memberId}/book/{bookId}")
    public void deleteLoan(@PathVariable long memberId, @PathVariable long bookId) {
        loanService.deleteLoan(memberId, bookId);
    }

}