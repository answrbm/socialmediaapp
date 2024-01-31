package ansarbektassov.socialmediarest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "Person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int personId;
    @NotNull(message = "Username shouldn't be null")
    @NotEmpty(message = "Username shouldn't be empty")
    @Column(name = "username")
    private String username;
    @NotNull(message = "Password shouldn't be null")
    @NotEmpty(message = "Password shouldn't be empty")
    @Column(name = "password")
    private String password;
    @NotNull(message = "Email shouldn't be null")
    @NotEmpty(message = "Email shouldn't be empty")
    @Email(message = "Email should be valid")
    @Column(name = "email")
    private String email;
    @NotNull(message = "Name shouldn't be null")
    @NotEmpty(message = "Name shouldn't be empty")
    @Size(min = 2, max = 200, message = "Name should be between 2 and 200 characters")
    @Column(name = "name")
    private String name;
    @Column(name = "role")
    private String role;

    @OneToMany(mappedBy = "creator")
    private List<Post> posts;
}
