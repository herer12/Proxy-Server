package model.Database;

import java.util.Date;

public class User {
    private int userID;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private Date created;
    private int is_active;

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
