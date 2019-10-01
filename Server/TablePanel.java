package Server;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.SocketException;
import java.util.Vector;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class TablePanel extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    public JTable table;
    public Vector<Vector<String>> data;


    public TablePanel() {
     
        this.setPreferredSize(new Dimension(450,0));
        this.setBackground(Color.white);
        this.setLayout(new BorderLayout());

        Vector<String> colNames = new Vector<String>();
        colNames.add("User");
        colNames.add("IP");
        colNames.add("Time");
        data =new Vector<Vector<String>>();
        Vector<String> vs=new Vector<String>();

        
        DefaultTableModel tm =new DefaultTableModel(data, colNames);
        table = new JTable(tm);

        
        JScrollPane jsp =new JScrollPane(table);
        this.add(jsp);


        final JButton delButton = new JButton("Delete");
        delButton.addActionListener(new ActionListener(){//add action listener
            public void actionPerformed(ActionEvent e){
                int selectedRow = table.getSelectedRow();//get the index of selected line
                if(selectedRow!=-1)  
                {
                    tm.removeRow(selectedRow);  //delete line
                    try {
                        OutputStream ous = server.list.get(selectedRow).socket.getOutputStream();
                        DataOutputStream dos= new DataOutputStream(ous);
                        dos.writeByte(100);
                        dos.writeInt(1);
                        dos.writeInt(1);
                        dos.writeInt(1);
                        dos.writeInt(1);
                        dos.writeInt(1);
                        dos.writeUTF("h");
                        dos.writeUTF("hh");
                        dos.writeUTF("hhh");
                        dos.flush();
                        server.list.get(selectedRow).socket.close();
                        server.username.remove(selectedRow);
                    }

                    catch (SocketException e2) {
                        System.out.println("*****************");
                    }
                    catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    server.list.remove(selectedRow);
                }
            }
        });
        this.add(delButton, BorderLayout.AFTER_LAST_LINE);
    }

}



