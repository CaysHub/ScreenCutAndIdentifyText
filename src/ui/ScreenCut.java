package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * 主窗口，截图和转换文字操作
 */
public class ScreenCut extends JFrame implements MouseListener, MouseMotionListener, ActionListener,KeyListener{
    private JButton screenCutBtn,closeBtn;
    private JScrollPane jsp;
    private JTextArea ocrText;
    private MyScreen myScreen;
    private Point point;
    private boolean isPressed=false;
    private ImagePanel imagePanel;
    private ImageUtil imageUtil;
    private OCRUtil ocrUtil;
    private BufferedImage image;
    public ScreenCut() throws HeadlessException {
        initFrame();
        screenCutBtn=new JButton("截图");
        screenCutBtn.setBounds(0,0,400,50);
        screenCutBtn.addActionListener(this);
        closeBtn=new JButton("转换文字");
        closeBtn.setBounds(400,0,400,50);
        closeBtn.addActionListener(this);
        this.add(screenCutBtn);
        this.add(closeBtn);
        //添加图片显示
        imagePanel=new ImagePanel(null);
        imagePanel.setBounds(0,50,400,300);
        this.add(imagePanel);
        //文本显示
        ocrText = new JTextArea("",200,500);
        //ocrText.setBounds(600,50,200,500);
        ocrText.setLineWrap(true);
        jsp = new JScrollPane(ocrText);
        jsp.setBounds(400,50,400,500);
        this.add(jsp);
        this.setVisible(true);
    }
    private void initFrame(){
        point=new Point(0,0,0,0);
        imageUtil = new ImageUtil();
        ocrUtil = new OCRUtil();
        this.setTitle("屏幕截图");
        this.setLayout(null);
        //设置窗口大小
        this.setSize(new Dimension(800,600));
        int windowWidth = this.getWidth(); //获得窗口宽
        int windowHeight = this.getHeight();//获得窗口高
        Toolkit kit = Toolkit.getDefaultToolkit(); //定义工具包
        Dimension screenSize = kit.getScreenSize(); //获取屏幕的尺寸
        int screenWidth = screenSize.width; //获取屏幕的宽
        int screenHeight = screenSize.height; //获取屏幕的高
        //设置窗口居中
        this.setLocation(screenWidth/2-windowWidth/2, screenHeight/2-windowHeight/2);//设置窗口居中显示
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //设置窗口大小不可变
        this.setResizable(false);
    }
    /**
     * MouseListener事件
     * 相应事件和处理方法
     * 鼠标事件         处理方法
     * MOUSE_CLICKED    MouseClicked (MouseEvent)    鼠标点击（单或双）
     * MOUSE_PRESSED    MousePressed (MouseEvent)    鼠标按下
     * MOUSE_RELEASED   MouseReleased(MouseEvent)    鼠标松开
     * MOUSE_ENTERED    MouseEntered (MouseEvent)    鼠标进入（某组件区域）
     * MOUSE_EXITED     MouseExited  (MouseEvent)    鼠标离开（某组件区域）
     * 鼠标事件MouseEvent常用方法
     * int getClickCount()      得到点击次数1 OR 2；
     * int getX(), int getY()   得到鼠标的（象素）位置。
     * @param e
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.println("Clicked X:"+e.getX()+"\tY:"+e.getY());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("Pressed X:"+e.getX()+"\tY:"+e.getY());
        point.setStartX(e.getX());
        point.setStartY(e.getY());
        isPressed=true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("Released X:"+e.getX()+"\tY:"+e.getY());
        point.setEndX(e.getX());
        point.setEndY(e.getY());
        myScreen.setPoint(point);
        myScreen.repaint();
        isPressed=false;
        if (myScreen!=null){
            myScreen.setPoint(new Point(0,0,0,0));
            myScreen.repaint();
            myScreen.setVisible(false);
            image=imageUtil.getScreenShot(point);
            imagePanel.setImage(image);
            imagePanel.updateUI();
        }
        this.setVisible(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("Entered X:"+e.getX()+"\tY:"+e.getY());
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("Exited X:"+e.getX()+"\tY:"+e.getY());
    }

    /**
     * 鼠标监听器MouseMotionListener
     * 对于鼠标的移动和拖放，另外用鼠标运动监听器MouseMotionListener。
     * 因为许多程序不需要监听鼠标运动，把两者分开可简化程序。
     * 相应事件和处理方法
     * 鼠标事件          处理方法
     * MOUSE_MOVED      MouseMoved  (MouseEvent)    鼠标在移动
     * MOUSE_DRAGGED    MouseDragged(MouseEvent)    鼠标被拖动
     * @param e
     */
    @Override
    public void mouseDragged(MouseEvent e) {
        //System.out.println("mouseDragged X:"+e.getX()+"\tY:"+e.getY());
        if (isPressed) {
            point.setEndX(e.getX());
            point.setEndY(e.getY());
            myScreen.setPoint(point);
            myScreen.repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        //System.out.println("mouseMoved X:"+e.getX()+"\tY:"+e.getY());
    }
    /**
     * 按钮监听事件
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("截图")){
            if (myScreen==null) {
                myScreen = new MyScreen(this);
                myScreen.addMouseListener(this);
                myScreen.addMouseMotionListener(this);
            }else {
                myScreen.setVisible(true);
            }
            this.setVisible(false);
        }else if (e.getActionCommand().equals("转换文字")){
            if (image == null){
                ocrText.setText("没有图片！");
            }else {
                //图片文件：此图片是需要被识别的图片路径
                String basePath = System.getProperty("user.dir");
                String path = basePath + "\\src\\ui\\image\\";
                String fileName = "test3.png";
                try {
                    File file = imageUtil.saveCaptureScreen(path,fileName,image);
                    String regText = ocrUtil.recognizeText(file,"png");
                    ocrText.setText(regText);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    /**
     * 键盘监听事件
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("Key:"+e.getKeyChar());
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE){
            myScreen.setPoint(new Point(0,0,0,0));
            myScreen.repaint();
            myScreen.setVisible(false);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}