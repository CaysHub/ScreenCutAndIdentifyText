package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * @ClassName ImagePanel
 * @Description TODO
 * ScreenCut的图片面板，显示截图的图片用
 * @Author Cays
 * @Date 2019/6/18 17:12
 * @Version 1.0
 **/
public class ImagePanel extends JPanel {
    private BufferedImage image;

    public ImagePanel(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void paint(Graphics g) {
        //需要执行，否则重绘会保留原图片
        super.paint(g);
        if (image!=null) {
            int imageWidth=image.getWidth(),imageHeight=image.getHeight();
            double rate=(getWidth()/(imageWidth*1.0))>(getHeight()/(imageHeight*1.0))
                    ?(getHeight()/(imageHeight*1.0)):(getWidth()/(imageWidth*1.0));
            int w =imageWidth, h=imageHeight;
            if (imageWidth>getWidth()||imageHeight>getHeight()) {
                w = (int) (imageWidth * rate);
                h = (int) (imageHeight * rate);
            }
            g.drawImage(image, 0, 0, w,h,null);
        }
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }
}
