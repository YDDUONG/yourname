/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package yourname;

/**
 * 这是引用本地字体的类
 * @author ydduong
 */
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class typefaceTTF {
	
	/**
	* 函数getDefinedFont(float ttfSize)
	* 返回自定义的字体，如果没有找到字体文件，默认返回楷体
	*/
    public static Font getDefinedFont(float ttfSize) {

        Font definedFont = null;
        InputStream inputStream = null;
        BufferedInputStream bufferedInputStream = null;
        try {
            inputStream = new FileInputStream(new File(localResources.getFontPath()));
            bufferedInputStream = new BufferedInputStream(inputStream);
            definedFont = Font.createFont(Font.TRUETYPE_FONT, inputStream);
            definedFont = definedFont.deriveFont(ttfSize); // 设置字体大小 
        } catch (FontFormatException | IOException e) {
        } finally {
            try {
                if (null != bufferedInputStream) {
                    bufferedInputStream.close();
                }
                if (null != inputStream) {
                    inputStream.close();
                }
            } catch (IOException e) {
            }
        }
        return definedFont;
    }
}
