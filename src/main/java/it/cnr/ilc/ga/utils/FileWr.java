/**
 * 
 */
package it.cnr.ilc.ga.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * @author Angelo Del Grosso
 *
 */
public class FileWr {
	
	//static FileOutputStream fos;
	//static Writer out;
	/**
	 * 
	 */
//	static{
//		try{
//		fos = new FileOutputStream("c:\\tmp\\test.txt");
//		out = new OutputStreamWriter(fos,"UTF-8");
//		}catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	public FileWr() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param args
	 */
	
	public static boolean print(String input){
		//System.err.println("in fileWR");
		//System.out.println(input);
		if(!"".equals(input)){
			try{
				//FileOutputStream fos = new FileOutputStream("c:\\tmp\\test.txt");
				//Writer out = new OutputStreamWriter(fos,"UTF-8");
				FileWriter fw = new FileWriter("c:\\tmp\\saussure_idx.txt",true);
				BufferedWriter out = new BufferedWriter(fw);
				out.write(input);
				out.newLine();
				System.out.println(input);
				System.err.println("dopo scrittura");
				out.close();
			}catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FileWr.print("ciao\n");
		FileWr.print("ρω\n");

	}

}
