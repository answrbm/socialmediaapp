package ansarbektassov.socialmediarest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MessageCreateDTO {

    @NotEmpty(message = "Content shouldn't be empty")
    @NotNull(message = "Content shouldn't be null")
    private String content;
}
