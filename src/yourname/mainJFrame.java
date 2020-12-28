/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yourname;

import static com.sun.java.accessibility.util.AWTEventMonitor.addWindowListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import testpoi.HSSFXLS;
import testpoi.XSSFXLSX;
//import static yourname.startJPanel.bottomPanel;

/**
 * 这是整个程序的框架frame，在frame之上有一个面板管理器panelContainer
 * panelContainer来管理所有的面板（简言之用panelContainer刷新页面的）
 * 总共有两个函数：
 *     1.createMainJFrame()//创建程序框架并添加开始页面
 *     2.setMainJFrame()//改变程序框架内的面板，达到刷新页面的效果
 * @author ydduong
 */
public class mainJFrame {
    public static JPanel panelContainer; //类的全局变量，frame的面板管理器panelContainer，用来添加页面的

    /**
     * 1.createMainJFrame()// 创建程序框架并添加开始页面
     */
    public void createMainJFrame() {
        mainJFrame MF = new mainJFrame(); // 创建mainJFrame类的一个对象，用于创建整个程序
        startJPanel MP = new startJPanel(); // 创建开始面板

        panelContainer = new JPanel(); // 初始化面板管理器
        MF.setMainJFrame(MP.createStartJPanel()); //将开始界面添加到面板管理器！！！
        
        JSeparator splitTopLine = new javax.swing.JSeparator(); 
        // 窗口底部的一条线
        JPanel bottomLinePanel = new JPanel();
        bottomLinePanel.setLayout(new BoxLayout(bottomLinePanel, BoxLayout.X_AXIS));
        bottomLinePanel.setBackground(localResources.getWHITE());
        bottomLinePanel.add(Box.createHorizontalStrut(32));
        JSeparator splitBottomLine = new javax.swing.JSeparator(); 
        splitBottomLine.setPreferredSize(new Dimension(bottomLinePanel.getWidth(), 1));
        bottomLinePanel.add(splitBottomLine);
        bottomLinePanel.add(Box.createHorizontalStrut(32));
        
        // 窗口底部文字
        JLabel copyright = new JLabel("Copyright@ 2019-2020 GUILIN University Of Technology");
        copyright.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        copyright.setPreferredSize(new Dimension(300,36));
        copyright.setFont(new Font(Font.SERIF, Font.PLAIN, 12));
        copyright.setForeground(localResources.getDEEP_GRAY());
        copyright.setBackground(localResources.getWHITE());
        copyright.setOpaque(true);

        JFrame frame = new JFrame("不点名不上课"); // 创建框架
        // 以下是设置框架的属性，并将panelContainer添加到frame中
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setFont(typefaceTTF.getDefinedFont(14));
        frame.setLayout(new GridBagLayout()); // 网格袋布局
        
        GridBagConstraints c0 = new GridBagConstraints();
        c0.gridx = 0;
        c0.gridy = 0;
        c0.weightx = 1.0;
        c0.weighty = 0;
        c0.fill = GridBagConstraints.HORIZONTAL; // 加宽组件，使它在水平方向上填满其显示区域，但是不改变高度
        splitTopLine.setPreferredSize(new Dimension(frame.getWidth(), 1));
        frame.add(splitTopLine, c0);
        
        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0; // 动态网格序列，左上角（0,0）
        c1.gridy = 1;
        c1.weightx = 1.0; // 添加额外空间
        c1.weighty = 1.0;
        c1.fill = GridBagConstraints.BOTH; // 在水平方向和垂直方向上同时调整组件大小,使组件完全填满其显示区域
        frame.add(panelContainer, c1); // 将顶部面板添加到开始面板
        
        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 2;
        c2.weightx = 1.0;
        c2.weighty = 0;
        c2.fill = GridBagConstraints.HORIZONTAL; // 加宽组件，使它在水平方向上填满其显示区域，但是不改变高度
        frame.add(bottomLinePanel, c2);
        
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 3;
        c3.weightx = 1.0;
        c3.weighty = 0;
        c3.fill = GridBagConstraints.HORIZONTAL;
        frame.add(copyright, c3);

        frame.setLocation(192 * 2, (int) (108 * 1.3));
        frame.setSize(new Dimension((int) (192 * 5.6), (int) (108 * 6.2)));
        frame.setIconImage(Toolkit.getDefaultToolkit().createImage(localResources.getIconCornerMarkerPath()));
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.out.println("调用窗体关闭功能");
                int result = JOptionPane.showConfirmDialog(null, "是否保存点名记录？", "温馨提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) { //保存数据
                    try {
                        if (studentJPanel.getIsXLSFile()) {
                            HSSFXLS.writeXLS(studentJPanel.getLuckyDog());
                        } else {
                            XSSFXLSX.writeXLSX(studentJPanel.getLuckyDog());
                        }
                    } catch (IOException ex) {
                        Logger.getLogger(studentJPanel.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (java.lang.NullPointerException ee) {
                        System.out.println("文件不存在，写入失败");
                    }
                }
                frame.dispose();
            }

            public void windowClosed(WindowEvent e) {
                System.out.println("触发windowClosed事件");
            }
        });
    }

    /**
     * 2.setMainJFrame()// 改变程序框架内的面板，达到刷新页面的效果
     * @param con
     */
    public void setMainJFrame(Container con) { // 传入一个新的面板
        panelContainer.removeAll(); //清除以前的内容
        panelContainer.setLayout(new GridBagLayout());
        GridBagConstraints c1 = new GridBagConstraints(); // 重新定义布局
        c1.gridx = 0; // 动态网格序列，左上角（0,0）
        c1.gridy = 0;
        c1.weightx = 1.0; // 添加额外空间
        c1.weighty = 1.0;
        c1.fill = GridBagConstraints.BOTH;
        panelContainer.add(con, c1); // 将新界面添加到面板管理器中，已达到刷新界面的效果
        panelContainer.revalidate();
        panelContainer.repaint(); // 重新绘制页面
    }
}
