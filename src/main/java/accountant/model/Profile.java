package accountant.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class representing profiles, which implement a multi-user application.
 * */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    int id;
    String username;
    int balance;

}
