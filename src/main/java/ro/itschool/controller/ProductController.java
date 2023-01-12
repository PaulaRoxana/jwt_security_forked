package ro.itschool.controller;

import ro.itschool.entity.Product;
import ro.itschool.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @GetMapping(value = "/all")
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity getProductById(@PathVariable Integer id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent())
            return new ResponseEntity<>(optionalProduct, HttpStatus.OK);
        else
            return ResponseEntity.badRequest().build();
    }

    @GetMapping(value = "/name")
    public ResponseEntity getProductByName(@RequestParam String name) {
        Optional<Product> optionalProduct = productRepository.findByName(name);
        if (optionalProduct.isPresent())
            return new ResponseEntity<>(optionalProduct, HttpStatus.OK);
        else
            return ResponseEntity.badRequest().build();
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Product> addProductToShop(@RequestBody Product product) {
        return new ResponseEntity<>(productRepository.save(product), HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> removeProduct(@PathVariable Integer id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.ok().build();
        } else
            return ResponseEntity.badRequest().build();
    }
}
