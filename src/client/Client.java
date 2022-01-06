package client;

import common.Constant;
import event.PublicEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 *
 * @author letoan
 */

public class Client {

    static Client instance;
    private Socket socket = null;
    private BufferedReader in = null;
    private BufferedWriter out = null;   
    private String nickname = null;    

    public static Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void connect() {
        try {
            socket = new Socket("localhost", 5656);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            t.start();
        } catch (IOException e) {
            System.out.println("Server not found");
        }
    }
    Thread t = new Thread() {
        @Override
        public void run() {
            while (true) {
                try {
                    // phản hồi từ server
                    String response = in.readLine();
                    System.out.println(response);
                    if (response != null) {
                        String[] message = response.split(Constant.spread);
                        switch (message[0]) {
                            case "choose":
                                if (message[1].equals("true")) {
                                    PublicEvent.getInstance().getEventChoose().choose("true");
                                    setNickname(message[2]);
                                    PublicEvent.getInstance().getEventWaiting().setNickname(message[2]);
                                } else {
                                    PublicEvent.getInstance().getEventChoose().choose("false");
                                }
                                break;
                            case "invite":
                                PublicEvent.getInstance().getEventWaiting().waitingPair(message[1]);
                                break;
                            case "joinchat":
                                PublicEvent.getInstance().getEventMain().chat(message[1]);
                                break;
                            case "sendmessage":
                                PublicEvent.getInstance().getEventChat().reciveMessage(message[1], message[2]);
                                break;
                            case "cancelchat":
                                PublicEvent.getInstance().getEventWaiting().cancelConverse(message[1]);
                                break;
                            case "exit":
                                PublicEvent.getInstance().getEventMain().conserveWaiting();
                                PublicEvent.getInstance().getEventWaiting().exitConverse();
                                break;
                        }
                    }
                } catch (IOException ex) {
                    break;
                }
            }
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
            }
        }
    };

    public void sendServer(String request) throws IOException {
        System.out.println("Phía client gửi đi: " + request);
        out.write(request);
        out.newLine();
        out.flush();
    }

    public void closeSocket() {
        try {
            in.close();
            out.close();
            socket.close();
        } catch (IOException e) {
        }
    }

    public boolean serverIsWorking(){
        return !(socket == null || in == null || out == null );
    }
}
