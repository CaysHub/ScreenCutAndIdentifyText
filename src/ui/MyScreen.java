package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * @ClassName MyScreen
 * @Description TODO
 * 点击截图按钮相框
 * @Author Cays
 * @Date 2019/6/17 23:46
 * @Version 1.0
 **/
public class MyScreen extends JFrame{
    private CaptruePanel myPanel;
    public MyScreen(KeyListener keyListener){
        initJFrame(keyListener);
        //设置JFrame无边框无标题
        this.setUndecorated(true);
        //设置组件透明
        this.setBackground(new Color(0,0,0,0));
        this.setVisible(true);
    }
    private void initJFrame(KeyListener keyListener){
        this.setTitle("test");
        this.setLayout(null);
        //设置窗口大小
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        this.setSize(new Dimension(screenWidth,screenHeight));
        this.setLocation(0,0);//设置窗口位置
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);
        myPanel=new CaptruePanel(screenWidth,screenHeight);
        myPanel.setBounds(0,0,screenWidth,screenHeight);
        //设置面板透明
        myPanel.setBackground(null);
        myPanel.setOpaque(false);
        this.addKeyListener(keyListener);
        this.add(myPanel);
    }
    public void repaint(){
        myPanel.repaint();
    }
    public void setPoint(Point point){
        myPanel.setPoint(point);
    }

    public CaptruePanel getMyPanel() {
        return myPanel;
    }

    public void setMyPanel(CaptruePanel myPanel) {
        this.myPanel = myPanel;
    }
}
