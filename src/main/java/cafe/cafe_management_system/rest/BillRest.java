package cafe.cafe_management_system.rest;

import cafe.cafe_management_system.Entity.Bill;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/bill")
public interface BillRest {

    @PostMapping("/generateReport")  // http://localhost:4200/bill/generateReport
    ResponseEntity<String> generateReport(@RequestBody Map<String,Object> requestMap);

    @GetMapping("/getBills") // http://localhost:4200/bill/getBills
    ResponseEntity<List<Bill>> getBills();

    @PostMapping("/getPdf") // http://localhost:4200/bill/getPdf
    ResponseEntity<byte[]> getPdf(@RequestBody Map<String, Object> requestMap);

    @PostMapping("/delete/{id}") // http://localhost:4200/bill/delete/{id}
    ResponseEntity<String> deleteBill(@PathVariable Long id);

}
