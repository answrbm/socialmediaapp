package ansarbektassov.socialmediarest.repositories;

import ansarbektassov.socialmediarest.models.Friendship;
import ansarbektassov.socialmediarest.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FriendshipsRepository extends JpaRepository<Friendship, Integer> {

    List<Friendship> findByReceiver(Person receiver);
    List<Friendship> findBySubscriber(Person subscriber);
}
