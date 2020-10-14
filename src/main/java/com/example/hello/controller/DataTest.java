package com.example.hello.controller;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.*;
import java.util.Date;

/**
 * @author zk
 * @date 2020/9/21 9:14
 */
public class DataTest {
    public static void main(String[] args) {
        long begin = System.nanoTime();

//        String bigFilePath = "D:/桌面/银河/需求文件/数据拆分/SJSMX10911.DBF";
        String bigFilePath = "dbf/20200922122710.dbf";

        //rw : 设置模式为读写模式
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(bigFilePath, "rw");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        byte[] buf = new byte[1024 * 100];
        int len = 0;
        while (true) {
            try {
                len = raf.read(buf);
                if (len == -1) {
                    break;
                }
                System.out.println("读-当前记录指针位置：" + raf.getFilePointer());
//                write(new String(buf));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println(new String(buf));
            System.out.println("===");
        }
        System.out.println("用时:" + (System.nanoTime() - begin) / 1000000000 + "s");


//        try {
//            write();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private static void write(String msg) throws IOException {
        //读写的数据源
        File f = new File("dbf/" + DateFormatUtils.format(new Date(), "yyyyMMddHHmmss") + ".dbf");
        //指定文件不存在就创建同名文件
        if (!f.exists()) {
            f.createNewFile();
        }
        //rw : 设为读写模式
        RandomAccessFile raf = new RandomAccessFile(f, "rw");
        System.out.println("写-当前记录指针位置：" + raf.getFilePointer());
        //记录指针与文件内容长度相等
        raf.seek(raf.length());
        System.out.println("写-当前文件长度：" + raf.length());
        //以字节形式写入字符串
        raf.write(msg.getBytes());
//        raf.write("ABCDEFG写入尾部".getBytes());
        System.out.println("写-当前记录指针位置：" + raf.getFilePointer());
    }
}
