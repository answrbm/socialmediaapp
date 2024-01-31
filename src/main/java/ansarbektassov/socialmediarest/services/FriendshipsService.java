package ansarbektassov.socialmediarest.services;

import ansarbektassov.socialmediarest.exceptions.friendship.FriendshipNotFoundException;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotFoundException;
import ansarbektassov.socialmediarest.models.Friendship;
import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.repositories.FriendshipsRepository;
import ansarbektassov.socialmediarest.util.FriendshipStatus;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@AllArgsConstructor
public class FriendshipsService {

    private final FriendshipsRepository friendshipRepository;
    private final PeopleService peopleService;

    public List<Friendship> findAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return friendshipRepository.findAll()
                .stream()
                .filter(fsh -> fsh.getReceiver().getUsername().equals(username)
                        || fsh.getSubscriber().getUsername().equals(username)).toList();
    }

    public Friendship findById(int friendshipId) {
        return friendshipRepository.findById(friendshipId).orElseThrow(FriendshipNotFoundException::new);
    }

    public Friendship findByUsername(String username, int personId) {
        return findAll().stream().filter(fsh ->
                        fsh.getReceiver().getUsername().equals(username)
                                || fsh.getSubscriber().getUsername().equals(username)).toList()
                .stream().filter(fsh -> fsh.getReceiver().getPersonId() == personId || fsh.getSubscriber().getPersonId() == personId)
                .findAny().orElseThrow(FriendshipNotFoundException::new);
    }

    public List<Friendship> findByUsername(String username) {
        return findAll().stream().filter(fsh ->
                        fsh.getReceiver().getUsername().equals(username)
                                || fsh.getSubscriber().getUsername().equals(username)).toList();
    }

    public List<Friendship> findByReceiver(Person receiver) {
        return friendshipRepository.findByReceiver(receiver);
    }

    public List<Friendship> findBySubscriber(Person subscriber) {
        return friendshipRepository.findBySubscriber(subscriber);
    }

    public void subscribe(int receiverId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Person subscriber = peopleService.findByUsername(username).orElseThrow(PersonNotFoundException::new);
        Person receiver = peopleService.findById(receiverId);

        Friendship friendship = new Friendship();
        friendship.setSubscriber(subscriber);
        friendship.setReceiver(receiver);
        friendship.setFriendshipStatus(FriendshipStatus.SUBSCRIBER);
        friendshipRepository.save(friendship);
    }

    public void cancelFriendship(int friendshipId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Person person = peopleService.findByUsername(username).orElseThrow(PersonNotFoundException::new);
        Friendship friendship = friendshipRepository.findByReceiver(person).stream().filter(fsh -> fsh.getFriendshipId() == friendshipId).findAny()
                .orElseGet(() -> friendshipRepository.findBySubscriber(person).stream()
                        .filter(fsh -> fsh.getFriendshipId() == friendshipId).findAny().orElseThrow(FriendshipNotFoundException::new));
        if(friendship.getFriendshipStatus() == FriendshipStatus.SUBSCRIBER) {
            friendshipRepository.delete(friendship);
        } else {
            if(friendship.getSubscriber().getUsername().equals(person.getUsername())) {
                Person oldReceiver = friendship.getReceiver();
                friendship.setSubscriber(oldReceiver);
                friendship.setReceiver(person);
            }
            friendship.setFriendshipStatus(FriendshipStatus.SUBSCRIBER);
            friendshipRepository.save(friendship);
        }
    }

    public void acceptFriendship(int friendshipId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Person receiver = peopleService.findByUsername(username).orElseThrow(PersonNotFoundException::new);

        Friendship friendship = friendshipRepository.findByReceiver(receiver)
                            .stream().filter(fsh -> fsh.getFriendshipId() == friendshipId).findAny()
                            .orElseThrow(FriendshipNotFoundException::new);
        friendship.setFriendshipStatus(FriendshipStatus.FRIEND);
        friendship.setFriendshipDate(LocalDateTime.now());
        friendshipRepository.save(friendship);
    }

}
