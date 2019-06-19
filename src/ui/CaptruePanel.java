package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * @ClassName MyPanel
 * @Description TODO
 * 截图面板，点击截图按钮时显示此面板，当作背景
 * @Author Cays
 * @Date 2019/6/18 8:33
 * @Version 1.0
 **/
public class CaptruePanel extends JPanel{
    private int screenWeight;
    private int screenHeight;
    private Point point;

    public CaptruePanel(int screenWeight, int screenHeight) {
        this.screenWeight = screenWeight;
        this.screenHeight = screenHeight;
        point=new Point(0,0,0,0);
    }

    @Override
    public void paint(Graphics g) {
        // 必须先调用父类的paint方法
        super.paint(g);
        //paintPeople(g);
        paintShadle(g);
    }

    private void paintPeople(Graphics g){
        // 用画笔Graphics，在画板JPanel上画一个小人
        g.drawOval(100, 70, 30, 30);// 头部（画圆形）
        g.drawRect(105, 100, 20, 30);// 身体（画矩形）
        g.drawLine(105, 100, 75, 120);// 左臂（画直线）
        g.drawLine(125, 100, 150, 120);// 右臂（画直线）
        g.drawLine(105, 130, 75, 150);// 左腿（画直线）
        g.drawLine(125, 130, 150, 150);// 右腿（画直线）
    }
    private void paintReact(Graphics g){
        g.setColor(new Color(0,0,0,50));
        g.fillRect(point.getStartX(),point.getStartY(),point.getEndX(),point.getEndY());
    }
    private void paintShadle(Graphics g) {
        g.setColor(new Color(0,255,0,70));
        g.fillRect(0,0,screenWeight,point.getStartY());
        g.fillRect(0,point.getStartY(),point.getStartX(),point.getEndY()-point.getStartY());
        g.fillRect(point.getEndX(),point.getStartY(),screenWeight-point.getEndX(),point.getEndY()-point.getStartY());
        g.fillRect(0,point.getEndY(),screenWeight,screenHeight-point.getEndY());
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
