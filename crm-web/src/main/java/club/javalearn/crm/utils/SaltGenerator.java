package club.javalearn.crm.utils;

import java.security.SecureRandom;
import java.util.Random;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-01-14
 **/
public class SaltGenerator {

    public static String createSalt(){
        Random ranGen = new SecureRandom();
        byte[] aesKey = new byte[20];
        ranGen.nextBytes(aesKey);
        StringBuilder hexString = new StringBuilder();
        for(int i = 0; i < aesKey.length; i++) {
            String hex = Integer.toHexString(0xff & aesKey[i]);
            if (hex.length() == 1){
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }
}
