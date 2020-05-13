package mainpage;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

import tools.MyTools;
import vote.Vote;
import 公告.Compile;

public class MainPage2 extends MainPage {
	
	JMenu bianji = new JMenu("编辑公告");
	JMenuItem compile = new JMenuItem("公告编辑",new ImageIcon("imag/compile.jpg"));
	
	JMenu vote = new JMenu("发起投票");
	JMenuItem faqi = new JMenuItem("发起投票",new ImageIcon("imag/发起.jpg"));
	
	common.students stu1;
	
	public MainPage2(common.students s) {
		
		super(s);
		
		stu1 = s;
		
		//管理员的特殊功能
		bianji.setFont(MyTools.f1);
		vote.setFont(MyTools.f1);
		jmBar.add(bianji);
		jmBar.add(vote);
		
		compile.setFont(MyTools.f2);
		bianji.add(compile);
		faqi.setFont(MyTools.f2);
		vote.add(faqi);
		
		//编辑公告
		comlistener c1 = new comlistener();
		compile.addActionListener(c1);
		
		Listenervote v1 = new Listenervote();
		faqi.addActionListener(v1);
		
	}
	
	
	
	
	public static void main(String[] args) {
		MainPage2 m2 = new MainPage2(null);
	}
	
	//编辑公告的监听器
	class comlistener implements ActionListener{
		
		public void actionPerformed(ActionEvent e) {
			new Compile(stu1.getName());
			
		}
	}
	
	//发起投票的监听器
	class Listenervote implements ActionListener{

		
		public void actionPerformed(ActionEvent arg0) {
			
			new Vote(stu1.getName());
			
		}
		
	}

}
