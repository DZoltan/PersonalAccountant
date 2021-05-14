package HandlerTest;

import accountant.model.Profile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import accountant.model.ProfileHandler;

import java.util.ArrayList;
import java.util.List;

public class testProfile {
    ProfileHandler profileHandler = new ProfileHandler();

    private String Encyptor(String text, int CryptNum){

        String encrypted = profileHandler.Enrcyptor(text, CryptNum);

        return profileHandler.Enrcyptor(encrypted, -CryptNum);
    }

    @Test
    void testEncryptor(){

        assertEquals( "alma", Encyptor("alma", 2));
        assertEquals( "alma", Encyptor("alma", 42));
        assertEquals( "körte", Encyptor("körte", 4));
        assertEquals( "körte", Encyptor("körte", 75));
    }

    @Test
    void testProfileId(){
        List<Profile> profile = new ArrayList<>();

        List<Profile> profile1 = new ArrayList<>();
        profile1.add(new Profile(0, "tesztAntal", 0));
        profile1.add(new Profile(1, "tesztBéla", 0));
        profile1.add(new Profile(2, "tesztCecil", 0));

        List<Profile> profile2 = new ArrayList<>();
        profile2.add(new Profile(0, "tesztAntal", 0));
        profile2.add(new Profile(4, "tesztBéla", 0));
        profile2.add(new Profile(3, "tesztCecil", 0));

        assertEquals(0, profileHandler.getNewProfileId(profile));
        assertEquals(3, profileHandler.getNewProfileId(profile1));
        assertEquals(5, profileHandler.getNewProfileId(profile2));

        
    }
}
