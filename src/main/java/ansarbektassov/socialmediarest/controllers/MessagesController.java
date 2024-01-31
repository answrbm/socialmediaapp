package ansarbektassov.socialmediarest.controllers;

import ansarbektassov.socialmediarest.dto.MessageCreateDTO;
import ansarbektassov.socialmediarest.dto.MessageDTO;
import ansarbektassov.socialmediarest.models.Message;
import ansarbektassov.socialmediarest.services.FriendshipsService;
import ansarbektassov.socialmediarest.services.MessagesService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/api/messages")
@Tag(name = "Messages")
public class MessagesController {

    private final MessagesService messagesService;
    private final FriendshipsService friendshipsService;
    private final ModelMapper modelMapper;

    @GetMapping
    public List<MessageDTO> findAll() {
        return messagesService.findAll().stream().map(this::convertToMessageDTO).toList();
    }

    @GetMapping("/chat")
    public List<MessageDTO> findAllWithPerson(@RequestParam("personId") int personId) {
        return messagesService.findAllWithPerson(personId).stream().map(this::convertToMessageDTO).toList();
    }

    @PostMapping
    public Map<String, String> sendMessage(@RequestParam("personId") int personId, @RequestBody @Validated MessageCreateDTO messageDTO) {
        messagesService.sendMessage(personId,messageDTO);
        return Map.of("message","message was sent");
    }

    @PutMapping("/{messageId}")
    public Map<String, String> editMessage(@PathVariable("messageId") int messageId, @RequestBody @Validated MessageCreateDTO messageDTO) {
        messagesService.editMessage(messageId,messageDTO);
        return Map.of("message","message was updated");
    }

    @DeleteMapping("/{messageId}")
    public Map<String, String> deleteMessage(@PathVariable("messageId") int messageId) {
        messagesService.deleteMessage(messageId);
        return Map.of("message","message was deleted");
    }

    public MessageDTO convertToMessageDTO(Message message) {
        return modelMapper.map(message, MessageDTO.class);
    }
}

