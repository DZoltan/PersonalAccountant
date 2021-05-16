package accountant.model;

import accountant.model.DAO.ProfileDAO;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.Handler;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.sql.SQLOutput;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Class representing the connection between the {@link Profile} and Database.
 * */

public class ProfileHandler {
    public int id = 0;
    public Profile profile = new Profile();

     Jdbi jdbi = Jdbi.create("jdbc:sqlite:test.db")
             .installPlugin(new SqlObjectPlugin());

     Handle handle = jdbi.open();
     ProfileDAO dao = handle.attach(ProfileDAO.class);

     /**
      * Constructor of Class.
      * Create tables in Database, which can store the {@link Profile} and  password dates.
      * */
     public ProfileHandler() {
         try {
            dao.createProfileTable();
            dao.createPasswordTable();
         } catch (Exception e) {
             System.out.println(e);
         }
     }

    /**
     * Check Profile table in Database.
     * If the table already contain a profile, the return value is true.
     * */
    public boolean checkProfiles() {
        if(dao.listProfile().size() !=0){
            return true;
        }
        else return false;
    }
    /***
     * Store the profile and password data in database.
     * First pass the password the Encryptor, and store only the encrypted password.
     * @param password password of the user from GUI.
     * @param username username of the user from GUI.
     */

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
    /**
     * Update the profile with new balance, and store in database.
     * @param id the id of the user.
     * @param balance The new balance of the user, which want to store.
     * */
    public void updateBalance(int id, int balance){
         dao.updateBalance(id,balance);
    }


    /**
     * This method representing the login to application. Pass the password to Encryptor.
     * If the output is same as the stored password, the login is successful.
     * @param password password of the user from GUI.
     * @param username username of the user from GUI.
     * @return If the DB contain the valid dates, the return value is true, otherwise false.
     * @throws NoSuchElementException If the DB doesn't contain the user.
     * */
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
    /**
     * With this method reach the application a Profile data.
     * @param id The id of a user.
     * @return The profile for the specified id.
     * */
    public Profile getProfileFromId(int id){
         return dao.getProfileFromID(id).orElseThrow();
    }

    /**
     * If the user register a profile, this method give an id.
     * @param profileList list of all profiles.
     * @return if the list is not empty, max +1, otherwise 0.
     * */
    public int getNewProfileId(List<Profile> profileList){
        if(profileList.size() == 0) return 0;
        else {
            return  profileList.stream().mapToInt(Profile::getId).max().orElseThrow() + 1;
        }
    }
    /**
     * This method representing the Caeser-Encryptor.
     * @param password The password to encrypt.
     * @param CryptNum The key of the Encryptor.
     * @return Enrypted text.
     * */
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
