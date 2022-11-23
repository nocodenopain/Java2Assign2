package tic_finally;

import java.io.*;
import java.net.Socket;

public class WriteThread extends Thread{

	private String chess = "";
	private Socket socket;
	private BufferedReader socketReader;  
	private BufferedWriter socketWriter;
	public WriteThread(Socket socket) throws IOException {
		this.socket = socket;
		socketReader = new BufferedReader(new InputStreamReader(System.in));
		socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
	}
	@Override
	public void run() {
		System.out.println("请输入你的姓名：");
		try {
			while(true){
				String msg = socketReader.readLine();   //从键盘读入数据
				socketWriter.write(msg);
				socketWriter.newLine();
				socketWriter.flush();   //写入缓冲中，并刷新
//				if (msg.contains("#")) System.out.println("wait!");
//				if (msg.contains("X")) chess = "O";
//				else chess = "X";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			ResUtils.closeAll(socket,socketReader,socketWriter);
		}   
	}

}
