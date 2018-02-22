package test.club.javalearn.crm.web;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * crm-parent
 *
 * @author king-pan
 * @create 2017-12-09
 **/
public class ArrayTest {

    @Test
    public void listToArray(){
        List<String> stringList = new ArrayList<>();
        stringList.add("zs");
        stringList.add("ls");
        stringList.add("ww");

        String[] strAry = new String[]{};
        System.out.println(Arrays.toString(stringList.toArray(strAry)));
    }

    @Test
    public void testMax(){
        System.out.println(Long.MAX_VALUE+2);
        long time = 199902222222222222L;
        System.out.println(time);
        double d = 2.0;
        System.out.println(d);
    }



}
