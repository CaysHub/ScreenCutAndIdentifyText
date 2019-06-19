package ui;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * @ClassName ImageUtil
 * @Description TODO
 * 保存截图生成的BufferedImage图片
 * @Author Cays
 * @Date 2019/6/18 16:52
 * @Version 1.0
 **/
public class ImageUtil {
    /**
     * 截图
     * @param filePath 截图保存文件夹路径
     * @param fileName 截图文件名称
     * @throws Exception
     */
    public void captureScreen(String filePath, String fileName,Point point) throws Exception {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Rectangle screenRectangle = new Rectangle(screenSize);
        Robot robot = new Robot();
        BufferedImage image = robot.createScreenCapture(screenRectangle);
        // 截图保存的路径
        File screenFile = new File(filePath + fileName);
        // 如果文件夹路径不存在，则创建
        if (!screenFile.getParentFile().exists()) {
            screenFile.getParentFile().mkdirs();
        }
        if (!screenFile.exists()){
            screenFile.createNewFile();
        }
        // 指定屏幕区域，参数为截图左上角坐标(100,100)+右下角坐标(500,500)
        BufferedImage subimage = image.getSubimage(point.getStartX(), point.getStartY(),
                point.getEndX()-point.getStartX(), point.getEndY()-point.getStartY());
        ImageIO.write(subimage, "png", screenFile);
    }
    public BufferedImage getScreenShot(Point point){
        int x=point.getStartX();
        int y=point.getStartY();
        int width=point.getEndX()-point.getStartX();
        int height=point.getEndY()-point.getStartY();
        BufferedImage image=null;
        try {
            Robot robot = new Robot();
            image = robot.createScreenCapture(new Rectangle(x, y, width, height));
        } catch (AWTException e) {
            e.printStackTrace();
        }
        return image;
    }
    public File saveCaptureScreen(String filePath, String fileName,BufferedImage image) throws IOException {
        // 截图保存的路径
        File screenFile = new File(filePath + fileName);
        // 如果文件夹路径不存在，则创建
        if (!screenFile.getParentFile().exists()) {
            screenFile.getParentFile().mkdirs();
        }
        if (screenFile.exists()){
            screenFile.delete();
        }
        screenFile.createNewFile();
        String suffix = "png";
        if (fileName.contains(".")) {
            suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        ImageIO.write(image, suffix, screenFile);
        return screenFile;
    }
}
