package test.club.javalearn.crm.web;

import org.junit.Test;

/**
 * crm-parent
 *
 * @author king-pan
 * @date 2018-03-05
 **/
public class FinallyTest {

    public static void main(String[] args) {
        //System.out.println(getInt());
        try {
            System.out.println(getDouble());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(getInt());
        System.out.println(getInt());
    }


    private static int getInt(){
        int i = 0;
        try {
            i = 1;
            System.out.println("初始化i");
            System.exit(0);
            i= i*12;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            System.out.println("getInt method's finally");
        }
        return i;
    }

    private static double getDouble(){
        double i = 0;
        try {
            int d = 0;
            System.out.println("初始化getDouble'i");
            i = 100/d;
            i= i*12;
            System.out.println(i);
        }catch (Exception e){
            e.printStackTrace();
            System.exit(0);
        }finally {
            System.out.println("getDouble method's finally");
        }
        return i;
    }



}
