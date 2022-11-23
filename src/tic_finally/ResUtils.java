package tic_finally;

import java.io.Closeable;
import java.io.IOException;

public class ResUtils {
	public static void closeAll(Closeable...ables){
		for (Closeable cb : ables) {
			if(cb!=null){
				try{
					cb.close();
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}
}
