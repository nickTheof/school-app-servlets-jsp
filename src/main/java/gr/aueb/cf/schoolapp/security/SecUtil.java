package gr.aueb.cf.schoolapp.security;

import org.mindrot.jbcrypt.BCrypt;

public class SecUtil {
    private SecUtil() {

    }

    public static String hashPassword(String password) {
        int workload = 12;
        String salt = BCrypt.gensalt(workload);
        return BCrypt.hashpw(password, salt);
    }

    public static Boolean isPasswordValid(String inputPassword, String storedHashPassword) {
        return BCrypt.checkpw(inputPassword, storedHashPassword);
    }
}
