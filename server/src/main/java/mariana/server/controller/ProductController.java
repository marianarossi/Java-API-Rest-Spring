package mariana.server.controller;

import jakarta.validation.Valid;
import mariana.server.model.Product;
import mariana.server.service.IProductService;
import mariana.server.service.impl.ProductServiceImpl;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {
    private final IProductService productService;
    public ProductController(final ProductServiceImpl productService) { this.productService = productService; }

    @PostMapping
    public ResponseEntity<Product> createProduct(@RequestBody @Valid final Product product) {
        Product savedProduct = productService.save(product);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedProduct.getId()).toUri();
        return ResponseEntity.created(location).body(savedProduct);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.findAll());
    }

    @GetMapping("page")
    public ResponseEntity<Page<Product>> getProductsByPage(@RequestParam int page,
                                                           @RequestParam int size,
                                                           @RequestParam(required = false) String order,
                                                           @RequestParam(required = false) Boolean asc) {
        page = page - 1;
        PageRequest pageRequest = PageRequest.of(page, size);
        if(order != null && asc != null)
        {
            pageRequest = PageRequest.of(page, size, asc ? Sort.Direction.ASC: Sort.Direction.DESC, order);
        }
        return ResponseEntity.ok(productService.findAll(pageRequest));
    }

    @GetMapping("{id}")
    public ResponseEntity<Product> findProductById(@PathVariable Long id)
    {
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping("exists")
    public ResponseEntity<Boolean> existsProductById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.exists(id));
    }

    @GetMapping("count")
    public ResponseEntity<Long> countProducts() {
        return ResponseEntity.ok(productService.count());
    }

    @DeleteMapping("{id}")
    public void deleteProductById(@PathVariable Long id) {
        productService.delete(id);
    }

}
