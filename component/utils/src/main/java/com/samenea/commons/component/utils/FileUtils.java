package com.samenea.commons.component.utils;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: Maziar Kaveh
 * Email:maziar.kaveh@gmail.com
 * Date: 11/29/11
 * Time: 3:21 PM
 */
public class FileUtils {
    public static void createFile(String path, byte[] content) throws IOException {
//        String str =  System.getProperty("file.separator");
        String str = "/";
        File dir = new File(path.substring(0, path.lastIndexOf(str)));
        dir.mkdirs();
        FileOutputStream fileOutputStream = new FileOutputStream(new File(path));
        fileOutputStream.write(content);
        fileOutputStream.close();
    }



    public static void createFile(String path, String content) throws IOException {
        createFile(path, content.getBytes());
    }

    public static void createFile(String path, InputStream inputStream) throws IOException {
        createFile(path, IOUtils.toByteArray(inputStream));
    }
}
