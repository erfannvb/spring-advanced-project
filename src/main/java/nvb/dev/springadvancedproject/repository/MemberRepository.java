package nvb.dev.springadvancedproject.repository;

import nvb.dev.springadvancedproject.model.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    @Query("SELECT m FROM MemberEntity m WHERE UPPER(m.fullName) = UPPER(:fullName)")
    Optional<MemberEntity> findMemberByFullNameIgnoreCase(@Param("fullName") String fullName);

    Optional<MemberEntity> findMemberByFullName(String fullName);

}
