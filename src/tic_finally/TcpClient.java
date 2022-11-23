package tic_finally;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.Socket;

public class TcpClient extends Application {
	public static void main(String[] args) {
	try {
	  Socket socket = new Socket("localhost",34567);
			new ReadThread(socket).start();
			new WriteThread(socket).start();
			launch(args);
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


