package cafe.cafe_management_system.service;

import cafe.cafe_management_system.wrapper.UserWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {

    ResponseEntity<String> signUp(Map<String,String> requestMap);

    ResponseEntity<String> login(Map<String, String> requestMap);

    ResponseEntity<List<UserWrapper>> getAllUser(Pageable pageable);

    ResponseEntity<String> update(Map<String, String> requestMap);

    ResponseEntity<String> checkToken();


    ResponseEntity<String> changePassword(Map<String, String> requestMap);

    ResponseEntity<String> forgotPassword(Map<String, String> requestMap);
}
