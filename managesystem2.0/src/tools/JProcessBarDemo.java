package tools;
import java.awt.Color;
import java.awt.FlowLayout;
 
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
 
 
 
public class JProcessBarDemo extends JFrame{
 
	private static final long serialVersionUID = 1L;
	private JProgressBar processBar;
	
	public JProcessBarDemo(){
		try {
			UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
			// 改变风格
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setTitle("文件传输进度");		//设置窗体标题
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 设置窗体退出的操作
		
		setBounds(600, 600, 350, 100);// 设置窗体的位置和大小
		
		setResizable(false);
		
		JPanel contentPane = new JPanel();   // 创建内容面板
		
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));// 设置内容面板边框
		
		setContentPane(contentPane);// 应用(使用)内容面板
		
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER,5,5));// 设置为流式布局
		
		processBar = new JProgressBar();// 创建进度条
		
		processBar.setStringPainted(true);// 设置进度条上的字符串显示，false则不能显示
		
		processBar.setBackground(Color.GREEN);
	
		
		
		contentPane.add(processBar);// 向面板添加进度控件
	}
	
	public void setValues(int i) {
//		// 创建线程显示进度
//		new Thread(){
//
//			public void run(){
//				while(i!=100) {
//					try {
//						Thread.sleep(100);  //   让当前线程休眠0.1ms
//					} catch (InterruptedException e) {
//						// TODO: handle exception
//						e.printStackTrace();
//					}
//					processBar.setValue(i);	// 设置进度条数值
//				}
//				processBar.setString("文件传输完成");// 设置提示信息
//			}
//		}.start(); //  启动进度条线程
		
		processBar.setValue(i);
		
	}
	
	public static void main(String[] args){
		JProcessBarDemo JPBD = new JProcessBarDemo();
		new Thread() {
			public void run() {
				for(int i=1;i<=100;i++) {
					JPBD.setValues(i);
					JPBD.setVisible(true);
					if(i==100) {
						JPBD.dispose();
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		}.start();
		
		
//		JPBD.setVisible(true);
	}
}
