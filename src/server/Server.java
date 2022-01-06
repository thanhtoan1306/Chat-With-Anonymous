package server;

import common.ChatModel;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author letoan
 */
public class Server {

    private Socket socket = null;
    private ServerSocket server = null;
    
    static Vector<ClientHandler> listUserActive = new Vector<>();
    public static ArrayList<String> listUserWaitChat = new ArrayList<>();
    public static ArrayList<ChatModel> listChatParing = new ArrayList<>();
    
    public Server() {

    }

    public Server(int port) {

        try {
            //init
            server = new ServerSocket(port);
            while (true) {
                socket = null;

                InetAddress inetAddress = InetAddress.getLocalHost();
                System.out.println("Server opened at: " + inetAddress.getHostAddress());
                System.out.println("Server is waiting to accept Client...");                
                
                socket = server.accept();
                
                ClientHandler clientHandler = new ClientHandler(socket);
                Thread t = new Thread(clientHandler);
                listUserActive.add(clientHandler);
                t.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void main(String[] args) {
        Server server = new Server(5656);
    }

}
