package com.example.downloaddatabag;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileIOUtil {

	/**
	 * 读取文本文件
	 * 
	 * @param path
	 * @return
	 */
	public static String readFile(String path) {
		StringBuffer stringBuffer = new StringBuffer();
		BufferedReader in;
		try {
			in = new BufferedReader(new FileReader(path));
			String line = null;
			while ((line = in.readLine()) != null) {
				stringBuffer.append(line);
			}
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}

}
