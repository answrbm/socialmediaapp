package ansarbektassov.socialmediarest.services;

import ansarbektassov.socialmediarest.dto.MessageCreateDTO;
import ansarbektassov.socialmediarest.dto.MessageDTO;
import ansarbektassov.socialmediarest.exceptions.message.MessageNotFoundException;
import ansarbektassov.socialmediarest.models.Friendship;
import ansarbektassov.socialmediarest.models.Message;
import ansarbektassov.socialmediarest.models.Person;
import ansarbektassov.socialmediarest.repositories.MessagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class MessagesService {

    private final MessagesRepository messagesRepository;
    private final FriendshipsService friendshipsService;

    public List<Message> findAll() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return messagesRepository.findAll()
                .stream()
                .filter(message -> message.getReceiver().getUsername().equals(username)
                        || message.getSender().getUsername().equals(username)).toList();
    }

    public List<Message> findAllWithPerson(int personId) {
        return findAll().stream().filter(msg -> msg.getSender().getPersonId() == personId ||
                msg.getReceiver().getPersonId() == personId).toList();
    }

    public Message findById(int messageId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Message message = messagesRepository.findById(messageId).orElseThrow(MessageNotFoundException::new);
        if(message.getReceiver().getUsername().equals(username) ||
        message.getSender().getUsername().equals(username)) {
            return message;
        } else {
            throw new MessageNotFoundException();
        }
    }

    public void sendMessage(int receiverId, MessageCreateDTO messageDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Friendship friendship = friendshipsService.findByUsername(username,receiverId);
        Person sender = friendship.getReceiver().getUsername()
                .equals(username) ? friendship.getReceiver() : friendship.getSubscriber();
        Person receiver = friendship.getReceiver().getUsername()
                .equals(username) ? friendship.getSubscriber() : friendship.getReceiver();
        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(messageDTO.getContent());
        message.setSentAt(LocalDateTime.now());
        messagesRepository.save(message);
    }

    public void editMessage(int messageId, MessageCreateDTO messageDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Message message = findAll().stream().filter(msg -> msg.getSender().getUsername().equals(username)
                || msg.getReceiver().getUsername().equals(username)).toList()
                .stream().filter(msg -> msg.getMessageId() == messageId).findAny().orElseThrow(MessageNotFoundException::new);
        message.setContent(messageDTO.getContent());
        messagesRepository.save(message);
    }

    public void deleteMessage(int messageId) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Message message = findAll().stream().filter(msg -> msg.getSender().getUsername().equals(username)
                        || msg.getReceiver().getUsername().equals(username)).toList()
                .stream().filter(msg -> msg.getMessageId() == messageId).findAny().orElseThrow(MessageNotFoundException::new);
        messagesRepository.delete(message);
    }

}
