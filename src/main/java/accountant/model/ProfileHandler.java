package accountant.model;

import accountant.model.DAO.ProfileDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.Handler;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.SQLOutput;
import java.util.List;
import java.util.NoSuchElementException;

public class ProfileHandler {
    public int id = 0;
    public Profile profile = new Profile();

     Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db")
             .installPlugin(new SqlObjectPlugin());

     Handle handle = jdbi.open();
     ProfileDAO dao = handle.attach(ProfileDAO.class);

     public ProfileHandler() {
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
        id = getNewProfileId(dao.listProfile());
        String Encrypted = Enrcyptor(password, id);
        Profile new_profile = new Profile(id, username, 0);
        dao.insertNewProfile(new_profile);
        dao.insertNewPassword(id, Encrypted);
    }
    catch (Exception e){
        System.out.println(e);
    }
    }

    public void updateBalance(int id, int balance){
         dao.updateBalance(id,balance);
    }

    public boolean loginProfile (String username, String password){
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
            Profile used_profile = getProfileFromId(profile_id);
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

    public int getNewProfileId(List<Profile> profileList){
        if(profileList.size() == 0) return 0;
        else {
            return  profileList.stream().mapToInt(Profile::getId).max().orElseThrow() + 1;
        }
    }

    public String Enrcyptor(String password, int CryptNum ){
        StringBuilder CryptedPass = new StringBuilder();
        for(int i = 0; i < password.length(); i++){
            int NumOfChar = password.charAt(i);
            int CryptedNumOfChar = NumOfChar + CryptNum * 7 ;
            char NewCharacter = (char) CryptedNumOfChar ;
            CryptedPass.append(NewCharacter);
        }

        return  CryptedPass.toString();
    }


}
