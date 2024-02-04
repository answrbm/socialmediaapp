package ansarbektassov.socialmediarest.controllers;

import ansarbektassov.socialmediarest.dto.FriendshipDTO;
import ansarbektassov.socialmediarest.models.Friendship;
import ansarbektassov.socialmediarest.services.FriendshipsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/friendships")
@Tag(name = "Friendships")
public class FriendshipsController {

    private final FriendshipsService friendshipsService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<FriendshipDTO> findAll() {
        return friendshipsService.findAll().stream().map(this::convertToFriendshipDTO).toList();
    }

    @GetMapping("/{friendshipId}")
    public FriendshipDTO findById(@PathVariable("friendshipId") int friendshipId) {
        return convertToFriendshipDTO(friendshipsService.findById(friendshipId));
    }

    @GetMapping("/people")
    public FriendshipDTO findByPersonId(@RequestParam("personId") int personId) {
        return convertToFriendshipDTO(friendshipsService.findByPersonId(personId));
    }

    @PostMapping
    public Map<String, String> subscribe(@RequestParam("personId") int personId) {
        friendshipsService.subscribe(personId);
        return Map.of("message","subscribed");
    }

    @DeleteMapping
    public Map<String, String> cancelSubscription(@RequestParam("friendshipId") int friendshipId) {
        friendshipsService.cancelFriendship(friendshipId);
        return Map.of("message","unsubscribed");
    }


    @PutMapping
    public Map<String, String> acceptFriendship(@RequestParam("friendshipId") int friendshipId) {
        friendshipsService.acceptFriendship(friendshipId);
        return Map.of("message","Friendship accepted");
    }

    public FriendshipDTO convertToFriendshipDTO(Friendship friendship) {
        return modelMapper.map(friendship, FriendshipDTO.class);
    }

}
