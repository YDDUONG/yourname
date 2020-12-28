/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yourname;

import javax.swing.UIManager;

public class yourname {
	
    /**
     * 程序的入口函数main 
     * 主要负责创建整个程序的窗体
     */
    public static void main(String[] args) {
        // 获取当前系统的组件风格
        try {
			// 这里还可设置其他风格，可自行百度UIManager
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch(Exception e) {
        	System.out.println(e);
        }

        // 创建窗体
        mainJFrame frame = new mainJFrame();
        frame.createMainJFrame(); // 创建框架并添加开始页面
    }
}
