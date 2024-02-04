package ansarbektassov.socialmediarest.services;

import ansarbektassov.socialmediarest.dto.PostCreateDTO;
import ansarbektassov.socialmediarest.dto.PostDTO;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotFoundException;
import ansarbektassov.socialmediarest.exceptions.post.PostNotCreatedException;
import ansarbektassov.socialmediarest.exceptions.post.PostNotFoundException;
import ansarbektassov.socialmediarest.models.Friendship;
import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.models.Post;
import ansarbektassov.socialmediarest.repositories.PostsRepository;
import ansarbektassov.socialmediarest.security.PersonDetails;
import ansarbektassov.socialmediarest.util.FriendshipStatus;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class PostsService {

    private final PostsRepository postsRepository;
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    public List<Post> findAll() {
        return postsRepository.findAll();
    }

    public Post findById(int id) {
        return postsRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    public List<Post> findByCreator(Person creator) {
        return postsRepository.findByCreator(creator);
    }

    public List<Post> findByCreator(int personId) {
        Person foundPerson = peopleService.findById(personId);
        return postsRepository.findByCreator(foundPerson);
    }

    public List<Post> findByFriendships(List<Friendship> friendships) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        List<Post> friendsPosts = new ArrayList<>();
        boolean isMainUserUsed = false;
        for(Friendship f : friendships) {
            if(!f.getReceiver().getUsername().equals(username)) {
                friendsPosts.addAll(findByCreator(f.getReceiver()));
            } else if(!isMainUserUsed) {
                friendsPosts.addAll(findByCreator(f.getReceiver()));
                isMainUserUsed = true;
            }

            if(!f.getSubscriber().getUsername().equals(username)) {
                friendsPosts.addAll(findByCreator(f.getSubscriber()));
            } else if(!isMainUserUsed) {
                friendsPosts.addAll(findByCreator(f.getSubscriber()));
                isMainUserUsed = true;
            }
        }
        return friendsPosts;
    }

    public List<Post> findByUsername(String username) {
        return findAll().stream().filter(post ->
                post.getCreator().getUsername().equals(username)).toList();
    }

    public void save(Post postToCreate) {
        enrichPost(postToCreate);
        postsRepository.save(postToCreate);
    }

    public void update(int id, PostCreateDTO postDTO) {
        Post postToUpdate = findById(id);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(postToUpdate.getCreator().getUsername().equals(authentication.getName())) {
            postToUpdate.setTitle(postDTO.getTitle());
            postToUpdate.setDescription(postDTO.getDescription());
            postsRepository.save(postToUpdate);
        } else {
            throw new PostNotCreatedException("Cannot modify not your posts");
        }
    }

    public void delete(int postId) {
        Post postToDelete = postsRepository.findById(postId).orElseThrow(PostNotFoundException::new);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(postToDelete.getCreator().getUsername().equals(authentication.getName()))
            postsRepository.delete(postToDelete);
        else
            throw new PostNotCreatedException("Cannot modify not your posts");
    }

    public void enrichPost(Post post) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        post.setCreator(peopleService.findByUsername(username)
                .orElseThrow(PersonNotFoundException::new));
        post.setCreatedAt(LocalDateTime.now());
    }

    public PostDTO convertToPostDTO(Post post) {
        return modelMapper.map(post,PostDTO.class);
    }
}
