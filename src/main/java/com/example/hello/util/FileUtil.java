package com.example.hello.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author zhoukai
 * @date 2020/7/31 16:54
 */
public class FileUtil {

    public static void setUserAndPass(String userName, String Pass) {
        PrintWriter outputStream;

        try {
            outputStream = new PrintWriter(new FileOutputStream("user_pass.txt"));
            outputStream.println(userName + "#" + Pass);
            outputStream.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static String getUserAndPass() {
        Scanner scan = null;
        try {
            scan = new Scanner(new FileInputStream("user_pass.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return scan.nextLine();
    }

}


