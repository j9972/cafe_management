package cafe.cafe_management_system.config;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class JasyptConfigTest {
    @Test
    void encryptTest() {
        String id = "root";
        String password = "jh485200@@";
        String pwd = "ayvapqftbpxhvapv";

        System.out.println(jasyptEncoding(id));
        System.out.println(jasyptEncoding(password));
        System.out.println(jasyptEncoding(pwd));
    }

    public String jasyptEncoding(String value) {
        String key = "test_code_password";
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword(key);
        return pbeEnc.encrypt(value);
    }

}