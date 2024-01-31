package ansarbektassov.socialmediarest.exceptions.handlers;

import ansarbektassov.socialmediarest.exceptions.ErrorResponse;
import ansarbektassov.socialmediarest.exceptions.friendship.FriendshipNotCreatedException;
import ansarbektassov.socialmediarest.exceptions.friendship.FriendshipNotFoundException;
import ansarbektassov.socialmediarest.exceptions.message.MessageNotCreatedException;
import ansarbektassov.socialmediarest.exceptions.message.MessageNotFoundException;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotCreatedException;
import ansarbektassov.socialmediarest.exceptions.person.PersonNotFoundException;
import ansarbektassov.socialmediarest.exceptions.post.PostNotCreatedException;
import ansarbektassov.socialmediarest.exceptions.post.PostNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionsHandlerController {

//    @ExceptionHandler
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public ResponseEntity<ErrorResponse> handleAuthException(AuthenticationCredentialsNotFoundException e) {
//        ErrorResponse response = new ErrorResponse(e.getMessage());
//        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
//    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handlePersonNotFoundException(PersonNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlePersonNotCreatedException(PersonNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handlePostNotFoundException(PostNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handlePostNotCreatedException(PostNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(FriendshipNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleFriendshipNotFoundException() {
        ErrorResponse response = new ErrorResponse("Friendship not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleFriendshipNotCreatedException(FriendshipNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MessageNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> handleMessageNotFoundException() {
        ErrorResponse response = new ErrorResponse("Message not found");
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleMessageNotCreatedException(MessageNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
