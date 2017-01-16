package com.zhenzhen.datestorage.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhenzhen on 2017/1/10.
 */

public class FileStorage {

    public void addToFile(String path){

        File files = new File(path);
        try {
            FileInputStream ins = new FileInputStream(files);
            InputStreamReader inputStreamReader = new InputStreamReader(ins);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            bufferedReader.readLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
