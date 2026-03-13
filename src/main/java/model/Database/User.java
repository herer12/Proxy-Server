package model.Database;

import java.time.LocalDateTime;
import java.util.Date;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private LocalDateTime created;
    private int is_active;

    public User(int userID, String firstName, String lastName, String email, String password, String phone, LocalDateTime created, int is_active) {
        this.userID = userID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.created = created;
        this.is_active = is_active;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhone() {
        return phone;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public int getIs_active() {
        return is_active;
    }

    public String getPassword() {
        return password;
    }
    public String getEmail() {
        return email;
    }
    public int getUserID() {
        return userID;
    }
}
