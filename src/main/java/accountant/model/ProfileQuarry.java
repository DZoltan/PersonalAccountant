package accountant.model;

import accountant.model.DAO.ProfileDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.Handler;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.SQLOutput;
import java.util.NoSuchElementException;

public class ProfileQuarry{
    public int id = 0;

     Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db");


    public boolean checkProfiles ()  {
    try(Handle handle = jdbi.open()){
        ProfileDAO dao = handle.attach(ProfileDAO.class);
        if(dao.listProfile().size() !=0){
            return true;
        }
        else return false;
    }
    }

    public void CreateProfile (String username, String password){
    jdbi.installPlugin(new SqlObjectPlugin());


    //TODO: Megadott username-t és a titkosítótt jelszót az adatbáztisba küldi (encryptor)

    try ( Handle handle = jdbi.open()){
        ProfileDAO dao = handle.attach(ProfileDAO.class);
        id = dao.listProfile().size() + 1;
        String Encrypted = Enrcyptor(password, id);
        Profile new_profile = new Profile(id, username, 0);
        dao.insertNewProfile(new_profile.getId(), new_profile.getUserName(), new_profile.getBalance());
        dao.insertNewPassword(id, Encrypted);
    }
    }

    public void test_method(){
        try ( Handle handle = jdbi.open()){
            ProfileDAO dao = handle.attach(ProfileDAO.class);
            dao.listProfile().forEach(System.out::println);
        }
    }

    public void loginProfile (String username, String password){
    jdbi.installPlugin(new SqlObjectPlugin());

    int balance = 0;
    int profile_id = 0;
    String EncryptedPasswordFromDb = "";

    try(Handle handle = jdbi.open()){
        ProfileDAO dao = handle.attach(ProfileDAO.class);
        profile_id = Integer.parseInt(dao.getIdForLogin(username).orElseThrow());
        EncryptedPasswordFromDb = dao.getPasswordForLogin(profile_id).orElseThrow();
    }
    catch(NoSuchElementException e){
        System.out.println("A felhasználónév nem található");
    }
    catch (Exception e ){
        System.out.println("Something went wrong.... _@_/'");
    }
    if(EncryptedPasswordFromDb != "") {
        String Encrypted = Enrcyptor(password, profile_id);
        if (EncryptedPasswordFromDb.contentEquals(Encrypted)) {
            Profile used_profile = new Profile(id, username, balance);
            System.out.println("Helló, " + username);
        } else {
            System.out.println("A jelszó nem egyezik!");
        }
    }
    }


    public String Enrcyptor(String password, int CryptNum ){
        StringBuilder CryptedPass = new StringBuilder();
        for(int i = 0; i < password.length(); i++){
            int NumOfChar = password.charAt(i);
            int CryptedNumOfChar = NumOfChar + CryptNum * 7;
            char NewCharacter = (char) CryptedNumOfChar ;
            CryptedPass.append(NewCharacter);
            //System.out.println(i + " "+ password.charAt(i) + " " + NumOfChar + " " + CryptedNumOfChar + " " + NewCharacter );
        }

        return  CryptedPass.toString();
    }


}
