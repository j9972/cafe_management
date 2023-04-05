package cafe.cafe_management_system.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

// product의 status = true 인 value 들만 데이터를 출력해준다.
//@NamedQuery(name = "Category.getAllCategory",
//        query = "select c from Category c where c.id in (" +
//                "select p.category from  Product p where p.status='true')")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;
}
