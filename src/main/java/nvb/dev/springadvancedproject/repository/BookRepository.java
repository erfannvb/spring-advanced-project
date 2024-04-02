package nvb.dev.springadvancedproject.repository;

import nvb.dev.springadvancedproject.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findBookByAuthorId(long authorId);

    @Transactional
    void deleteBookByAuthorId(long authorId);

}
