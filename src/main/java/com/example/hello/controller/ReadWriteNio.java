package com.example.hello.controller;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author zk
 * @date 2020/9/22 10:40
 */
public class ReadWriteNio {

    public static void main(String args[]) throws Exception {
        int bufSize = 100;
//        File fin = new File("E:\\jiahui\\2014-09-01.dat");
        File fin = new File("D:/桌面/银河/需求文件/数据拆分/SJSMX10911.DBF");

//        File fout = new File("E:\\jiahui\\res.txt");
        File fout = new File("dbf/newFileTest.dbf");

        System.out.print("开始读取并重写文件，请等待...");

        FileChannel fcin = new RandomAccessFile(fin, "r").getChannel();
        ByteBuffer rBuffer = ByteBuffer.allocate(bufSize);

        FileChannel fcout = new RandomAccessFile(fout, "rws").getChannel();
        ByteBuffer wBuffer = ByteBuffer.allocateDirect(bufSize);

        readFileByLine(bufSize, fcin, rBuffer, fcout, wBuffer);

        System.out.print("读写完成！");
    }

    /*读文件同时写文件*/
    public static void readFileByLine(int bufSize, FileChannel fcin, ByteBuffer rBuffer,
                                      FileChannel fcout, ByteBuffer wBuffer) {
        String enterStr = "\n";
        try {
            byte[] bs = new byte[bufSize];

            int size = 0;
            StringBuffer strBuf = new StringBuffer("");
            while ((size = fcin.read(rBuffer)) != -1) {
// while(fcin.read(rBuffer) != -1){
                if (size > 1 * 1024) {
                    break;
                }

                int rSize = rBuffer.position();
                rBuffer.rewind();
                rBuffer.get(bs);
                rBuffer.clear();
                String tempString = new String(bs, 0, rSize, "UTF-8");
                // System.out.println(size+": "+tempString);

                int fromIndex = 0;
                int endIndex = 0;
                while ((endIndex = tempString.indexOf(enterStr, fromIndex)) != -1) {
                    String line = tempString.substring(fromIndex, endIndex);
                    line = new String(strBuf.toString() + line + "\n");
                    System.out.println(size + ": " + line);
                    //System.out.print("</over/>");
                    //write to anthone file
                    writeFileByLine(fcout, wBuffer, line);

                    strBuf.delete(0, strBuf.length());
                    fromIndex = endIndex + 1;
                }
                if (rSize > tempString.length()) {
                    strBuf.append(tempString.substring(fromIndex, tempString.length()));
                } else {
                    strBuf.append(tempString.substring(fromIndex, rSize));
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*写文件*/
    public static void writeFileByLine(FileChannel fcout, ByteBuffer wBuffer, String line) {
        try {
            //write on file head
            //fcout.write(wBuffer.wrap(line.getBytes()));
            //wirte append file on foot
            fcout.write(wBuffer.wrap(line.getBytes()), fcout.size());

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


}
