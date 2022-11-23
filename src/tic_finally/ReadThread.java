package tic_finally;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ReadThread extends Thread {
  private String[][] panel = new String[3][3];
    private Socket socket;

    private String chess = "?";

    private BufferedReader socketReader;

    public ReadThread(Socket socket) throws IOException {
//		for (int i = 0; i < 3; i++) {
//			for (int j = 0; j < 3; j++) {
//				panel[i][j] = "";
//			}
//		}
        this.socket = socket;
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
    }

    @Override
    public void run() {

        try {
            while (true) {
                String msg = socketReader.readLine();
                System.out.println(msg);
//                if (msg.length() == 1) {
//                    this.chess = msg;
//                }
//				if (msg.contains("X")) chess = "X";
//				if (msg.contains("O")) chess = "O";
                if (msg.length() == 5) {
                    int x = Integer.parseInt(msg.split(" ")[1]);
                    int y = Integer.parseInt(msg.split(" ")[2]);
                    panel[x][y] = msg.split(" ")[0];
                    print();
                }
//                if (!chess.equals("?") && checkWin()) {
//                    System.out.println("you win!");
//					ResUtils.closeAll(socket, socketReader);
//                }
//                if (!chess.equals("?") && checkLose()) {
//                    System.out.println("you lose");
//					ResUtils.closeAll(socket, socketReader);
//                }
//				if (msg.contains("finish")){
//					int n = Integer.parseInt(msg.split("#")[0]);
//					n /= 2;
//					panel = TcpServer.panel.get(n);
//					print();
////					panel = TcpServer.panel.get()
//				}
//				if (msg.contains("#") && !chess.equals("?")) {
//					String context = msg.split("#")[1];
//					int x = Integer.parseInt(context.split(" ")[0]);
//					int y = Integer.parseInt(context.split(" ")[1]);
////					panel[x][y] = Objects.equals(chess, "X") ? "O" : "X";
//					if (TcpServer.map.containsKey(enemy)){
//						panel = TcpServer.map.get(enemy);
//					}
//					panel[x][y] = Objects.equals(chess, "X") ? "O" : "X";
//					TcpServer.map.put(this.getName(), panel);
//					System.out.println(TcpServer.map.size());
//					print();
//					System.out.println("your turn!");
////					TcpServer.map.put()
//				} else if (msg.contains("#")){
//					String context = msg.split("#")[1];
//					chess = context.split(" ")[1];
//					enemy = context.split(" ")[0];
//					System.out.println("your enemy is " + enemy + " and your chess is "+ chess);
//				} else {
//					System.out.println(msg);
//				}
            }
        } catch (IOException e) {
            System.out.println("服务器异常终止");
            System.exit(0);
//			e.printStackTrace();
        } finally {
            ResUtils.closeAll(socket, socketReader);
        }

    }

    private void print() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(panel[i][j] + " ");
            }
            System.out.println();
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (panel[i][0].equals(chess) && panel[i][1].equals(chess) && panel[i][2].equals(chess)) return true;
            if (panel[0][i].equals(chess) && panel[1][i].equals(chess) && panel[2][i].equals(chess)) return true;
        }
        if (chess.equals(panel[0][0]) && chess.equals(panel[1][1]) && chess.equals(panel[2][2])) return true;
        return chess.equals(panel[0][2]) && chess.equals(panel[1][1]) && chess.equals(panel[2][0]);
    }

    private boolean checkLose() {
        String enemy = "";
        if (chess.equals("X")) enemy = "O";
        if (chess.equals("O")) enemy = "X";
        for (int i = 0; i < 3; i++) {
            if (panel[i][0].equals(enemy) && panel[i][1].equals(enemy) && panel[i][2].equals(enemy)) return true;
            if (panel[0][i].equals(enemy) && panel[1][i].equals(enemy) && panel[2][i].equals(enemy)) return true;
        }
        if (enemy.equals(panel[0][0]) && enemy.equals(panel[1][1]) && enemy.equals(panel[2][2])) return true;
        return enemy.equals(panel[0][2]) && enemy.equals(panel[1][1]) && enemy.equals(panel[2][0]);
    }
}
