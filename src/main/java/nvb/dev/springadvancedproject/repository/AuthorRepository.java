package nvb.dev.springadvancedproject.repository;

import nvb.dev.springadvancedproject.model.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {

    Optional<AuthorEntity> findByFirstNameIgnoreCase(String firstName);

    @Query("SELECT a FROM AuthorEntity a WHERE a.age BETWEEN :minAge AND :maxAge")
    List<AuthorEntity> findByAgeBetween(int minAge, int maxAge);

}
