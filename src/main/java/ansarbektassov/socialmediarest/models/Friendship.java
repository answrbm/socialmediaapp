package ansarbektassov.socialmediarest.models;

import ansarbektassov.socialmediarest.util.FriendshipStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Friendship")
public class Friendship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "friendship_id")
    private int friendshipId;
    @NotNull(message = "Friendship status shouldn't be null")
    @Enumerated(EnumType.STRING)
    @Column(name = "friendship_status")
    private FriendshipStatus friendshipStatus;
    @Column(name = "friendship_date")
    private LocalDateTime friendshipDate;

    @OneToOne
    @JoinColumn(name = "receiver_id", referencedColumnName = "person_id")
    private Person receiver;

    @OneToOne
    @JoinColumn(name = "subscriber_id", referencedColumnName = "person_id")
    private Person subscriber;

}
