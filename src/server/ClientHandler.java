package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import common.ChatModel;
import common.Constant;

/**
 *
 * @author letoan
 */
public class ClientHandler implements Runnable {

    private final Server server = new Server();
    private final Socket socket;
    private String nickname = null; // dùng để phân biệt nickname được chọn hay chưa
    private ArrayList<String> listUserBlock = new ArrayList<>();
    BufferedReader in;
    BufferedWriter out;

    public ClientHandler(Socket socket) throws IOException {
        this.socket = socket;
        this.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public ArrayList<String> getListUserBlock() {
        return listUserBlock;
    }

    public void setListUserBlock(ArrayList<String> listUserBlock) {
        this.listUserBlock = listUserBlock;
    }

    public Socket getSocket() {
        return socket;
    }

    @Override
    public void run() {
        System.out.println("Client" + socket.toString() + " accepted");

        while (true) {
            try {
                String request = in.readLine();
                if (request == null) {
                    break;
                }
                String[] message = request.split(Constant.spread);
                switch (message[0]) {
                    case "choose" -> {
                        boolean isDuplicated = false;
                        for (ClientHandler client : Server.listUserActive) {
                            if (client.getNickname() != null && client.getNickname().equals(message[1])) {
                                sendClient("choose" + Constant.spread+ "false");
                                isDuplicated = true;
                                break;
                            }
                        }
                        if (!isDuplicated) {
                            sendClient("choose" + Constant.spread + "true" + Constant.spread + message[1]);                            
                            setNickname(message[1]);
                            findPair();
                        }
                        break;
                    }
                    case "confirm" -> {
                        String userchat = message[2]; // người bạn muốn chat cùng
                        if (message[1].equals("true") && !server.listChatParing.isEmpty()) {
                            for (ChatModel cp : server.listChatParing) {
                                if (cp.getNickname1().equals(userchat)) {
                                    cp.setResponse1(true);
                                    if (cp.isResponse2() == true) {
                                        listUserBlock.removeAll(listUserBlock);
                                        sendClient("joinchat"+ Constant.spread + userchat);
                                        for (ClientHandler tc : server.listUserActive) {
                                            if (tc.getNickname() != null && tc.getNickname().equals(userchat)) {
                                                tc.sendClient("joinchat"+ Constant.spread + getNickname());
                                                break;
                                            }
                                        }
                                        server.listChatParing.remove(server.listChatParing.indexOf(cp));
                                        break;
                                    }
                                    break;
                                }
                                if (cp.getNickname2().equals(userchat)) {
                                    cp.setResponse2(true);
                                    if (cp.isResponse1() == true) {
                                        listUserBlock.removeAll(listUserBlock);
                                        sendClient("joinchat"+ Constant.spread + userchat);
                                        for (ClientHandler tc : server.listUserActive) {
                                            if (tc.getNickname() != null && tc.getNickname().equals(userchat)) {
                                                tc.sendClient("joinchat"+ Constant.spread + getNickname());
                                                break;
                                            }
                                        }
                                        server.listChatParing.remove(server.listChatParing.indexOf(cp));
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (message[1].equals("false")) {
                            server.listUserWaitChat.add(userchat); // đẩy user đó vào danh sách chờ
                            for (ChatModel cp : server.listChatParing) {
                                // xóa cặp đôi chat khi 1 user từ chối
                                if (cp.getNickname1().equals(userchat) || cp.getNickname2().equals(userchat)) {
                                    int i = server.listChatParing.indexOf(cp);
                                    server.listChatParing.remove(i);
                                    break;
                                }
                            }
                            sendClient("cancelchat"+ Constant.spread + userchat); // gửi thông báo từ chối friend
                            getListUserBlock().add(userchat);
                            findPair();
                            for (ClientHandler tc : server.listUserActive) {
                                if (tc.getNickname() != null && tc.getNickname().equals(userchat)) { // phía friend gửi lại
                                    tc.sendClient("cancelchat"+ Constant.spread + getNickname()); // gửi thông báo từ chối cho bản thân
                                    tc.getListUserBlock().add(getNickname());
                                    tc.findPair();
                                    break;
                                }
                            }

                        }
                        break;
                    }
                    case "sendmessage" -> {
                        for (ClientHandler tc : Server.listUserActive) {
                            if (message[2].equals(tc.nickname)) {
                                tc.sendClient("sendmessage"+ Constant.spread + message[1] + Constant.spread + message[3]);
                                break;
                            }
                        }
                        break;
                    }
                    case "exit" -> {
                        userExitConverse(message[1]);
                        break;
                    }
                }
            } catch (IOException ex) {
                break;
            }
        }

        try {
            removeClient();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void pair(int rand) {
        String user;
        user = server.listUserWaitChat.get(rand);
        if (user != null && !user.equals(getNickname())) { // nếu user ghép cặp != null && user != bản thân
            sendClient("invite" + Constant.spread + user); // bản thân gửi lời mời cho friend
            server.listUserWaitChat.remove(user);
            server.listUserWaitChat.remove(getNickname());
            for (ClientHandler tc : server.listUserActive) {
                if (tc.getNickname() != null && tc.getNickname().equals(user)) {
                    tc.sendClient("invite" + Constant.spread + getNickname()); // friend gửi lời mời cho bản thân
                    break;
                }
            }
            server.listChatParing.add(new ChatModel(getNickname(), user));
        }
    }

    private void findPair() {
        // kiểm tra danh sách user đang chờ
        if (server.listUserWaitChat.isEmpty()) {
            server.listUserWaitChat.add(getNickname());
        } else {
            if (getListUserBlock().size() == server.listUserWaitChat.size()) {
                server.listUserWaitChat.add(getNickname());
            } else {                
                int rangeRandom = (((server.listUserWaitChat.size() - 1) - 0) + 1); //((max - min) + 1)
                int index = new Random().nextInt(rangeRandom) + 0; //+ min
                String user = getRandomNickname(index);
                while (getListUserBlock().contains(user)) { // kiểm tra nếu tìm được user khác những user đã từ chối thì thoát khỏi vòng lặp
                    index = new Random().nextInt(rangeRandom) + 0; //+ min
                    user = server.listUserWaitChat.get(index);
                }
                pair(index);
            }
        }
    }

    private void userExitConverse(String user) {
        for (ClientHandler client : server.listUserActive) {
            if (client.getNickname() != null && client.getNickname().equals(user)) {
                client.sendClient("exit" + Constant.spread + getNickname());
                client.findPair();
                break;
            }
        }
    }
    
    private String getRandomNickname(int rand){
        return server.listUserWaitChat.get(rand);
    }

    private void removeClient() throws IOException {
        for (ClientHandler client : server.listUserActive) {
            if (client.getSocket().equals(getSocket())) {
                int i = server.listUserActive.indexOf(client);
                server.listUserActive.remove(i);
                server.listUserWaitChat.remove(getNickname());
                break;
            }
        }
        close();
    }

    public void sendClient(String response) {
        try {
            out.write(response);
            out.newLine();
            out.flush();
        } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void close() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
        }
    }

}
