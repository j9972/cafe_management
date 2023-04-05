package cafe.cafe_management_system.DAO;

import cafe.cafe_management_system.Entity.Product;
import cafe.cafe_management_system.wrapper.ProductWrapper;
import jakarta.persistence.NamedQuery;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Long> {
    //@Query("select new cafe.cafe_management_system.wrapper.ProductWrapper(p.id,p.name,p.description,p.price,p.status,p.category.id,p.category.name) from Product p")
    List<ProductWrapper> getAllProduct(Pageable pageable);

    @Modifying
    @Transactional
    //@Query("update Product p set p.status =: status where p.id=:id")
    Integer updateProductStatus(@Param("status") String status, @Param("id") Long id);

    //@Query("select  new cafe.cafe_management_system.wrapper.ProductWrapper(p.id,p.name) from Product p where p.category.id=:id and p.status='true'")
    List<ProductWrapper> getProductByCategory(@Param("id") Long id);

    //@Query("select  new cafe.cafe_management_system.wrapper.ProductWrapper(p.id,p.name,p.description,p.price) from Product p where p.id=:id")
    ProductWrapper getProductById(@Param("id") Long id);
}
