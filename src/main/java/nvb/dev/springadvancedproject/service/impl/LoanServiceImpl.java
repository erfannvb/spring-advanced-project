package nvb.dev.springadvancedproject.service.impl;

import lombok.RequiredArgsConstructor;
import nvb.dev.springadvancedproject.exception.EntityNotStorableException;
import nvb.dev.springadvancedproject.exception.book.BookNotFoundException;
import nvb.dev.springadvancedproject.exception.member.MemberNotFoundException;
import nvb.dev.springadvancedproject.model.BookEntity;
import nvb.dev.springadvancedproject.model.LoanEntity;
import nvb.dev.springadvancedproject.model.MemberEntity;
import nvb.dev.springadvancedproject.repository.BookRepository;
import nvb.dev.springadvancedproject.repository.LoanRepository;
import nvb.dev.springadvancedproject.repository.MemberRepository;
import nvb.dev.springadvancedproject.service.LoanService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    @Override
    public LoanEntity loanBookToMember(long memberId, long bookId, LoanEntity loanEntity) {
        try {
            MemberEntity memberEntity = unwrapMember(memberRepository.findById(memberId), memberId);
            BookEntity bookEntity = unwrapBook(bookRepository.findById(bookId), bookId);
            loanEntity.setMember(memberEntity);
            loanEntity.setBook(bookEntity);
            return loanRepository.save(loanEntity);
        } catch (DataIntegrityViolationException e) {
            throw new EntityNotStorableException();
        }
    }

    @Override
    public void deleteLoan(long memberId, long bookId) {
        if (memberRepository.findById(memberId).isEmpty()) throw new MemberNotFoundException(memberId);
        if (bookRepository.findById(bookId).isEmpty()) throw new BookNotFoundException(bookId);
        loanRepository.deleteByMemberIdAndBookId(memberId, bookId);
    }

    private MemberEntity unwrapMember(Optional<MemberEntity> entity, long memberId) {
        if (entity.isPresent()) return entity.get();
        else throw new MemberNotFoundException(memberId);
    }

    private BookEntity unwrapBook(Optional<BookEntity> entity, long bookId) {
        if (entity.isPresent()) return entity.get();
        else throw new BookNotFoundException(bookId);
    }
}
