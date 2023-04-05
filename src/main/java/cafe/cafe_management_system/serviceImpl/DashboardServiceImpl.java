package cafe.cafe_management_system.serviceImpl;

import cafe.cafe_management_system.DAO.BillDao;
import cafe.cafe_management_system.DAO.CategoryDao;
import cafe.cafe_management_system.DAO.ProductDao;
import cafe.cafe_management_system.service.DashboardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final CategoryDao categoryDao;
    private final ProductDao productDao;
    private final BillDao billDao;

    @Override
    public ResponseEntity<Map<String, Object>> getCount() {
        Map<String, Object> map = new HashMap<>();
        map.put("category", categoryDao.count());
        map.put("product", productDao.count());
        map.put("bill", billDao.count());
        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
