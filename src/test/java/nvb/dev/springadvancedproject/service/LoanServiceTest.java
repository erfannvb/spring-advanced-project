package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.book.BookNotFoundException;
import nvb.dev.springadvancedproject.exception.member.MemberNotFoundException;
import nvb.dev.springadvancedproject.model.LoanEntity;
import nvb.dev.springadvancedproject.repository.BookRepository;
import nvb.dev.springadvancedproject.repository.LoanRepository;
import nvb.dev.springadvancedproject.repository.MemberRepository;
import nvb.dev.springadvancedproject.service.impl.LoanServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static nvb.dev.springadvancedproject.MotherObject.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
class LoanServiceTest {

    @Mock
    LoanRepository loanRepository;

    @Mock
    BookRepository bookRepository;

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    LoanServiceImpl loanService;

    @Test
    void testLoanBookToMemberWorksSuccessfully() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(anyValidLoanEntity());

        LoanEntity loanEntity = loanService.loanBookToMember(1L, 1L, anyValidLoanEntity());

        assertNotNull(loanEntity);
        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(loanRepository, atLeastOnce()).save(any(LoanEntity.class));
    }

    @Test
    void testLoanBookToMemberThrowsMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(anyValidLoanEntity());

        assertThrows(MemberNotFoundException.class, () ->
                loanService.loanBookToMember(1L, 1L, anyValidLoanEntity()));

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, never()).findById(anyLong());
        verify(loanRepository, never()).save(any(LoanEntity.class));
    }

    @Test
    void testLoanBookToMemberThrowsBookNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(loanRepository.save(any(LoanEntity.class))).thenReturn(anyValidLoanEntity());

        assertThrows(BookNotFoundException.class, () ->
                loanService.loanBookToMember(1L, 1L, anyValidLoanEntity()));

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(loanRepository, never()).save(any(LoanEntity.class));
    }

    @Test
    void testLoanBookToMemberThrowsEntityNotStorableException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));
        when(loanRepository.save(any(LoanEntity.class))).thenThrow(DataIntegrityViolationException.class);

        assertThrows(EntityNotStorableException.class, () ->
                loanService.loanBookToMember(1L, 1L, anyValidLoanEntity()));

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(loanRepository, atLeastOnce()).save(any(LoanEntity.class));
    }

    @Test
    void testDeleteLoanCanDeleteTheExistingLoan() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));

        loanService.deleteLoan(1L, 1L);

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(loanRepository, atLeastOnce()).deleteByMemberIdAndBookId(anyLong(), anyLong());
    }

    @Test
    void testDeleteLoanThrowsMemberNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(bookRepository.findById(anyLong())).thenReturn(Optional.of(anyValidBookEntity()));

        assertThrows(MemberNotFoundException.class, () -> loanService.deleteLoan(1L, 1L));

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, never()).findById(anyLong());
        verify(loanRepository, never()).deleteByMemberIdAndBookId(anyLong(), anyLong());
    }

    @Test
    void testDeleteLoanThrowsBookNotFoundException() {
        when(memberRepository.findById(anyLong())).thenReturn(Optional.of(anyValidMemberEntity()));
        when(bookRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(BookNotFoundException.class, () -> loanService.deleteLoan(1L, 1L));

        verify(memberRepository, atLeastOnce()).findById(anyLong());
        verify(bookRepository, atLeastOnce()).findById(anyLong());
        verify(loanRepository, never()).deleteByMemberIdAndBookId(anyLong(), anyLong());
    }

}