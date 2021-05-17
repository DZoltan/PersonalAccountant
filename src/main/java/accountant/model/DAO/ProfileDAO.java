package accountant.model.DAO;

import accountant.model.Profile;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;
/**
 * DAO class for {@link Profile} entity.
 * */
@RegisterBeanMapper(Profile.class)
public interface ProfileDAO {

    /**
     * Create a table for {@link Profile} entity.
     * */
    @SqlUpdate("""
            CREATE TABLE IF NOT EXISTS Profiles (
            id INTEGER PRIMARY KEY,
            username VARCHAR NOT NULL,
            balance INTEGER NOT NULL)
            """)
    void createProfileTable();

    /**
     * Create a table, where the users can store encrypted passwords.
     * */
    @SqlUpdate("""
            CREATE TABLE IF NOT EXISTS Passwords (
            profile_id INTEGER PRIMARY KEY,
            password VARCHAR NOT NULL)
            
            """)
    void createPasswordTable();

    /**
     * Store a new {@link Profile} entity in Database.
     * @param profile Entity of {@link Profile}.
     * */
    @SqlUpdate("INSERT INTO Profiles VALUES (:id, :username, :balance)")
    void insertNewProfile(@BindBean Profile profile);

    /**
     * Store a new Password for {@link Profile} in Database.
     * @param password Encrypted Password of User.
     * @param id Profile ID.
     * */
    @SqlUpdate("INSERT INTO Passwords VALUES (:profile_id, :password)")
    void insertNewPassword(@Bind("profile_id") int id, @Bind("password") String password);

    /**
     * Modify the actual balance in Database.
     * @param id Profile ID.
     * @param balance New balance of user.
     * */
    @SqlUpdate("UPDATE Profiles SET balance = :balance WHERE (id = :id)")
    void updateBalance(@Bind("id") int id, @Bind("balance") int balance);

    /**
     * For login needs a password of User.
     * @param id Profile ID.
     * @return Enrcrypted password of the User, which matches with the ID.
     * */
    @SqlQuery("SELECT password FROM Passwords WHERE profile_id = :profile_id")
    Optional<String> getPasswordForLogin(@Bind("profile_id") int id);

    /**
     * Select the Id, which belongs to user.
     * @param username Name of User.
     * @return
     * */
    @SqlQuery("SELECT id FROM Profiles WHERE username = :username")
    Optional<String> getIdForLogin(@Bind("username") String username);

    /**
     * Select the Profile, which belongs to user.
     * @param id Profile ID.
     * @return Entity of {@link Profile}, which belongs to User.
     * */
    @SqlQuery("SELECT * FROM Profiles WHERE id = :id")
    Optional<Profile>getProfileFromID(@Bind("id") int id);

    /**
     * List all {@link Profile}, ordered by ID.
     * @return List of {@link Profile}.
     * */
    @SqlQuery("SELECT * FROM Profiles ORDER BY id")
    List<Profile> listProfile();

}
