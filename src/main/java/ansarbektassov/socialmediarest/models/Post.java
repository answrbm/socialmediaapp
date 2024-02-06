package ansarbektassov.socialmediarest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Post")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;
    @NotEmpty(message = "Title shouldn't be empty")
    @NotNull(message = "Title shouldn't be null")
    @Size(min = 1,max = 100, message = "Title should be between 1 and 100 characters")
    @Column(name = "title")
    private String title;
    @NotEmpty(message = "Description shouldn't be empty")
    @NotNull(message = "Description shouldn't be null")
    @Size(min = 1,max = 1000, message = "Description should be between 1 and 1000 characters")
    @Column(name = "description")
    private String description;
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private Person creator;

}
