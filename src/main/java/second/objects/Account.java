package second.objects;

import java.sql.Timestamp;

public class Account {
    private final int account_id;
    private String firstname;
    private String lastname;
    private String email;
    private String password;
    private String phone;
    private Timestamp created_at;
    private int is_active;

    Account(int account_id, String firstname, String lastname, String email, String password, String phone, Timestamp created_at) {
        this.account_id = account_id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.created_at = created_at;
        this.is_active = 1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Account{");
        sb.append("account_id=").append(account_id);
        sb.append(", firstname='").append(firstname).append('\'');
        sb.append(", lastname='").append(lastname).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", created_at=").append(created_at);
        sb.append(", is_active=").append(is_active);
        sb.append('}');
        return sb.toString();
    }

    public int getAccount_id() {
        return account_id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public boolean getIs_active() {
        return is_active == 1;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }
}
