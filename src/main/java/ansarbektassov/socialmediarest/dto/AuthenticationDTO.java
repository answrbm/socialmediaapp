package ansarbektassov.socialmediarest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class AuthenticationDTO {

    private String username;
    private String password;
}
