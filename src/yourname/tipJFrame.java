/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yourname;

/**
 * 这是一个弹窗类，660MS后会自动销毁
 * @author ydduong
 */
import javax.swing.JFrame;
import javax.swing.JLabel;

public class tipJFrame extends JFrame {

	/**
	* 初始化
	*/
    public void init(int x, int y, String str) {
        JLabel tip = new JLabel(str);
        tip.setFont(typefaceTTF.getDefinedFont(24));
        tip.setOpaque(false);

        this.add(tip);
        this.setLocation(x, y);
        this.setUndecorated(true); // 除去边框
        this.pack();
        this.setVisible(true);
        
        new Close().start();
    }
	
	/**
	* 开线程
	*/
    public class Close extends Thread {
        public void run() {
            try {
                Thread.sleep(660);//线程暂停660MS
            } catch (InterruptedException e) {
            }
            dispose();
        }
    }
}
