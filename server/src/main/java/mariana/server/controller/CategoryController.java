package mariana.server.controller;

import mariana.server.model.Category;
import mariana.server.service.ICategoryService;
import mariana.server.service.impl.CategoryServiceImpl;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
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

    /**
     * Persiste uma categoria ao enviar uma requisição HTTP POST com um JSON representando uma
     * categoria no corpo. POST para http://localhost:8080/categories
     */

    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid final Category category) {
        Category categoryCreated = categoryService.save(category);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}") //retorna o id da categoria
                .buildAndExpand(categoryCreated.getId()).toUri();
        return ResponseEntity.created(location).body(categoryCreated);
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok(categoryService.findAll());
    }

    // GET http://localhost:8080/categories/page?page=1&size=5
    @GetMapping("page")
    public ResponseEntity<Page<Category>> findPage(@RequestParam int page, @RequestParam int size,
                                                   @RequestParam(required = false) String order,
                                                   @RequestParam(required = false) Boolean asc) {
        page = page - 1; //tratamento, requisicao de pagina 1 tem que ser pagina 0 (primeira pagina no banco)
        PageRequest pageRequest = PageRequest.of(page, size);
        if(order != null && asc != null)
        {
            pageRequest = PageRequest.of(page, size, asc ? Sort.Direction.ASC: Sort.Direction.DESC, order);
        }
        return ResponseEntity.ok(categoryService.findAll(pageRequest));
    }

    // GET para http://localhost:8080/categories/1 - traz categoria com código 1
    @GetMapping("{id}")
    public ResponseEntity<Category> findCategoryById(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.findById(id));
    }

    // GET para http://localhost:8080/categories/exists/1
    @GetMapping("exists/{id}")
    public ResponseEntity<Boolean> checkIfExists(@PathVariable Long id) {
        return ResponseEntity.ok(categoryService.exists(id));
    }

    @GetMapping("count")
    public ResponseEntity<Long> countCategories() {
        return ResponseEntity.ok(categoryService.count());
    }

    @DeleteMapping({"{id}"})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryById(@PathVariable Long id) {
        categoryService.delete(id);
    }
}
