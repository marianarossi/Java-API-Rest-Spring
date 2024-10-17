package mariana.server.controller;

import mariana.server.model.Category;
import mariana.server.service.ICategoryService;
import mariana.server.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("categories")
public class CategoryController {
    private final ICategoryService categoryService;

    public CategoryController(final CategoryServiceImpl categoryService) {
        this.categoryService = categoryService;
    }

//    @PostMapping
//    public ResponseEntity<GenericResponse> createCategory(@RequestBody @Valid Category category) {
//        categoryService.save(category);
//        return ResponseEntity.ok(new GenericResponse("Category created successfully"));
//    }

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid final Category category) {
        Category categoryCreated = categoryService.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(categoryCreated.getId()).toUri();

        return ResponseEntity.created(location).body(categoryCreated);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.findAll();
    }

    @GetMapping("page")
    public Page<Category> getPagedCategories(Pageable pageable) {
        return categoryService.findAll(pageable);
    }

    @GetMapping("findById")
    public Category getCategoryById(Long id) {
        return categoryService.findById(id);
    }

    @GetMapping("exists")
    public boolean checkIfExists(Long id) {
        return categoryService.exists(id);
    }

    @GetMapping("count")
    public void countCategories() {
        categoryService.count();
    }

    @DeleteMapping
    public void deleteCategoryById(Long id) {
        categoryService.delete(id);
    }
}
