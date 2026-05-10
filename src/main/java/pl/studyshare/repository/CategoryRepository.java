package pl.studyshare.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.studyshare.domain.Category;

public interface CategoryRepository extends JpaRepository<Category, Long>
{
    
}
