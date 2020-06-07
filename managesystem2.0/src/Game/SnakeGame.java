package Game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

/*
* 1--窗口+面板+固定
* 2--在界面上画出蛇
* 3--让蛇动起来
* 4--让蛇听键盘的方向键指挥
* 5--随机产生豆子并画在界面上
* 6--指挥蛇吃到豆子并长长身体
* 7--撞墙GAME OVER ！
* 8--咬到自己的尾巴 GAME OVER !
* 9--修复BUG
*/
public class SnakeGame extends JFrame implements KeyListener {
	private static final long serialVersionUID = 1L;
	private int fw = 800;
	private int fh = 600;
	private SP sp = null;
	private int sex = 200; // 蛇关节元素属性
	private int sey = 200;
	private int sesize = 20;
	private SE se = null;
	private Timer timer = new Timer();
	private LinkedList<SE> ses = new LinkedList<SE>();
	private String direction = "up"; // 蛇行走的方向
	private int bx; // 豆子属性
	private int by;
	private int bsize = sesize;
	private Bean bean = null;
	private Random rand = new Random();
	private boolean bean_is_eated = false;
	
	//死亡后退出窗口但不退出程序
	private boolean xianshi = true;

	public SnakeGame() {
		this.setAlwaysOnTop(true);
		this.setUndecorated(true);
		this.getContentPane().setBackground(Color.BLACK);
		this.setSize(800, 600);
		this.setLocationRelativeTo(null);
		sp = new SP();
		this.add(sp);
		
		new Thread() {
			@SuppressWarnings("deprecation")
			public void run() {
				while(true) {
					if(!xianshi) {
						SnakeGame.this.dispose();
						System.out.print(xianshi);
						sp.setVisible(false);
						this.stop();
					}
					
				    System.out.println(xianshi);
				}
			} 
	}.start();
		this.setVisible(true);
		this.addKeyListener(this);
	}

	class SP extends JPanel {
		private static final long serialVersionUID = 1L;

