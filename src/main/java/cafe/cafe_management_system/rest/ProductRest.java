package cafe.cafe_management_system.rest;

import cafe.cafe_management_system.wrapper.ProductWrapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/product")
public interface ProductRest {

    @PostMapping("/add") // http://localhost:4200/product/add
    ResponseEntity<String> addNewProduct(@RequestBody Map<String,String> requestMap);

    @GetMapping("/get") // http://localhost:4200/product/get
    ResponseEntity<List<ProductWrapper>>  getAllProduct(@PageableDefault(page = 0, size = 3, sort = "id", direction = Sort.Direction.DESC) Pageable pageable);

    @PostMapping("/update") // http://localhost:4200/product/update
    ResponseEntity<String> updateProduct(@RequestBody Map<String,String> requestMap);

    @PostMapping("/delete/{id}") // http://localhost:4200/product/delete/{id}
    ResponseEntity<String> deleteProduct(@PathVariable Long id);

    @PostMapping("/updateStatus") // http://localhost:4200/product/updateStatus
    ResponseEntity<String> updateStatus(@RequestBody Map<String,String> requestMap);

    @GetMapping("/getByCategory/{id}") // http://localhost:4200/product/getByCategory/{id}
    ResponseEntity<List<ProductWrapper>> getByCategory(@PathVariable Long id);

    @GetMapping("/getById/{id}") // http://localhost:4200/product/getById/{id}
    ResponseEntity<ProductWrapper> getProductById(@PathVariable Long id);
}
