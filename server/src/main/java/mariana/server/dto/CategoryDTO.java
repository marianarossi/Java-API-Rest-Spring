package mariana.server.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class CategoryDTO {
    private Long id;

    @NotNull
    @Size(min = 2, max = 50)
    private String name;
}
