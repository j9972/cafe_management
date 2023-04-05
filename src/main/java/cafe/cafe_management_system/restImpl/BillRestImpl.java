package cafe.cafe_management_system.restImpl;

import cafe.cafe_management_system.Entity.Bill;
import cafe.cafe_management_system.constants.CafeConstants;
import cafe.cafe_management_system.rest.BillRest;
import cafe.cafe_management_system.service.BillService;
import cafe.cafe_management_system.utils.CafeUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BillRestImpl implements BillRest {

    private final BillService billService;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> requestMap) {

        try {
            return billService.generateReport(requestMap);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        try {
            return billService.getBills();
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        try {
            return billService.getPdf(requestMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ResponseEntity<String> deleteBill(Long id) {
        try {
            return billService.deleteBill(id);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
