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
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import testpoi.HSSFXLS;
import testpoi.XSSFXLSX;

/**
 * @author ydduong
 */
public class lookStuInformationJPanel {

//    private JPanel stuInformationPanel;
    private static JPanel topPanel; //顶端面板
    public static JPanel bottomPanel; //底端面板

    public static void createTablePanel(boolean isXLSFile) {
        //学生信息表格
        bottomPanel = new JPanel();
        String[] columnName = {"班级", "学号", "姓名", "请假次数", "缺勤次数", "总评"};
        String[][] rowData, newData;
        int rowNum, newNum = 0, k = 0;
        /*
        if(isXLSFile) rowData = HSSFXLS.getStuInformation();
        else rowData = XSSFXLSX.getStuInformation();
        */
        if(isXLSFile) {
            rowData = HSSFXLS.getStuInformation();
            rowNum = HSSFXLS.getRowNum();
        } else {
            rowData = XSSFXLSX.getStuInformation();
            rowNum = XSSFXLSX.getRowNum();
        }
        newData = new String[rowNum][6];
        for (int i = 0; i < rowNum - 1; i++) {
//            if(rowData[i][3].equals("")) System.out.println("333333");
//            if(rowData[i][4].equals("")) System.out.println("4444");
            if (!rowData[i][3].equals("") || !rowData[i][4].equals("")) {
                newNum++;
                for (int j = 0; j < 6; j++) {
                    newData[k][j] = rowData[i][j];
                }
                k++;
            }
        }


        //筛选信息
        
        JTable table = new JTable(new DefaultTableModel(newData, columnName));// 创建表格
        table.setFont(new Font("楷体", Font.PLAIN, 20));
        table.setRowHeight(32);
        DefaultTableCellRenderer rendererTable = (DefaultTableCellRenderer)table.getDefaultRenderer(Object.class);
        rendererTable.setHorizontalAlignment(JLabel.CENTER); // 设置表格文字居中
        
        JTableHeader head = table.getTableHeader(); // 创建表格标题对象
        head.setPreferredSize(new Dimension(head.getWidth(), 35));// 设置表头大小
        head.setFont(new Font("楷体", Font.PLAIN, 22));// 设置表格字体
        DefaultTableCellRenderer rendererHead = (DefaultTableCellRenderer)head.getDefaultRenderer();
        rendererHead.setHorizontalAlignment(rendererHead.CENTER); // 设置表头文字居中显示
//        head.set
        table.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
        for (int i = 0; i < 6; i++) {
            TableColumn  column = table.getColumnModel().getColumn(i); // 设置表格列宽
            if (i == 2 || i == 3 || i == 4 || i == 5) {
                column.setPreferredWidth(26);
            }
        }

        JScrollPane scrollPane = new JScrollPane(table); // 创建包含表格的滚动窗格
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
//        scrollPane.setFont(new Font("楷体", Font.PLAIN, 24));
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS)); // 定义 topPanel 的布局为 BoxLayout，BoxLayout 为垂直排列 
        bottomPanel.add(Box.createVerticalStrut(2)); // 先加入一个不可见的 Strut，从而使 topPanel 对顶部留出一定的空间
        bottomPanel.add(scrollPane); // 加入包含表格的滚动窗格
        bottomPanel.add(Box.createVerticalStrut(6)); // 再加入一个不可见的 Strut，从而使 topPanel 和 middlePanel 之间留出一定的空间
        bottomPanel.setBackground(localResources.getWHITE());
    }
    
    public static void createButtonPanel() {
        //文本展示
        JLabel returnButton = new JLabel("返回"){
            @Override //划线
            public void paint(Graphics g) {
                super.paint(g);
                Rectangle r = g.getClipBounds();
                g.drawLine(0, r.height - getFontMetrics(getFont()).getDescent() + 5,
                        getFontMetrics(getFont()).stringWidth(getText()), r.height
                        - getFontMetrics(getFont()).getDescent() + 5);
            }
        };
        returnButton.setFont(typefaceTTF.getDefinedFont(32));
        returnButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        returnButton.setOpaque(false);
        returnButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                studentJPanel SP = new studentJPanel(); // 新建一个点名面板对象，并读取表格数据，
                mainJFrame MF = new mainJFrame();
                MF.setMainJFrame(SP.returnStudentPanel());
            }
        });

        topPanel = new JPanel();
        topPanel.setBackground(Color.WHITE);
        topPanel.add(returnButton);
    }

    public Container createLookStuInformationJPanel(boolean isXLSFile) {
        createTablePanel(isXLSFile);
        createButtonPanel();

        JPanel stuInformationPanel = new JPanel();
        stuInformationPanel.setBackground(localResources.getWHITE());
        stuInformationPanel.setLayout(new GridBagLayout()); //网格袋布局

        GridBagConstraints c1 = new GridBagConstraints();
        c1.gridx = 0; //动态网格序列，左上角（0,0）
        c1.gridy = 1;
        c1.weightx = 1.0; //添加额外空间
        c1.weighty = 0;
        c1.fill = GridBagConstraints.HORIZONTAL; //在水平方向和垂直方向上同时调整组件大小,使组件完全填满其显示区域
        stuInformationPanel.add(topPanel, c1);

        GridBagConstraints c2 = new GridBagConstraints();
        c2.gridx = 0;
        c2.gridy = 0;
        c2.weightx = 1.0;
        c2.weighty = 1.0;
        c2.fill = GridBagConstraints.BOTH; //加宽组件，使它在水平方向上填满其显示区域，但是不改变高度
        stuInformationPanel.add(bottomPanel, c2);

        return stuInformationPanel;
    }
}
