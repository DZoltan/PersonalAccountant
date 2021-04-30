package accountant.model;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.util.Optional;

public class ProfileQuarry{
    public int id = 1;

     Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");

    public boolean checkProfiles ()  {
    //TODO: Adatbázis ellenőrzése, hogy van e már profil létrehozva
    return false;
    }

    public void CreateProfile (String username, String password){
    jdbi.installPlugin(new SqlObjectPlugin());
    String Encrypted = Enrcyptor(password, id);
    Profile new_profile = new Profile(id, username, 0);

    //TODO: Megadott username-t és a titkosítótt jelszót az adatbáztisba küldi (encryptor)

    try ( Handle handle = jdbi.open()){
        ProfileDAO dao = handle.attach(ProfileDAO.class);
        dao.createProfileTable();
        dao.createPasswordTable();
        dao.insertNewProfile(new_profile.getId(), new_profile.getUserName(), new_profile.getBalance());
        dao.insertNewPassword(id, Encrypted);
        dao.testProfile().forEach(System.out::println);
    }
    }

    public void test_method(){
        try ( Handle handle = jdbi.open()){
            ProfileDAO dao = handle.attach(ProfileDAO.class);
            dao.testProfile().forEach(System.out::println);
        }
    }

    public void LoginProfile (String username, String password){
    int balance = 0;
    String Encrypted = Enrcyptor(password, id);

    //TODO: A felhasználó nevet és a megadott jelszót(enrypted) kéri le az adatbázisból

    Profile used_profile = new Profile(id, username, balance);
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
