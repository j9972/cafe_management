package cafe.cafe_management_system;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

/*
    @SpringBootApplication(exclude = SecurityAutoConfiguration.class)
    build.gradle 에서는 Spring Security 의존성을 추가했지만,
    아직 인증단계를 개발하지 않은 경우 Application 에서 exclude 를 이용해 Security 기능을 꺼둘 수 있다
 */
@SpringBootApplication
@EnableEncryptableProperties // jasypt 를 위함
public class CafeManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(CafeManagementSystemApplication.class, args);
    }

}
