/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yourname;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

/**
 * 该类只有一个函数： 
 * 1.createStartJPanel() // 创建开始面板
 * @author ydduong
 */
public class startJPanel {

    private static JPanel startPanel; // 开始面板

    /**
     * 1.createStartJPanel()// 创建开始面板
     * @return
     */
    public Container createStartJPanel() {
        // 开始界面的图片展示
        startPanel = new JPanel() {
            protected void paintComponent(Graphics g) { 
                Image img = localResources.getIconStartPanel().getImage();
                g.drawImage(img, 0, 0, startPanel.getWidth(),
                        startPanel.getHeight(), localResources.getIconStartPanel().getImageObserver());
            }
        };

        // 开始面板的鼠标相应事件
        startPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("kaishi");
                JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(JFileChooser.FILES_ONLY);
                jfc.setMultiSelectionEnabled(true);
                jfc.setFileFilter(new FileNameExtensionFilter("war", "xlsx", "xls"));
                jfc.showDialog(new JLabel(), "选择");
                try {
                    File file = jfc.getSelectedFile(); // 用户选择的文件
                    System.out.println("文件:" + file.getAbsolutePath());
                    // 检查文件是否合乎标准....（这个功能暂时没有）

                    String filePath = file.getAbsolutePath(); // 获取文件绝对地址
                    studentJPanel SSP = new studentJPanel(filePath); // 新建一个点名面板对象，并读取表格数据，
                    JOptionPane.showMessageDialog(null, "上传成功！", "提示", JOptionPane.INFORMATION_MESSAGE);

                    //文件上传成功后，跳转到学生信息页面
                    mainJFrame MF = new mainJFrame();
                    MF.setMainJFrame(SSP.createStudentPanel());

                } catch (NullPointerException es) {
                    JOptionPane.showMessageDialog(null, "上传失败！", "提示", JOptionPane.ERROR_MESSAGE);
                } catch (IOException | InvalidFormatException ex) {
                    Logger.getLogger(startJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        return startPanel; // 返回开始面板
    }
}
