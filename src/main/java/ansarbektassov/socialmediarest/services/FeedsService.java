package ansarbektassov.socialmediarest.services;

import ansarbektassov.socialmediarest.dto.PostDTO;
import ansarbektassov.socialmediarest.models.Friendship;
import ansarbektassov.socialmediarest.models.Post;
import ansarbektassov.socialmediarest.util.FriendshipStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class FeedsService {

    private final PostsService postsService;
    private final FriendshipsService friendshipsService;

    public List<PostDTO> findAll() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        List<Friendship> friendships = friendshipsService.findByUsername(username);
//        List<Friendship> filteredFriendships = friendships.stream()
//                .filter(friendship -> friendship.getFriendshipStatus() == FriendshipStatus.FRIEND ||
//                friendship.getSubscriber().getUsername().equals(username)).toList();
//
//        List<PostDTO> posts = new ArrayList<>(postsService.findByFriendships(filteredFriendships).stream()
        List<PostDTO> posts = new ArrayList<>(postsService.findAll().stream()
                .map(postsService::convertToPostDTO).toList());
        posts.sort((o1, o2) -> o1.getCreatedAt().compareTo(o2.getCreatedAt()));
        return posts;
    }
}
