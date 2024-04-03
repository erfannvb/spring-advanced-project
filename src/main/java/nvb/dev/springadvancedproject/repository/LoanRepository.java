package nvb.dev.springadvancedproject.repository;

import nvb.dev.springadvancedproject.model.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface LoanRepository extends JpaRepository<LoanEntity, Long> {

    @Transactional
    void deleteByMemberIdAndBookId(long memberId, long bookId);

}
