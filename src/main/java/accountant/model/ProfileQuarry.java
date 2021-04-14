package accountant.model;

public class ProfileQuarry {
    public int id;

    public boolean checkProfiles(){
        //TODO: Adatbázis ellenőrzése, hogy van e már profil létrehozva
        return false;
    }

    public void CreateProfile(String username, String password){
        //TODO: Megadott username-t és a titkosítótt jelszót az adatbáztisba küldi (encryptor)
        String Encrypted = Enrcyptor(password, id);
    }

    public void LoginProfile(String username, String password){
        //TODO: A felhasználó nevet és a megadott jelszót(enrypted) kéri le az adatbázisból
        String Encrypted = Enrcyptor(password, id);
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
