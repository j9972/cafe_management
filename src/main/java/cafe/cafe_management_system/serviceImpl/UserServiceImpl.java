package cafe.cafe_management_system.serviceImpl;

import cafe.cafe_management_system.DAO.UserDao;
import cafe.cafe_management_system.Entity.User;
import cafe.cafe_management_system.JWT.CustomerUsersDetailsService;
import cafe.cafe_management_system.JWT.JwtFilter;
import cafe.cafe_management_system.JWT.JwtUtil;
import cafe.cafe_management_system.constants.CafeConstants;
import cafe.cafe_management_system.service.UserService;
import cafe.cafe_management_system.utils.CafeUtils;
import cafe.cafe_management_system.utils.EmailUtils;
import cafe.cafe_management_system.wrapper.UserWrapper;
import io.jsonwebtoken.lang.Strings;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final AuthenticationManager authenticationManager;
    private final CustomerUsersDetailsService customerUsersDetailsService;
    private final JwtUtil jwtUtil;
    private final JwtFilter jwtFilter;
    private final PasswordEncoder passwordEncoder;

    private final EmailUtils emailUtils;

    @Override
    public ResponseEntity<String> signUp(Map<String, String> requestMap) {
        log.info("Inside signUp {}", requestMap);

        try {
            if(validateSignUpMap(requestMap)) {
                User user =  userDao.findByEmailId(requestMap.get("email"));

                if(Objects.isNull(user)) {
                    userDao.save(getUserFromMap(requestMap));
                    return  CafeUtils.getResponseEntity("Successfully Register", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("Email already exist", HttpStatus.BAD_REQUEST);
                }

            } else {
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);

    }

    private boolean validateSignUpMap(Map<String,String> requestMap) {
        if (requestMap.containsKey("name") && requestMap.containsKey("contactNumber")
                && requestMap.containsKey("email") && requestMap.containsKey("password")) {
            return true;
        }
        return false;
    }


    /*
        setter, get() 이 가능한 이유는 Entity 파일에 @Data 애노테이션이 getter, setter 를 포함하기때 문
     */
    private User getUserFromMap(Map<String,String> requestMap) {
        User user = new User();
        user.setName(requestMap.get("name"));
        user.setContactNumber(requestMap.get("contactNumber"));
        user.setEmail(requestMap.get("email"));
        user.setPassword(passwordEncoder.encode(requestMap.get("password")));
        user.setStatus("false");
        user.setRole("user");
        return user;
    }

    @Override
    public ResponseEntity<String> login(Map<String, String> requestMap) {
        log.info("Inside login check");

        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(requestMap.get("email"), requestMap.get("password"))
            );


            if(auth.isAuthenticated()) {
                log.info("auth 인증 됨");
                if(customerUsersDetailsService.getUserDetail().getStatus().equalsIgnoreCase("true")) {
                    return new ResponseEntity<String>("{\"token\":\""+
                            jwtUtil.generateToken(customerUsersDetailsService.getUserDetail().getEmail(),
                                    customerUsersDetailsService.getUserDetail().getRole()) + "\"}",
                            HttpStatus.OK);
                }
            } else {
                log.info("auth 인증 안됨");
                return new ResponseEntity<String>("{\"message\":\""+"Wait for admin approval ."+"\"}",
                        HttpStatus.BAD_REQUEST);
            }

        } catch (Exception e) {
            log.error("{}", e);
        }
        return new ResponseEntity<String>("{\"message\":\""+"Bad Credentials."+"\"}",
                HttpStatus.BAD_REQUEST);
    }

    @Override
    public ResponseEntity<List<UserWrapper>> getAllUser(Pageable pageable) {
        try {
            if(jwtFilter.isAdmin()) {
                log.info("this is admin");
                return new ResponseEntity<>(userDao.getAllUser("user",pageable), HttpStatus.OK);
            } else {
                // admin X , user O
                log.info("this is user");
                return new ResponseEntity<>(new ArrayList<>(), HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> update(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                // admin 입장에서의 logic
                Optional<User> optional = userDao.findById(Long.parseLong(requestMap.get("id")));

                if(!optional.isEmpty()) {
                    // Id ( user ) 가 있는 것
                    userDao.updateStatus(requestMap.get("status"), Long.parseLong(requestMap.get("id")));

                    // 메일 인증 or 메일로 bills 받는 로직의 시작
                    sendMailToAllAdmin(requestMap.get("status"), optional.get().getEmail(), userDao.getAllAdmin("admin"));

                    return CafeUtils.getResponseEntity("User status updated successfully", HttpStatus.OK);
                } else {
                    return CafeUtils.getResponseEntity("User id doesn't exist", HttpStatus.OK);
                }

            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.info("exception e -> 에러남 {} :", e.getMessage());
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private void sendMailToAllAdmin(String status, String user, List<String> allAdmin) {
        allAdmin.remove(jwtFilter.getCurrentUser());
        if(status!=null && status.equalsIgnoreCase("true")) {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account approved ", "User:- " + user+ "\n is approved by \n ADMIN: -" + jwtFilter.getCurrentUser(), allAdmin);
        } else {
            emailUtils.sendSimpleMessage(jwtFilter.getCurrentUser(), "Account disabled ", "User:- " + user+ "\n is disabled by \n ADMIN: -" + jwtFilter.getCurrentUser(), allAdmin);
        }
    }

    // token 검사
    @Override
    public ResponseEntity<String> checkToken() {
        // user 입장에서는 admin page 접근은 불가능
        return CafeUtils.getResponseEntity("true", HttpStatus.OK);
    }

    /*
        비번 변경 로직은 비번 체크 필수
    */
    @Override
    public ResponseEntity<String> changePassword(Map<String, String> requestMap) {
        try {
            User userObj = userDao.findByEmail(jwtFilter.getCurrentUser());
            // user != null 이라면
            if(!userObj.equals(null)) {
                /*
                    암호화 시키지 않았을 때 비번 확인 하는 방법
                    if(userObj.getPassword().equals(requestMap.get("oldPassword"))) {

                    암호화되어서 저장되어 있는 비밀번호를 가져와서 같은지 확인 하는 방법
                    passwordEncoder.matches(requestMap.get("oldPassword"), userObj.getPassword())
                 */
                if(passwordEncoder.matches(requestMap.get("oldPassword"), userObj.getPassword())) {
                    userObj.setPassword(passwordEncoder.encode(requestMap.get("newPassword")));
                    userDao.save(userObj);

                    return CafeUtils.getResponseEntity("password updated successfully", HttpStatus.OK);
                }

                // 비번 틀림
                log.info("비번 틀림");
                return CafeUtils.getResponseEntity("Incorrect Old Password", HttpStatus.BAD_REQUEST);
            }
            return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG,HttpStatus.INTERNAL_SERVER_ERROR);

        } catch (Exception e) {
            log.error(" e : {}", e.getMessage());
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
        비번 찾기 로직이 가능하려면, 이미 로그인이 되어있는 상태니까 비번 변경전 한번더 비번 체크는 안함
     */
    @Override
    public ResponseEntity<String> forgotPassword(Map<String, String> requestMap) {
        try {
            User user = userDao.findByEmail(requestMap.get("email"));

            /*
                guava 라이브러리 ->  Strings.isNullOrEmpty() or objects.isNull() 이런거 사용 가능하게 해줌
                !Strings.isNullOrEmpty(user.getEmail()) -> !(user.getEmail()).isEmpty() && user.getEmail() != null
             */
            if(!Objects.isNull(user) && !(user.getEmail()).isEmpty() && user.getEmail() != null ) {
                /*
                    1. 암호화된 비밀번호를 메일로 보내준다
                    log.info("암호화된 비밀번호 : {}", user.getPassword());
                emailUtils.forgotMail(user.getEmail(), "Credentials by cafe management system", user.getPassword());
                 */

                // 2. 임시 비번 생성 하는 방법
                String tempPwd = temporaryPassword(12);
                log.info("임시 비밀번호 : {}",temporaryPassword(12));

                emailUtils.forgotMail(user.getEmail(), "Credentials by cafe management system", tempPwd);

                log.info("암호화된 임시 비밀번호 : {}", passwordEncoder.encode(tempPwd));
                user.setPassword(passwordEncoder.encode(tempPwd));
                userDao.save(user);
            }
            return CafeUtils.getResponseEntity("check ur mail for Credentials." ,HttpStatus.OK);

        } catch (Exception e) {
            log.error("e . message : {}", e.getMessage());
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /*
    원하는 길이의 랜덤 비번 생성 ( 매개변수 : 사이즈 )
     */
    public static String temporaryPassword(int size) {

        StringBuffer buffer = new StringBuffer();
        Random random = new Random();

        String chars[] = "A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z,a,b,c,d,e,f,g,h,i,j,k,l,m,n,o,p,q,r,s,t,u,v,w,x,y,z,0,1,2,3,4,5,6,7,8,9,!,@,#,$,%,^,&,*,(,)".split(",");

        for (int i = 0; i < size; i++) {
            buffer.append(chars[random.nextInt(chars.length)]);
        }
        return buffer.toString();
    }
}
