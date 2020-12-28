/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yourname;
import testpoi.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 学生信息显示的界面
 * @author ydduong
 */
public class studentJPanel {

    private static JPanel studentPanel = null;
    private static JPanel stuInformationPanel = new JPanel();
    private static JPanel buttonPanel;
    private static String stuName, stuSno, stuClas, stuNotArriveNum, stuLeave, stuTotal;
    private static String[][] value;
    private static int rows, cols, rowNum = 1, count = 0, stuStatus = 1;
    private static boolean[] isAt;
    private static int[] luckyDog;
    private static boolean isXLSFile;

    /**
     * 0.构造函数 传入一个文件地址，从而构建一个表格对象， 获取表格的行数rows，并将表格的全部内容存到二维数组value中
     *
     * @param filePath
     * @throws java.io.IOException
     * @throws org.apache.poi.openxml4j.exceptions.InvalidFormatException
     */
    public studentJPanel() {
        super();
    }

    public studentJPanel(String filePath) throws IOException, InvalidFormatException {
        super();
        //获取表格内容和基本信息
        if (filePath.charAt(filePath.length() - 1) == 'x') { //判断是.xlsx文件
            XSSFXLSX xssfXlsx = new XSSFXLSX(filePath);
            rows = xssfXlsx.getRowNum();
            cols = xssfXlsx.getColNum();
            value = xssfXlsx.getAllValue();
            isXLSFile = false;
        } else {
            HSSFXLS hssfXls = new HSSFXLS(filePath);
            rows = hssfXls.getRowNum();
            cols = hssfXls.getColNum();
            value = hssfXls.getAllValue();
            isXLSFile = true;
        }
        stuClas = value[rowNum][0];
        stuSno = value[rowNum][1];
        stuName = value[rowNum][2];
        stuLeave = value[rowNum][cols - 3];
        stuNotArriveNum = value[rowNum][cols - 2];
        if(stuNotArriveNum.equals("")) stuTotal = "100";
        else stuTotal = value[rowNum][cols - 1];

        isAt = new boolean[rows];
        luckyDog = new int[rows];
        for (int i = 0; i < rows; i++) {
            isAt[i] = false;
            luckyDog[i] = 0;
        }
    }

    public static int[] getLuckyDog() {
        return luckyDog;
    }

    public static boolean getIsXLSFile() {
        return isXLSFile;
    }

    
    /**
     * 0.5随机抽查一名学生，并更新TopPanel()面板
     */
    public static void renewStuInformationPanelTrue() {
        if (count == rows - 1) {
            JOptionPane.showMessageDialog(null, "全部学生已经点过了", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        //isAt[rowNum] == false; 表示学生没有被点
        Random random = new Random();
        while (true) { //去重
            rowNum = random.nextInt(rows - 1) + 1; //产生一个随机数
            if (isAt[rowNum] == false) { //判断学生有没有被点， 为false则没被点了，跳出去
                break;
            }
        }

        stuClas = value[rowNum][0];
        stuSno = value[rowNum][1];
        stuName = value[rowNum][2];
        stuLeave = value[rowNum][cols - 3];
        stuNotArriveNum = value[rowNum][cols - 2];
        if(stuNotArriveNum.equals("")) stuTotal = "100";
        else stuTotal = value[rowNum][cols - 1];

        stuInformationPanel.removeAll();
        createStuInformationPanel(); // 重新添加组件
        stuInformationPanel.setOpaque(false);
        stuInformationPanel.validate();
        stuInformationPanel.repaint();
    }
//    public static void renewStuInformationPanelFalse() {
//        Random random = new Random();
//        rowNum = random.nextInt(rows - 1) + 1; //产生一个随机数
//        String tempNum = String.valueOf(random.nextInt(900) + 100);
//        stuClas = value[rowNum][0];
//        stuSno = value[rowNum][1];
//        stuName = value[rowNum][2];
//        stuLeave = tempNum;
//        stuNotArriveNum = tempNum;
//        stuTotal = tempNum;
//
//        stuInformationPanel.removeAll();
//        createStuInformationPanel(); // 重新添加组件
//        stuInformationPanel.setOpaque(false);
//        stuInformationPanel.validate();
//        stuInformationPanel.repaint();
//    }
    /**
     * 1.创建学生信息面板；显示学生的相关信息
     */
    public static void createStuInformationPanel() {
        //学生信息展示
        if (stuNotArriveNum.equals("")) {
            stuNotArriveNum = "0";
        }
        if (stuLeave.equals("")) {
            stuLeave = "0";
        }
        JLabel information = new JLabel("<html>" + "姓&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp名：" + stuName + "<br>"
                + "班&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp级：" + stuClas + "<br>"
                + "学&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp号：" + stuSno + "<br>"
                + "缺勤次数：" + stuNotArriveNum + "<br>"
                + "请假次数：" + stuLeave + "<br>"
                + "总&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp评：" + stuTotal + "<br>"
                + "</html>");
        information.setFont(new Font("楷体", Font.PLAIN, 35));
        information.setHorizontalAlignment(javax.swing.SwingConstants.CENTER); // 使information在stuInformationPanel中水平居中

        stuInformationPanel.setAlignmentX(0.5f); // 使stuInformationPanel在studentPanel中水平居中
        stuInformationPanel.setLayout(new BoxLayout(stuInformationPanel, BoxLayout.Y_AXIS));
        stuInformationPanel.add(Box.createVerticalStrut(120)); //和顶端之间流出距离
        stuInformationPanel.add(information);
        stuInformationPanel.add(Box.createVerticalStrut(10)); //和顶端之间流出距离
        stuInformationPanel.setOpaque(false);
    }

    /**
     * 2.按钮面板
     */
    public static void createButtonPanel() {        
        //单选按钮组
        JRadioButton arrivedRadioButton = new JRadioButton("已到");
        arrivedRadioButton.setSelected(true); //单选按钮默认选择“已到”
        stuStatus = 1;
        arrivedRadioButton.setFont(typefaceTTF.getDefinedFont(28)); // 设置字体
        arrivedRadioButton.setFocusPainted(false); // 除去虚线框
        arrivedRadioButton.setOpaque(false); // 设置透明
        arrivedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stuStatus = 1; //表示“已到”
            }
        });

