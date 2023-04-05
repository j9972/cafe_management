package cafe.cafe_management_system.rest;

import cafe.cafe_management_system.wrapper.UserWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@RequestMapping("/user")
public interface UserRest {

    @PostMapping("/signup") // http://localhost:4200/user/signup
    public ResponseEntity<String> signUp(@RequestBody Map<String, String> requestMap);

    @PostMapping("/login") // http://localhost:4200/user/login
    public ResponseEntity<String> login(@RequestBody Map<String, String> requestMap);

    @GetMapping("/get") // http://localhost:4200/user/get
    public ResponseEntity<List<UserWrapper>> getAllUser(@PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable);

    @PostMapping("/update") // http://localhost:4200/user/update ( admin 계정으로 user info update )
    public ResponseEntity<String> update(@RequestBody Map<String,String> requestMap);

    // check token
    @GetMapping("/checkToken") // http://localhost:4200/user/checkToken
    ResponseEntity<String> checkToken();

    // check password
    @PostMapping("/changePassword") // http://localhost:4200/user/changePassword
    ResponseEntity<String> changePassword(@RequestBody Map<String,String> requestMap);

    // forget password
    @PostMapping("/forgotPassword") // http://localhost:4200/user/forgotPassword
    ResponseEntity<String> forgotPassword(@RequestBody Map<String,String> requestMap);
}
