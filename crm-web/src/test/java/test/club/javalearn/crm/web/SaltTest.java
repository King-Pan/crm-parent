package test.club.javalearn.crm.web;

import club.javalearn.crm.utils.Md5Utils;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-03-05
 **/
public class SaltTest {

    @Test
    public void test(){
        String password =  Md5Utils.encryptPassword("admin","888888","b23bed5df5c568a66835b1fad2ed69f9824fd26d");
        System.out.println(password);
        //$2a$10$emy7rkhRS0tnFLWf418kf.T30TfRggT47Qoh20iMSZ9Vzr4mVIByG
    }
}
