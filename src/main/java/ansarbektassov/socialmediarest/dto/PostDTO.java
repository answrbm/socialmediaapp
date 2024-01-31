package ansarbektassov.socialmediarest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDTO {

    private int postId;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private PersonDTO creator;
}
