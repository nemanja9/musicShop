package fon.silab.web.an.ainmusicshop.hashing;

import org.springframework.security.crypto.bcrypt.BCrypt;
public class PasswordGenerator {


    public static String generateHashedPass(String password){
        
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }
    public static boolean checkPass(String uneta, String izBaze){
        try{
        if (BCrypt.checkpw(uneta, izBaze))
            return true;
        else 
            return false;
    }
    catch(IllegalArgumentException e){
    e.printStackTrace(); // ovde ce baciti IllegalArgumentException ako sifra iz baze nije hesirana nego plain text
    }
        return false;
    }
    
    

}
