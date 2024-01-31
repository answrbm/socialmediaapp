package ansarbektassov.socialmediarest.repositories;

import ansarbektassov.socialmediarest.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessagesRepository extends JpaRepository<Message,Integer> {
}
