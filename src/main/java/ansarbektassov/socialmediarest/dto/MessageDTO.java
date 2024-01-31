package ansarbektassov.socialmediarest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {

    private int messageId;
    private String content;
    private LocalDateTime sentAt;
    private PersonDTO receiver;
    private PersonDTO sender;

}
