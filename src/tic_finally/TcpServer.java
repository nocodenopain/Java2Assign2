package tic_finally;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TcpServer extends Application {
	
	public static List<ServerThread> list = new ArrayList<>();

	public static Map<String, String[][]> map = new HashMap<>();

//	public static Map<Integer, String[][]> panel = new HashMap<>();

	public static void main(String[] args) {
//		launch(args);
		try {
			ServerSocket socket = new ServerSocket(34567);
			System.out.println("服务器监听34567端口。。。。");
			int cnt = 1;
			while(true){
	Socket accept = socket.accept();
				cnt++;
				ServerThread st = new ServerThread(accept, cnt);
				st.start();
				list.add(st);		//将对象放入集合中
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();

			fxmlLoader.setLocation(getClass().getClassLoader().getResource("mainUI.fxml"));
			Pane root = fxmlLoader.load();
			primaryStage.setTitle("Tic Tac Toe");
			primaryStage.setScene(new Scene(root));
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
