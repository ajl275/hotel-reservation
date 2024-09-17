package model;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * A customer with properly formatted email
 *
 * @author Amy Lanclos
 */
public class Customer {
    private final String firstName;
    private final String lastName;
    private final String email;
    private static final String EMAIL_REGEX = "^(.+)@(.+).(.+)$";

    /**
     * Customer constructor, rejects invalidly formatted email addresses throwing
     * an InvalidArgumentException
     *
     * @param firstName First Name
     * @param lastName Last Name
     * @param email Customer's email
     */
    public Customer(String firstName, String lastName, String email) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        if(!pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Error, Invalid email, please enter a valid email: name@domian.extension");
        }
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        return "Name: " + firstName +" "+ lastName +"\nEmail: " + email;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o)
            return true;
        if(o == null) return false;
        if(o.getClass() == this.getClass()) {
            Customer c = (Customer)o;
            if((firstName == null && c.getFirstName() == null) || (!(firstName == null || c.getFirstName() == null) && firstName.equals(c.getFirstName()))) {
                if(Objects.equals(lastName, c.getLastName())) {
                    return Objects.equals(email, c.getEmail());
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 31 * hash + (firstName == null ? 0 : firstName.hashCode());
        hash = 31 * hash + (lastName == null ? 0 : lastName.hashCode());
        hash = 31 * hash + (email == null ? 0 : email.hashCode());
        return hash;
    }
}
