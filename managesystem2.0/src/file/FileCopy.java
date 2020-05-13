package file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCopy {
	
	private static FileInputStream fis ;
	private static FileOutputStream fos;
	

	public static void main(String[] args) {
	
		File a = new File("F:\\新桌面\\java笔记.docx");
		File b = new File("F:\\新桌面\\共享文件的空间\\"+a.getName());
		
		copy(a,b);

	}
	
	public static void copy(File in,File out) {
		
		try {
			fis = new FileInputStream(in);
			fos = new FileOutputStream(out);
			
			byte[] bytes = new byte[fis.available()];
			
			fis.read(bytes);
			fos.write(bytes);
			fos.flush();
			
			fos.close();
			fis.close();
			System.out.println("copy successfully");
			
		} catch (FileNotFoundException e) {
			
			e.printStackTrace();
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
