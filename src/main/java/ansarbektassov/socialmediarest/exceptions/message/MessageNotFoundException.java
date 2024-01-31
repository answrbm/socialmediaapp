package ansarbektassov.socialmediarest.exceptions.message;

public class MessageNotFoundException extends RuntimeException {

    public MessageNotFoundException() {
        super("Message not found");
    }
}
