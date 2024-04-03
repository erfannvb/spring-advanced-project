package nvb.dev.springadvancedproject.service;

import nvb.dev.springadvancedproject.model.LoanEntity;

public interface LoanService {

    LoanEntity loanBookToMember(long memberId, long bookId, LoanEntity loanEntity);

    void deleteLoan(long memberId, long bookId);

}
