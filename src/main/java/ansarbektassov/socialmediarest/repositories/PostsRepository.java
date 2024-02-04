package ansarbektassov.socialmediarest.repositories;

import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostsRepository extends JpaRepository<Post,Integer> {

    List<Post> findByCreator(Person creator);
}
