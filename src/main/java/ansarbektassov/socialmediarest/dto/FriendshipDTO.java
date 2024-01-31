package ansarbektassov.socialmediarest.dto;

import ansarbektassov.socialmediarest.util.FriendshipStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FriendshipDTO {

    private int friendshipId;
    private PersonDTO receiver;
    private PersonDTO subscriber;
    private FriendshipStatus friendshipStatus;
    private LocalDateTime friendshipDate;
}
