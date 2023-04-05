package cafe.cafe_management_system.wrapper;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
/*
    how to get all users, update user ( enable, disable user account )
 */
public class UserWrapper {

    private Long id;

    private String email;

    private String name;

    private String contactNumber;

    private String status;

    public UserWrapper(Long id, String email, String name, String contactNumber, String status) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.contactNumber = contactNumber;
        this.status = status;
    }
}
