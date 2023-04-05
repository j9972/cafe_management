package cafe.cafe_management_system.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import java.io.Serializable;

@NamedQuery(name = "User.findByEmailId", query = "select u from User u where u.email=:email")
//// col 명이 Email이면, where u.Email=:email 이니까 조심하기
//
@NamedQuery(name = "User.getAllUser",
        query = "select new cafe.cafe_management_system.wrapper.UserWrapper(u.id, u.name,u.email, u.contactNumber, u.status) from User u where u.role=:role")

@NamedQuery(name = "User.updateStatus", query = "update User u set u.status =: status where u.id =:id")

@NamedQuery(name = "User.getAllAdmin", query = "select u.email from User u where u.role=:role")

@Data
@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;

}
// TODO : @NamedQuery를 전부 DAO 부분에 넣어주기