		public SP() {
			this.setOpaque(false);
			this.getSE(); // 调用创建蛇的方法
			this.getBean_not_on_snakebody();// 调用创建豆子的方法
			timer.schedule(new TimerTask() {
				@Override
				public void run() { // 需要执行的任务
					if ("up".equalsIgnoreCase(direction)) {
						ses.removeLast(); // 移除最后一个元素
//在原来第一个元素的基础上 新增一个元素
						ses.addFirst(new SE(ses.getFirst().getSex(),
								ses.getFirst().getSey() - ses.getFirst().getSesize(), ses.getFirst().getSesize()));
//在原来方向的基础上 尾部增长一个关节元素对象
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex(),
									ses.getLast().getSey() + ses.getLast().getSesize(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//判断是否撞墙 撞墙 GAME OVER ！
						if (ses.getFirst().getSey() < 0) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "撞墙提示", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
					if ("left".equalsIgnoreCase(direction)) {
						ses.removeLast(); // 移除最后一个元素
//在原来第一个元素的基础上 新增一个元素
						ses.addFirst(new SE(ses.getFirst().getSex() - ses.getFirst().getSesize(),
								ses.getFirst().getSey(), ses.getFirst().getSesize()));
//在原来方向的基础上 尾部增长一个关节元素对象
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex() + ses.getLast().getSesize(),
									ses.getLast().getSey(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//判断是否撞墙 撞墙 GAME OVER ！
						if (ses.getFirst().getSex() < 0) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "撞墙提示", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
					if ("down".equalsIgnoreCase(direction)) {
						ses.removeLast(); // 移除最后一个元素
//在原来第一个元素的基础上 新增一个元素
						ses.addFirst(new SE(ses.getFirst().getSex(),
								ses.getFirst().getSey() + ses.getFirst().getSesize(), ses.getFirst().getSesize()));
//在原来方向的基础上 尾部增长一个关节元素对象
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex(),
									ses.getLast().getSey() - ses.getLast().getSesize(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//判断是否撞墙 撞墙 GAME OVER ！
						if (ses.getFirst().getSey() + sesize > fh) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "撞墙提示", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
					if ("right".equalsIgnoreCase(direction)) {
						ses.removeLast(); // 移除最后一个元素
//在原来第一个元素的基础上 新增一个元素
						ses.addFirst(new SE(ses.getFirst().getSex() + ses.getFirst().getSesize(),
								ses.getFirst().getSey(), ses.getFirst().getSesize()));
//在原来方向的基础上 尾部增长一个关节元素对象
						if (bean_is_eated) {
							ses.addLast(new SE(ses.getLast().getSex() - ses.getLast().getSesize(),
									ses.getLast().getSey(), ses.getLast().getSesize()));
							bean_is_eated = false;
						}
//判断是否撞墙 撞墙 GAME OVER ！
						if (ses.getFirst().getSex() + sesize > fw) {
							JOptionPane.showMessageDialog(sp, "GAME OVER !", "撞墙提示", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
						}
					}
//蛇咬到自己身体的判断
					for (int i = 1; i < ses.size(); i++) {
						if (ses.getFirst().getSex() == ses.get(i).getSex()
								&& ses.getFirst().getSey() == ses.get(i).getSey()) {
							JOptionPane.showMessageDialog(sp, "你是猪啊，咬到自己身体啦！", "自残了，兄弟！", JOptionPane.DEFAULT_OPTION);
							xianshi = false;
							timer.cancel();
							
						}
					}
					sp.eatBean(); // 调用吃豆子的方法
					sp.repaint();
				}
			}, 0, 200);
		}

		@Override
		public void paint(Graphics g) {
			for (int i = 0; i < ses.size(); i++) {
//画出豆子
				g.setColor(Color.WHITE);
				g.fill3DRect(bean.getBx(), bean.getBy(), bean.getBsize(), bean.getBsize(), true);
//画出蛇
				if (i == 0) {
					g.setColor(Color.RED);
				} else {
					g.setColor(Color.GREEN);
				}
				g.fill3DRect(ses.get(i).getSex(), ses.get(i).getSey(), ses.get(i).getSesize(), ses.get(i).getSesize(),
						true);
			}
		}

		public void eatBean() {
//豆子被到了 //蛇的头部坐标和豆子的坐标重合
			if (ses.getFirst().getSex() == bean.getBx() && ses.getFirst().getSey() == bean.getBy()) {
				bean = new Bean();
				sp.getBean_not_on_snakebody();
				bean_is_eated = true;
			}
		}

		public void getBean_not_on_snakebody() {
			this.getBean();
// * 豆子不能出现在蛇的身体上 用 bx 、by 同时和蛇的身体每一个元素进行比较 保证没有全等
			for (int i = 0; i < ses.size(); i++) {
				if (ses.get(i).getSex() == bean.getBx() && ses.get(i).getSey() == bean.getBy()) {
					this.getBean();
				}
			}
		}

		public void getBean() {
// * 不能出现在右边的边界上 bx != fw
// * 豆子的x坐标必须能够整除（余数为0 ）蛇身体元素的尺寸 bx % sesize == 0
			while (true) {
				bx = rand.nextInt(fw);
				System.out.println(bx);
				if (bx != fw && bx % sesize == 0) {
					break;
				}
			}
// * 不能出现在下边的边界上 by != fh 
// * 豆子的y坐标必须能够整除（余数为0） 蛇身体元素的尺寸 by % sesize == 0 
			for (;;) {
				by = rand.nextInt(fh);
				if (by != fh && by % sesize == 0) {
					break;
				}
			}
			bean = new Bean(bx, by, bsize);
			System.out.println(bean);
		}

		public void getOneSEData() { // 获取一个蛇关节元素的数据
		}

		public void getSE() {// 获取一个蛇关节对象
			for (int i = 0; i < 4; i++) {
				se = new SE(sex, sey + sesize * i, sesize);
				ses.add(se);
			}
		}

		public void getSnake() { // 获取一整条蛇
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
//方向 不能改变为相反的方向 例如：left 不能 修改为 right
		if (e.getKeyCode() == KeyEvent.VK_LEFT && !"right".equalsIgnoreCase(direction)) {
			direction = "left";
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && !"up".equalsIgnoreCase(direction)) {
			direction = "down";
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && !"left".equalsIgnoreCase(direction)) {
			direction = "right";
		}
		if (e.getKeyCode() == KeyEvent.VK_UP && !"down".equalsIgnoreCase(direction)) {
			direction = "up";
		}
		if (e.getKeyCode() == 32) {
			Runtime.getRuntime().exit(0);
		}
	}

	public static void main(String[] args) {
		new SnakeGame();
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}
}

class SE implements Serializable {
	/**
	 * 蛇的关节对象
	 */
	private static final long serialVersionUID = 1L;
	private int sex;
	private int sey;
	private int sesize;

	public SE() {
		super();
// TODO Auto-generated constructor stub
	}

	public SE(int sex, int sey, int sesize) {
		super();
		this.sex = sex;
		this.sey = sey;
		this.sesize = sesize;
	}

	public int getSex() {
		return sex;
	}

	public void setSex(int sex) {
		this.sex = sex;
	}

	public int getSey() {
		return sey;
	}

	public void setSey(int sey) {
		this.sey = sey;
	}

	public int getSesize() {
		return sesize;
	}

	public void setSesize(int sesize) {
		this.sesize = sesize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + sesize;
		result = prime * result + sex;
		result = prime * result + sey;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SE other = (SE) obj;
		if (sesize != other.sesize)
			return false;
		if (sex != other.sex)
			return false;
		if (sey != other.sey)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "SE [sesize=" + sesize + ", sex=" + sex + ", sey=" + sey + "]";
	}
}

class Bean implements Serializable {
	/**
	 * 豆子对象
	 */
	private static final long serialVersionUID = 1L;
	private int bx;
	private int by;
	private int bsize;

	public Bean() {
		super();
// TODO Auto-generated constructor stub
	}

	public Bean(int bx, int by, int bsize) {
		super();
		this.bx = bx;
		this.by = by;
		this.bsize = bsize;
	}

	public int getBx() {
		return bx;
	}

	public void setBx(int bx) {
		this.bx = bx;
	}

	public int getBy() {
		return by;
	}

	public void setBy(int by) {
		this.by = by;
	}

	public int getBsize() {
		return bsize;
	}

	public void setBsize(int bsize) {
		this.bsize = bsize;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + bsize;
		result = prime * result + bx;
		result = prime * result + by;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bean other = (Bean) obj;
		if (bsize != other.bsize)
			return false;
		if (bx != other.bx)
			return false;
		if (by != other.by)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Bean [bsize=" + bsize + ", bx=" + bx + ", by=" + by + "]";
	}
}