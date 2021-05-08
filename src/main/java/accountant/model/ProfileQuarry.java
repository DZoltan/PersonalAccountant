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
    public Profile profile = new Profile();

     Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db")
             .installPlugin(new SqlObjectPlugin());

     Handle handle = jdbi.open();
     ProfileDAO dao = handle.attach(ProfileDAO.class);

     public ProfileQuarry() {
         try {
            dao.createProfileTable();
            dao.createPasswordTable();
         } catch (Exception e) {
             System.out.println(e);
         }
     }


    public boolean checkProfiles() {
        if(dao.listProfile().size() !=0){
            return true;
        }
        else return false;
    }

    public void CreateProfile (String username, String password){

    try {
        id = dao.listProfile().size() + 1;
        String Encrypted = Enrcyptor(password, id);
        Profile new_profile = new Profile(id, username, 0);
        dao.insertNewProfile(new_profile);
        dao.insertNewPassword(id, Encrypted);
    }
    catch (Exception e){
        System.out.println(e);
    }
    }

    public void test_method(){
        try{
            dao.listProfile().forEach(System.out::println);
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

    public boolean loginProfile (String username, String password){
    int balance = 0;
    int profile_id = 0;
    String EncryptedPasswordFromDb = "";

    try{
        profile_id = Integer.parseInt(dao.getIdForLogin(username).orElseThrow());
        EncryptedPasswordFromDb = dao.getPasswordForLogin(profile_id).orElseThrow();
    }
    catch(NoSuchElementException e){

    }
    catch (Exception e ){
        System.out.println("Something went wrong.... _@_/' " + e );
    }
    if(EncryptedPasswordFromDb != "") {
        String Encrypted = Enrcyptor(password, profile_id);
        if (EncryptedPasswordFromDb.contentEquals(Encrypted)) {
            Profile used_profile = new Profile(profile_id, username, balance);
            profile = used_profile;
            return true;
        } else {
            return false;
        }
    }

    return false;
    }

    public Profile getProfileFromId(int id){
         return dao.getProfileFromID(id).orElseThrow();
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
