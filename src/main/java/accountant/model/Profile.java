package accountant.model;

import lombok.Data;

@Data
public class Profile {
    public int id;
    public String userName;
    public int balance;

    public Profile(int id, String userName, int balance) {
        this.id = id;
        this.balance = balance;
        this.userName = userName;
    }

}
