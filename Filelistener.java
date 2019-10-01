import javax.swing.*;
import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;



public class Filelistener extends JFrame implements ActionListener {
    // 定义一个文本框
    JTextArea jTextArea = null;
    // 定义一个菜单栏
    JMenuBar jMenuBar = null;
    // 定义一个菜单
    JMenu jMenu = null;
    // 定义一个子选项 功能打开
    JMenuItem jMenuItem0 = null;

    JMenuItem jMenuItem1 = null;
    //定义一个保存

    JMenuItem jMenuItem2 = null;

    JMenuItem jMenuItem3 = null;

    JMenuItem jMenuItem4 = null;

    //定义一个文件选择框

    JFileChooser jFileChooser = null;

    //定义一个文件输入流

    FileReader fileReader = null;
    //定义一个文件输出流

    FileWriter fileWriter = null;

    //定义一个缓冲字符输入区

    BufferedReader bufferedReader = null;

    private static String name;
    JFileChooser filechooser;


    public Filelistener(){
        filechooser =new JFileChooser();
        jTextArea = new JTextArea();
        // New 一个菜单栏
        jMenuBar = new JMenuBar();
        //new 一个菜单
        jMenu = new JMenu("File");
        jMenuItem0 = new JMenuItem("new");
        jMenuItem0.addActionListener(this);
        jMenuItem0.setActionCommand("new");
        jMenuItem1 = new JMenuItem("open");
        jMenuItem1.addActionListener(this);
        jMenuItem1.setActionCommand("open");
        jMenuItem2 = new JMenuItem("save");
        jMenuItem2.addActionListener(this);
        jMenuItem2.setActionCommand("save");
        jMenuItem3 = new JMenuItem("save as");
        jMenuItem3.addActionListener(this);
        jMenuItem3.setActionCommand("save as");
        jMenuItem4 = new JMenuItem("close");
        jMenuItem4.addActionListener(this);
        jMenuItem4.setActionCommand("close");
        //new 一个文件选择框
        jFileChooser = new JFileChooser();
        //设置文本框的背景颜色
        jTextArea.setBackground(Color.WHITE);
        // 归位，找到自己对应的位置
        // 将菜单栏添加到我的窗口， this 代表当前对象
        this.setJMenuBar(jMenuBar);
        // 将菜单添加到菜单栏里面
        jMenuBar.add(jMenu);
        // 将打开和保存添加到菜单文件
        jMenu.add(jMenuItem0);
        jMenu.add(jMenuItem1);
        jMenu.add(jMenuItem2);
        jMenu.add(jMenuItem3);
        jMenu.add(jMenuItem4);
        //将文本框添加到我的窗口中
        this.add(jTextArea);
        // 设置标题
        this.setTitle("whiteboard");
        // 设置窗口的大小
        this.setSize(800,600);
        // 关闭窗口的时候，关闭进程
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // 显示窗口
        this.setVisible(true);

    }
    public static void main(String[] args){
        Filelistener filetest = new Filelistener();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //如果点击的是打开
        if(e.getActionCommand().equals("open")){
            //实例化对象 文件选择框
            jFileChooser = new JFileChooser();
            //定义标题
            jFileChooser.setDialogTitle("open window");
            //设置默认路径
            jFileChooser.showOpenDialog(null);
            // 弹出窗口
            jFileChooser.setVisible(true);
            //用 address保存用户编辑文件的绝对路径
            String address = jFileChooser.getSelectedFile().getAbsolutePath();

            try {
                //实例化文件输入流 从你选择的这个文件中读取数据
                fileReader = new FileReader(address);
                //实例化缓冲字符输入流
                bufferedReader = new BufferedReader(fileReader);
                //定义一个str判断输入字符是否已为空
                String str = "";
                //定义一个strall接收文件的全部信息
                String strAll = "";
                // 去缓冲区里边拿我的数据并且保存到我的stralll中
                while ((str = bufferedReader.readLine()) !=null){
                    strAll += str + "\r\n";
                }
                jTextArea.setText(strAll);
            } catch(IOException e1){
                e1.printStackTrace();
            } finally{
                //确定一定以及肯定回执行它里面的代码
                try {
                    bufferedReader.close();
                    fileReader.close();
                } catch (IOException e1){
                    e1.printStackTrace();
                }

            }
        }
        if(e.getActionCommand().equals("close")){
            System.exit(0);
        }
        if (e.getActionCommand().equals("new")){
            createFile();
        }
        if (e.getActionCommand().equals("save")){
            filechooser.showSaveDialog(this);
            File file=filechooser.getSelectedFile();
            saveFile(file);
        }
        if (e.getActionCommand().equals("save as")){
            filechooser.showSaveDialog(this);
            File file=filechooser.getSelectedFile();
            saveFile(file);
        }


    }
    private void createFile() {


        // String name = null;

        File file = null;
        // 选择保存或取消
        if (filechooser.showSaveDialog(Filelistener.this) == JFileChooser.APPROVE_OPTION) {
            file = filechooser.getSelectedFile();// 获取选中的文件
        } else {
            return;
        }
        name = filechooser.getName(file);// 获取输入的文件名
        if (file.exists()) { // 若选择已有文件----询问是否要覆盖
            int i = JOptionPane.showConfirmDialog(null, "The file is existed, will you cover it", "Yes", JOptionPane.YES_NO_OPTION);
            if (i == JOptionPane.YES_OPTION) {
                saveFile(file);
            } else {
                filechooser.showSaveDialog(Filelistener.this);// 重新选择
            }
        } else {//文件不存在，则直接保存
            saveFile(file);
        }

        jTextArea.setText("");

    }

    private void saveFile(File file) {
        BufferedWriter bw = null;
        try {
            bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
            bw.write(jTextArea.getText());//写入文件
            bw.flush();
        } catch (FileNotFoundException e1) {
            JOptionPane.showMessageDialog(Filelistener.this, "There is a mistake when saving" + e1.getMessage());
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            try {
                if (bw != null)
                    bw.close();
            } catch (IOException e1) {
            }
        }
    }
}
