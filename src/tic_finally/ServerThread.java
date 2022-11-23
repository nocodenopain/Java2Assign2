package tic_finally;

import java.io.*;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerThread extends Thread {

    private final int number;

    public int turn = 1;
    public Map<String, String> map = new HashMap<>();


    public Map<String, String> color = new HashMap<>();

    @Override
    public void run() {
        welcome();
        try {
            while (true) {
                //在此处接收客户端发送的聊天信息，无论是那个客户端
                String msg;
                msg = socketReader.readLine();
                //定义规则：满足私聊的规则，则私发给某个人；发送时判断名字要相同
                //@XXX#XXXXXXXX
//                if (msg.startsWith("@") && (msg.contains("#"))) {
                sendToOne(msg);
//                } else {
                //获取信息后，要转发出去
//                    sendToOther(this.getName() + "说：" + msg);         //群发
//                }
            }
        } catch (IOException e) {
            //在此处捕获到客户端的退出信息；
            //1.在此处删除集合中对应的对象；
            //转发其他人，该用户退出
            TcpServer.list.remove(this);
//            sendToOther(this.getName() + "已经退出了游戏");
            try {
                for (ServerThread st : TcpServer.list) {
                    if (st.number / 2 == this.number / 2 && st.number != this.number) {        //除了自身不转发，其他的客户端都转发一份
                        st.sendMsg(this.getName() + "已经退出了游戏");        //注意一定要是set(其他线程）去调
                    }
                }
            } catch (IOException es) {
                es.printStackTrace();
            }
        } finally {
            ResUtils.closeAll(socket, socketWriter, socketReader);
        }
    }

    private void sendToOther(String msg) {
        try {
            for (ServerThread st : TcpServer.list) {
                if (st != this && st.number > this.number) {        //除了自身不转发，其他的客户端都转发一份
                    st.sendMsg(msg);        //注意一定要是set(其他线程）去调
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendToOne(String msg) {
        try {
            //字符串的分割，然后放入数组中
//            String[] datas = msg.substring(1).split("#");
//            String name = datas[0];        //记录姓名
//            String content = datas[1];        //记录内容
//            String[][] tmp;
//            if (panel.containsKey(number / 2)){
//                tmp = panel.get(number / 2);
//            } else {
//                tmp = new String[3][3];
//            }
//            int x = Integer.parseInt(msg.split(" ")[0]);
//            int y = Integer.parseInt(msg.split(" ")[1]);
//            tmp[x][y] = number % 2 == 0 ? "X" : "O";
//            panel.put(number / 2, tmp);
            if (msg.contains("游戏")) {
                for (ServerThread st : TcpServer.list) {
                    if (st.number / 2 == this.number / 2 && st.number != this.number) {        //在集合中找到名字相同的，就发一份
                        st.sendMsg(msg);
                    }
                }
            } else {
                for (ServerThread st : TcpServer.list) {
                    if (st.number / 2 == this.number / 2 && st.number != this.number) {        //在集合中找到名字相同的，就发一份
//                        sendMsg("X " + msg);
                        if (turn % 2 == 0){
                            turn++;
                            st.sendMsg("X " + msg);
                            sendMsg("X " + msg);
                        } else {
                            st.sendMsg("O " + msg);
                            sendMsg("O " + msg);
                            turn++;
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMsg(String msg) throws IOException {
        socketWriter.write(msg);
        socketWriter.newLine();
        socketWriter.flush();
    }

    private void welcome() {
        try {
            String name = socketReader.readLine();
            String msg;
            if (this.number % 2 == 0) {
                msg = "【系统消息】 欢迎" + name + "进入列表\n等待挑选对手";
                sendToOther(msg);
            } else {
                msg = "游戏开始, 编号为" + number / 2;
                sendToOne(msg);
            }
            socketWriter.write(msg);
            socketWriter.newLine();
            socketWriter.flush();
            this.setName(name);
        } catch (IOException e) {

//            e.printStackTrace();
        }
    }

    private Socket socket;
    private BufferedReader socketReader;
    private BufferedWriter socketWriter;

    //    public Map<Integer, String[][]> panel;
    public ServerThread(Socket socket, int a) throws IOException {
        this.socket = socket;
        this.number = a;
//        this.panel = panel;
        socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        socketWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }
}
