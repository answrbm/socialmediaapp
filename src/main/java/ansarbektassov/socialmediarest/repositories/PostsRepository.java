package ansarbektassov.socialmediarest.repositories;

import ansarbektassov.socialmediarest.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostsRepository extends JpaRepository<Post,Integer> {
}
