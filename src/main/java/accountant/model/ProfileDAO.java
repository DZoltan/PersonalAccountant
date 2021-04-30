package accountant.model;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

@RegisterBeanMapper(Profile.class)
public interface ProfileDAO {

    @SqlUpdate("""
            CREATE TABLE Profiles (
            id INTEGER PRIMARY KEY,
            username VARCHAR NOT NULL,
            balance INTEGER NOT NULL)
            """)
    void createProfileTable();

    @SqlUpdate("""
            CREATE TABLE Passwords (
            profile_id INTEGER PRIMARY KEY,
            password VARCHAR NOT NULL)
            
            """)
    void createPasswordTable();

    @SqlUpdate("INSERT INTO Profiles VALUES (:id, :username, :balance)")
    void insertNewProfile(@Bind("id") int id, @Bind("username") String username, @Bind("balance") int balance);

    @SqlUpdate("INSERT INTO Passwords VALUES (:profile_id, :password)")
    void insertNewPassword(@Bind("profile_id") int id, @Bind("password") String password);

    @SqlQuery("SELECT password FROM Passwords WHERE profile_id = : profile_id")
    Optional<String> login(@Bind("profile_id") int id);

    @SqlQuery("SELECT * FROM Profiles ORDER BY id")
    List<Profile> testProfile();
}
