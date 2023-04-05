package cafe.cafe_management_system.DAO;

import cafe.cafe_management_system.Entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryDao extends JpaRepository<Category, Long> {

    @Query("select c from Category c where c.id in (" +
                    "select p.category from  Product p where p.status='true')")
    List<Category> getAllCategory();

}
