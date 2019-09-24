import java.io.DataInputStream;
import java.io.DataOutputStream;  
import java.io.IOException;  
import java.io.InputStream;  
import java.io.OutputStream;  
import java.net.Socket;  
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import javax.swing.JOptionPane;

import static Server.Myserver.list;

public class ServerThread extends Thread{  
      
    public Socket socket;
    public DataInputStream dis ;  
    public DataOutputStream dos;
    public String name;
    private TablePanel tablePanel;
   
//    public InputStream ins2;
//    public OutputStream ous2;
      
    public ServerThread(Socket socket, TablePanel tablePanel, String name) {
        this.socket=socket;
        this.tablePanel=tablePanel;
        this.name = name;
//        this.socket2 = socket2;
    }  
  
    public void run() {

        Vector<String> vs=null;
        try {  
            //????????????  
           InputStream ins =socket.getInputStream();  
           OutputStream ous = socket.getOutputStream();
           String ip =socket.getRemoteSocketAddress().toString();
           Date date = new Date();
           SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
           String currentTime =sdf.format(date);

           vs = new Vector<String>();
           vs.add(name);
           vs.add(ip);
           vs.add(currentTime);

           tablePanel.data.add(vs);
           tablePanel.table.updateUI();
            
            //??????????????????  
           dis=new DataInputStream(ins);
           dos=new DataOutputStream(ous);
           while(true){  
                int value=dis.read();  
//             System.out.println(value);
                //????????????  
                for (int i = 0; i < list.size(); i++) {
                    ServerThread st = list.get(i);
                    if(st!=this){  
                        st.dos.write(value);  
                        st.dos.flush();
                       
                    }
                }                
           }
            
        }
        catch (SocketException e) {
            try {
                Myserver.list.remove(this);
                Myserver.username.remove(name);
                this.tablePanel.data.remove(vs);
                this.tablePanel.table.updateUI();
                System.out.println(name + " has left.");
                
                for (int i = 0; i < Myserver.list.size(); i++) {
                    ServerThread st = Myserver.list.get(i);
                    OutputStream new_ous = st.socket.getOutputStream();
                    DataOutputStream new_dos= new DataOutputStream(new_ous);
                    new_dos.writeByte(15);
                    new_dos.writeInt(1);
                    new_dos.writeInt(1);
                    new_dos.writeInt(1);
                    new_dos.writeInt(1);
                    new_dos.writeInt(1);
                    new_dos.writeUTF("");
                    new_dos.writeUTF(" has left.");
                    new_dos.writeUTF(name);
                    new_dos.flush();
                }
                JOptionPane.showMessageDialog(null, name + " has left :(");

//                this.socket.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
            //          e.printStackTrace();
        catch (Exception e2) {
            e2.printStackTrace();
        }
   
     }  
}