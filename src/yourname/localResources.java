/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yourname;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author ydduong
 */
public class localResources {
    private static Color WHITE = new Color(16777215); // 白色#ffffff
    private static Color BLACK = new Color(0); // 黑色#000000
    private static Color PALE_GRAY = new Color(15198183); // 浅灰色#e7e7e7
    private static Color GRAY = new Color(12303291); // 灰色#bbbbbb
    private static Color DEEP_GRAY = new Color(10790052); // 深灰色#a4a4a4
    private static Color BACKGROUND_GRAY = new Color(15527923); // 背景灰#eceff3
    private static ImageIcon iconStartPanel = new ImageIcon("src\\localResources\\startPanel.jpg");
    private static ImageIcon iconStuPanel = new ImageIcon("src\\localResources\\studentPanel.jpg");
    private static String fontPath = "src\\localResources\\禹卫书法行书简体（新优化版）.ttf"; // 字体文件直接放在工程目录下
    private static String iconCornerMarkerPath = "src\\localResources\\markerIcon.jpg";

    public static String getFontPath() {
        return fontPath;
    }

    public static ImageIcon getIconStuPanel() {
        return iconStuPanel;
    }

    public static String getIconCornerMarkerPath() {
        return iconCornerMarkerPath;
    }


    public static Color getWHITE() {
        return WHITE;
    }

    public static ImageIcon getIconStartPanel() {
        return iconStartPanel;
    }

    public static Color getBLACK() {
        return BLACK;
    }

    public static Color getPALE_GRAY() {
        return PALE_GRAY;
    }

    public static Color getGRAY() {
        return GRAY;
    }

    public static Color getDEEP_GRAY() {
        return DEEP_GRAY;
    }

    public static Color getBACKGROUND_GRAY() {
        return BACKGROUND_GRAY;
    }
    
}
