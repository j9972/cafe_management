package cafe.cafe_management_system.utils;

import com.google.common.base.Strings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.*;

@Slf4j
public class CafeUtils {

    private CafeUtils() {

    }

    /*
        ResponseEntity
        - 사용자의 HttpRequest 에 대한 응답 데이터를 포함하는 클래스이다. 따라서 HttpStatus, HttpHeaders, HttpBody 포함
     */
    public static ResponseEntity<String> getResponseEntity(String responseMessage, HttpStatus httpStatus) {
        return new ResponseEntity<String>("{\"message\":\"" + responseMessage +"\"}", httpStatus);
    }

    public static String getUUID() {
        Date data = new Date();
        long time = data.getTime();

        return "BILL-" + time;
    }

    public static JSONArray getJsonArrayFromString(String data) throws JSONException {
        JSONArray jsonArray = new JSONArray(data);

        log.info("getJsonArrayFromString - jsonArray : {}", jsonArray);
        return jsonArray;
    }

    public static Map<String,Object> getMapFromJson(String data) {
        log.info("data : {}", data);
        if(!Strings.isNullOrEmpty(data)) {
            return new Gson().fromJson(data, new TypeToken<Map<String, Object>>() {
            }.getType());
        }
        return new HashMap<>();
    }

    public static boolean isFileExist(String path) {
        log.info("inside isFileExist path : {}", path);
        try {
            File file = new File(path);
            return (file != null && file.exists()) ? Boolean.TRUE : Boolean.FALSE;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
