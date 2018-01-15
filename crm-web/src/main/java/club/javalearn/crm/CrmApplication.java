package club.javalearn.crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-11-18
 **/
@ServletComponentScan
@SpringBootApplication
public class CrmApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmApplication.class,args);
    }
}
