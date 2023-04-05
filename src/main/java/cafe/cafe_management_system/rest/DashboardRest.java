package cafe.cafe_management_system.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@RequestMapping("/dashboard")
public interface DashboardRest {

    @GetMapping("/details")  // http://localhost:4200/dashboard/details
    ResponseEntity<Map<String,Object>> getCount();
}
