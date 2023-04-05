package cafe.cafe_management_system.JWT;

import cafe.cafe_management_system.DAO.UserDao;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Objects;

/*
    다른 security 폴더 ( 다른 유튭 영상 ) 의 userDetailsService 역할을 한다
 */
@RequiredArgsConstructor
@Slf4j
@Component
public class CustomerUsersDetailsService implements UserDetailsService {

    private final UserDao userDao;

    private cafe.cafe_management_system.Entity.User userDetail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        log.info("Inside loadUserByUsername {}", username);

        userDetail = userDao.findByEmailId(username);
        if(!Objects.isNull(userDetail)) {
            return new User(userDetail.getEmail(), userDetail.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }

    public cafe.cafe_management_system.Entity.User getUserDetail() {
        return userDetail;
    }
}
