package Server;
import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;  
import java.util.ArrayList;  
  
public class server {  
    
 
      
    public ServerSocket server;
    public int port;
    private TablePanel tablePanel;

      
    public static ArrayList<ServerThread> list = new ArrayList<ServerThread>();
    public static ArrayList<String> username = new ArrayList<String>();

    public server(int port, TablePanel tablePanel) {
        this.port=port;
        this.tablePanel=tablePanel;
    }
      
    public void startServer() {  
          
        try {  
            server = new ServerSocket(port);

            System.out.println("Server has been setablished");  
            while(true){  
                Socket socket =server.accept();
                InputStream ins =socket.getInputStream();
                OutputStream ous = socket.getOutputStream();
                DataInputStream dis=new DataInputStream(ins);
                DataOutputStream dos= new DataOutputStream(ous);
                String name = dis.readUTF();
                boolean flag = true;
                for (int i = 0; i < username.size(); i++) {
                    if (name.equalsIgnoreCase(username.get(i))) {
                        flag = false;
                    }
                }
                if (flag) {
                    dos.writeUTF("Username valid.");
                    dos.flush();
                    int n = JOptionPane.showConfirmDialog(null,
                            name + " wants to join in.", "Access Permission",
                            JOptionPane.YES_NO_OPTION);
                    if (n == 0) {
                        System.out.println("A client has connected."+socket.getInetAddress());
                        dos.writeUTF("Connection established.");
                        dos.flush();
                        ServerThread st = new ServerThread(socket, tablePanel, name);
                        st.start();
                        System.out.println("client thread started");
                        list.add(st);
                        username.add(name);
                    }
                    else {
                        dos.writeUTF("Connection failed.");
                        dos.flush();
                    }
                }
                else {
                    dos.writeUTF("Username invalid.");
                    dos.flush();
                }


            }  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
  
    }

}  
