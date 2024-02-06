package ansarbektassov.socialmediarest.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private int messageId;
    @NotEmpty(message = "Content shouldn't be empty")
    @NotNull(message = "Content shouldn't be null")
    @Column(name = "content")
    private String content;
    @NotNull
    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @OneToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "person_id")
    private Person receiver;

    @OneToOne
    @JoinColumn(name = "sender_id", referencedColumnName = "person_id")
    private Person sender;
}
