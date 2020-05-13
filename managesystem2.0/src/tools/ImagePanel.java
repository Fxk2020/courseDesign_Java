package tools;

//动态加载一个图片做背景的的JPanel
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JPanel;

public class ImagePanel extends JPanel {

	Image picture;

	// 构造函数去指定该Panel大小
	public ImagePanel(Image picture) {
		this.picture = picture;
		int w = Toolkit.getDefaultToolkit().getScreenSize().width;
		int h = Toolkit.getDefaultToolkit().getScreenSize().height;
		this.setSize(w, h);
	}

	// 画出背景
	public void paintComponent(Graphics g) {
		// 清屏
		super.paintComponents(g);
		g.drawImage(picture, 0, 0, this.getWidth(), this.getHeight(), this);

	}
}
