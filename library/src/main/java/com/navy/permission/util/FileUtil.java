package com.navy.permission.util;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Navy on 2016/8/6.
 */
public class FileUtil {

    /**
     * 绝对路径
     * @param path
     */
    public static boolean writeStrinToFile(String path, String content){
        File file = new File(path);
        try {

            if (! file.exists()) {
                File directory  = file.getParentFile();
                if (!directory.exists()){
                    directory.mkdirs();
                }
                file.createNewFile();
            }
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(content.toString().getBytes());
            fos.flush();
            fos.close();
            return true;
        }  catch (IOException e) {

            e.printStackTrace();
        }
        return false;
    }

    /**
     * 读取文件中的了内容
     * @param path
     */
    public static String readStrinToFile(String path){
        File file = new File(path);
        try {
            if (! file.exists()) {
                throw new FileNotFoundException(file.getAbsolutePath()+"不存在");
            }
            FileInputStream fis = new FileInputStream(file);
            int length = fis.available();
            byte [] buffer = new byte[length];
            fis.read(buffer);
            fis.close();
            return new String(buffer);
        }  catch (IOException e) {

            e.printStackTrace();
        }
        return null;
    }
}
