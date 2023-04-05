package cafe.cafe_management_system.rest;

import cafe.cafe_management_system.Entity.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/category")
public interface CategoryRest {

    @PostMapping("/add")  // http://localhost:4200/category/add
    ResponseEntity<String> addNewCategory(@RequestBody Map<String,String> requestMap);

    @GetMapping("/get")  // http://localhost:4200/category/get
    ResponseEntity<List<Category>> getAllCategory(@RequestParam String filterValue);

    @PostMapping("/update")  // http://localhost:4200/category/update
    ResponseEntity<String> updateCategory(@RequestBody Map<String,String> requestMap);
}
