package ansarbektassov.socialmediarest.exceptions.person;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException() {
        super("Person not found");
    }
}
