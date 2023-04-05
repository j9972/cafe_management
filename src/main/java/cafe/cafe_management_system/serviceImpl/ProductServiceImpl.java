package cafe.cafe_management_system.serviceImpl;

import cafe.cafe_management_system.DAO.ProductDao;
import cafe.cafe_management_system.Entity.Category;
import cafe.cafe_management_system.Entity.Product;
import cafe.cafe_management_system.JWT.JwtFilter;
import cafe.cafe_management_system.constants.CafeConstants;
import cafe.cafe_management_system.service.ProductService;
import cafe.cafe_management_system.utils.CafeUtils;
import cafe.cafe_management_system.wrapper.ProductWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final JwtFilter jwtFilter;

    @Override
    public ResponseEntity<String> addNewProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {

                if(validateProductMap(requestMap,false)) {
                    productDao.save(getProductFromMap(requestMap, false));
                    return CafeUtils.getResponseEntity("Product added successfully", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);

            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private boolean validateProductMap(Map<String, String> requestMap, boolean validateId) {
        if(requestMap.containsKey("name")) {
            if (requestMap.containsKey("id") && validateId) {
                return true;
            } else if (!validateId) {
                return true;
            }
        }
        return false;
    }

    private Product getProductFromMap(Map<String, String> requestMap, boolean isAdd) {
        Category category = new Category();
        category.setId(Long.parseLong(requestMap.get("categoryId")));

        Product product = new Product();

        if(isAdd) {
            product.setId(Long.parseLong(requestMap.get("id")));
        }else {
            product.setStatus("true");
        }

        product.setCategory(category);
        product.setName(requestMap.get("name"));
        product.setDescription(requestMap.get("description"));
        product.setPrice(Integer.parseInt(requestMap.get("price")));

        return product;
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getAllProduct(Pageable pageable) {
        try {
            return new ResponseEntity<>(productDao.getAllProduct(pageable), HttpStatus.OK);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }

        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateProduct(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {

                if(validateProductMap(requestMap, true)) {
                    Optional<Product> optional = productDao.findById(Long.parseLong(requestMap.get("id")));

                    if(!optional.isEmpty()) {

                        Product product = getProductFromMap(requestMap, true);
                        product.setStatus(optional.get().getStatus());
                        productDao.save(product);
                        return CafeUtils.getResponseEntity("product updated successfully", HttpStatus.OK);

                    } else {
                        return CafeUtils.getResponseEntity("Product is does not exist", HttpStatus.OK);
                    }

                } else {
                    return CafeUtils.getResponseEntity(CafeConstants.INVALID_DATA, HttpStatus.BAD_REQUEST);
                }
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(id);
                if(!optional.isEmpty()) {
                    productDao.deleteById(id);
                    return CafeUtils.getResponseEntity("delete success", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Product id does not exist", HttpStatus.OK);
            } else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateStatus(Map<String, String> requestMap) {
        try {
            if(jwtFilter.isAdmin()) {
                Optional optional = productDao.findById(Long.parseLong(requestMap.get("id")));

                if(!optional.isEmpty()) {
                    productDao.updateProductStatus(requestMap.get("status"), Long.parseLong(requestMap.get("id")));
                    return CafeUtils.getResponseEntity("Product status Update Success", HttpStatus.OK);
                }
                return CafeUtils.getResponseEntity("Product id dose not exist ", HttpStatus.OK);
            }else {
                return CafeUtils.getResponseEntity(CafeConstants.UNAUTHORIZED_ACCESS, HttpStatus.UNAUTHORIZED);
            }
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }

        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRONG, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<ProductWrapper>> getByCategory(Long id) {
        try {
            return new ResponseEntity<>(productDao.getProductByCategory(id), HttpStatus.OK);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<ProductWrapper> getProductById(Long id) {
        try {
            return new ResponseEntity<>(productDao.getProductById(id), HttpStatus.OK);
        } catch (Exception e) {
            log.info("{}", e.getMessage());
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ProductWrapper(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
