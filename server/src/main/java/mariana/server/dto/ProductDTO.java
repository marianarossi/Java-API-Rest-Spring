package mariana.server.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public class ProductDTO {
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;

    @NotNull
    private String description;

    @NotNull
    private BigDecimal price;

    private CategoryDTO category;
}