        JRadioButton leaveRadioButton = new JRadioButton("请假");
        leaveRadioButton.setFont(typefaceTTF.getDefinedFont(28));
        leaveRadioButton.setFocusPainted(false);
        leaveRadioButton.setOpaque(false);
        leaveRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stuStatus = 2;
            }
        });

        JRadioButton notArrivedRadioButton = new JRadioButton("缺勤   ");
        notArrivedRadioButton.setFont(typefaceTTF.getDefinedFont(28));
        notArrivedRadioButton.setFocusPainted(false);
        notArrivedRadioButton.setOpaque(false);
        notArrivedRadioButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stuStatus = 3;
            }
        });

        ButtonGroup radioButtonGroup = new ButtonGroup(); //设置成安选按钮组
        radioButtonGroup.add(arrivedRadioButton);
        radioButtonGroup.add(leaveRadioButton);
        radioButtonGroup.add(notArrivedRadioButton);

        //记录标签
        JLabel recordLabel = new JLabel("记录") {
            @Override //画下划线
            public void paint(Graphics g) {
                super.paint(g);
                Rectangle r = g.getClipBounds();
                g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent() + 5,
                        getFontMetrics(getFont()).stringWidth(getText()), r.height
                        - getFontMetrics(getFont()).getDescent() + 5);
            }
        };
        recordLabel.setFont(typefaceTTF.getDefinedFont(32)); // 设置字体
        recordLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // 设置鼠标样式（小手样式）
        recordLabel.setOpaque(false); // 设置背景透明
        recordLabel.addMouseListener(new MouseAdapter() { // 鼠标点击监听事件
            @Override
            public void mouseClicked(MouseEvent e) {
//                System.out.println(stuStatus);
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
                        tipJFrame t = new tipJFrame();
                        t.init(point.x - 200, point.y - 80, "记录成功!"); // 弹窗事件
                        luckyDog[rowNum] = stuStatus;
                    }
                }, 10);
            }
        });
        
        //开线程，刷新页面
        class renewStuInformationPanelThread extends Thread { //封装成函数，在按钮监听事件了调用
            @SuppressWarnings("deprecation")
            public void run() {
                for (int i = 0; i < 32; i++) {
                    try {
                        renewStuInformationPanelTrue(); // 更新学生信息页面
                        Thread.sleep(35);// 线程暂停35MS
                    } catch (InterruptedException e) {
                    }
                }
                isAt[rowNum] = true; // 标记该学生，不会在下次点名的时候再次点到他
                count++; // 计数器，全部学生被点之后，就不会再点名了，防止死循环
//                System.out.println(rowNum);
            }
        }
        
        //点名标签
        JLabel selectStuNameLabel = new JLabel("点名") {
            @Override //画下划线
            public void paint(Graphics g) {
                super.paint(g);
                Rectangle r = g.getClipBounds();
                g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent() + 5,
                        getFontMetrics(getFont()).stringWidth(getText()), r.height
                        - getFontMetrics(getFont()).getDescent() + 5);
            }
        };
        selectStuNameLabel.setFont(typefaceTTF.getDefinedFont(32));
        selectStuNameLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        selectStuNameLabel.setOpaque(false);
        selectStuNameLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                    new renewStuInformationPanelThread().start();
            }
        });

        //查询标签
        JLabel queryLabel = new JLabel("查询") {
            @Override 
            public void paint(Graphics g) {
                super.paint(g);
                Rectangle r = g.getClipBounds();
                g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent() + 5,
                        getFontMetrics(getFont()).stringWidth(getText()), r.height
                        - getFontMetrics(getFont()).getDescent() + 5);
            }
        };
        queryLabel.setFont(typefaceTTF.getDefinedFont(32));
        queryLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        queryLabel.setOpaque(false);
        queryLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try { // 查询前先回写数据
                    if (isXLSFile) { // 判断文件类型，调用相应的类
                        HSSFXLS.writeXLS(luckyDog);
                    } else {
                        XSSFXLSX.writeXLSX(luckyDog);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(studentJPanel.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                Timer timer = new Timer();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Point point = java.awt.MouseInfo.getPointerInfo().getLocation();
                        tipJFrame t = new tipJFrame();
                        t.init(point.x - 120, point.y - 50, "数据已更新！"); // 弹窗
                        
                        lookStuInformationJPanel stuInformationJPanel = new lookStuInformationJPanel();
                        mainJFrame MF = new mainJFrame();
                        MF.setMainJFrame(stuInformationJPanel.createLookStuInformationJPanel(isXLSFile)); // 跳转到查询学生信息页面
                    }
                }, 0);
            }
        });

        //将单选按钮和记录按钮放在同一个板面中
        /*1.网格布局*/
        GridBagLayout gridBag = new GridBagLayout(); // 网格布局
        GridBagConstraints constraints = null; // 布局属性
        buttonPanel = new JPanel(gridBag);

        //依次添加按钮
        constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        gridBag.addLayoutComponent(arrivedRadioButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 0;
        gridBag.addLayoutComponent(leaveRadioButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 0;
        gridBag.addLayoutComponent(notArrivedRadioButton, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 3;
        constraints.gridy = 0;
        gridBag.addLayoutComponent(recordLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 1;
        constraints.gridy = 1;
        gridBag.addLayoutComponent(selectStuNameLabel, constraints);

        constraints = new GridBagConstraints();
        constraints.gridx = 2;
        constraints.gridy = 1;
        gridBag.addLayoutComponent(queryLabel, constraints);

        buttonPanel.add(arrivedRadioButton);
        buttonPanel.add(leaveRadioButton);
        buttonPanel.add(notArrivedRadioButton);
        buttonPanel.add(recordLabel);
        buttonPanel.add(selectStuNameLabel);
        buttonPanel.add(queryLabel);
        buttonPanel.setOpaque(false);
    }

    /**
     * 创建学生面板（第一次调用，从startJPanel进入的）
     * @return
     */
    public Container createStudentPanel() {
        //调用上面两个创建函数，创建学生信息面板和按钮面板
        createStuInformationPanel();
        createButtonPanel();

        studentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { // 添加背景图片
                ImageIcon icon = localResources.getIconStuPanel();
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, studentPanel.getWidth(),
                        studentPanel.getHeight(), icon.getImageObserver());
            }
        };
        //1.盒子布局
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        studentPanel.add(stuInformationPanel);
        studentPanel.add(buttonPanel);
        studentPanel.setOpaque(true);
        return studentPanel;
    }
    
    /**
     * 返回学生面板（从lookStuInformationJPanel返回到学生面板）
     * @return 
     */
    public Container returnStudentPanel() {
        stuInformationPanel.removeAll();
        createStuInformationPanel(); // 重新添加组件
        stuInformationPanel.validate();
        stuInformationPanel.repaint();

        buttonPanel.removeAll();
        createButtonPanel(); // 重新添加组件
        buttonPanel.validate();
        buttonPanel.repaint();

        studentPanel = new JPanel() { // 重新设置背景
            @Override
            protected void paintComponent(Graphics g) {
                ImageIcon icon = localResources.getIconStuPanel();
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, studentPanel.getWidth(),
                        studentPanel.getHeight(), icon.getImageObserver());
            }
        };
        
        //1.盒子布局
        studentPanel.setLayout(new BoxLayout(studentPanel, BoxLayout.Y_AXIS));
        studentPanel.add(stuInformationPanel);
        studentPanel.add(buttonPanel);
        studentPanel.setOpaque(true);
        return studentPanel;
    }
}
