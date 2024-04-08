package nvb.dev.springadvancedproject.repository;

import nvb.dev.springadvancedproject.model.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query("SELECT b FROM BookEntity b WHERE b.author.id = :authorId")
    List<BookEntity> findBookByAuthorId(long authorId);

}
