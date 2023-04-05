package cafe.cafe_management_system.DAO;

import cafe.cafe_management_system.Entity.User;
import cafe.cafe_management_system.wrapper.UserWrapper;
import jakarta.persistence.NamedQuery;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;






public interface UserDao extends JpaRepository<User, Long> {

    User findByEmailId(@Param("email") String email);

    List<UserWrapper> getAllUser(@Param("role") String role, Pageable pageable);


    List<String> getAllAdmin(@Param("role") String role);

    @Transactional
    @Modifying // update 임을 보이기 위한 Annotation 이라고 생각하기
    Integer updateStatus(@Param("status") String status, @Param("id") Long id);

    User findByEmail(String email);
}
