package nvb.dev.springadvancedproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.dto.LoanDto;
import nvb.dev.springadvancedproject.mapper.LoanMapper;
import nvb.dev.springadvancedproject.model.LoanEntity;
import nvb.dev.springadvancedproject.service.LoanService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/loan")
@RequiredArgsConstructor
@Tag(name = "Loan Controller", description = "Performing Operations on Loans")
public class LoanController {

    private final LoanService loanService;
    private final LoanMapper loanMapper;

    @Operation(summary = "Loan Book to Member", description = "Loans a book to the member")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Creation of Loan"),
            @ApiResponse(responseCode = "404", description = "Book Not Found"),
            @ApiResponse(responseCode = "404", description = "Member Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @PostMapping(path = "/submit/member/{memberId}/book/{bookId}")
    public ResponseEntity<LoanDto> loanBookToMember(@PathVariable long memberId,
                                                    @PathVariable long bookId,
                                                    @RequestBody LoanDto loanDto) {
        LoanEntity loanEntity = loanMapper.toLoanEntity(loanDto);
        LoanEntity loanedBookToMember = loanService.loanBookToMember(memberId, bookId, loanEntity);
        return new ResponseEntity<>(loanMapper.toLoanDto(loanedBookToMember), HttpStatus.CREATED);
    }

    @Operation(summary = "Remove Loan", description = "Discards the loan")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Deletion of Loan"),
            @ApiResponse(responseCode = "404", description = "Book Not Found"),
            @ApiResponse(responseCode = "404", description = "Member Not Found"),
            @ApiResponse(responseCode = "400", description = "Bad Request : Unsuccessful Submission")
    })
    @DeleteMapping(path = "/delete/member/{memberId}/book/{bookId}")
    public ResponseEntity<HttpStatus> deleteLoan(@PathVariable long memberId, @PathVariable long bookId) {
        loanService.deleteLoan(memberId, bookId);
        return ResponseEntity.noContent().build();
    }

}
