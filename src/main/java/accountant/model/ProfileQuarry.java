package accountant.model;

import org.jdbi.v3.core.Jdbi;

public class ProfileQuarry {
    public int id;

    Profile profile = new Profile();

    public boolean checkProfiles(){
        //TODO: Adatbázis ellenőrzése, hogy van e már profil létrehozva
        return false;
    }

    public void CreateProfile(String username, String password){
        String Encrypted = Enrcyptor(password, id);

        //TODO: Megadott username-t és a titkosítótt jelszót az adatbáztisba küldi (encryptor)

        Profile new_profile = new Profile(id, username, 0);
        profile = new_profile;
    }

    public void LoginProfile(String username, String password){
        int balance = 0;
        String Encrypted = Enrcyptor(password, id);

        //TODO: A felhasználó nevet és a megadott jelszót(enrypted) kéri le az adatbázisból

        Profile used_profile = new Profile(id, username, balance);
        profile = used_profile;
    }

    public String Enrcyptor(String password, int CryptNum ){
        StringBuilder CryptedPass = new StringBuilder();
        for(int i = 0; i < password.length(); i++){
            int NumOfChar = password.charAt(i);
            int CryptedNumOfChar = NumOfChar + CryptNum * 7;
            char NewCharacter = (char) CryptedNumOfChar ;
            CryptedPass.append(NewCharacter);
            System.out.println(i + " "+ password.charAt(i) + " " + NumOfChar + " " + CryptedNumOfChar + " " + NewCharacter );
        }

        return  CryptedPass.toString();
    }

    public static void main(String[] args) {
        int id = 15;
         ProfileQuarry test = new ProfileQuarry();
         String testpass = test.Enrcyptor("áfonya", id );
         System.out.println(testpass);
        System.out.println(test.Enrcyptor(testpass, -id ));
    }


}
