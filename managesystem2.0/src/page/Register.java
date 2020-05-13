package page;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import common.User;
import connection.ClientUser;

public class Register extends JFrame implements ActionListener {
	JButton register;
	JTextField studentnumbertf, nametf;
	JPasswordField password1tf, password2tf;
	User user;

	public Register(User u) {
		user = u;
		// JLabel registerLabel = new JLabel("’À∫≈◊¢≤·");
		JLabel name = new JLabel("–’√˚:");
		JLabel studentnumber = new JLabel("—ß∫≈:");
		JLabel password1 = new JLabel("√‹¬Î:");
		JLabel password2 = new JLabel("»∑∂®√‹¬Î:");
		nametf = new JTextField(16);
		studentnumbertf = new JTextField();
		password1tf = new JPasswordField();
		password2tf = new JPasswordField();

		JRadioButton user = new JRadioButton("”√ªß");
		JRadioButton Administrator = new JRadioButton("π‹¿Ì‘±");
		register = new JButton("◊¢≤·");
		Font font = new Font("ÀŒÃÂ", Font.PLAIN, 23);
		ButtonGroup group = new ButtonGroup();
		group.add(Administrator);
		group.add(user);
		name.setFont(font);
		studentnumber.setFont(font);
		studentnumber.setFont(font);
		password1.setFont(font);
		password2.setFont(font);

		register.setFont(new Font("ÀŒÃÂ", Font.PLAIN, 25));
		register.setBounds(170, -10, 120, 123);
		name.setBounds(90, 70, 70, 90);
		studentnumber.setBounds(90, 120, 70, 90);
		password1.setBounds(90, 170, 70, 90);
		password2.setBounds(80, 220, 90, 100);
		password2.setFont(new Font("ÀŒÃÂ", Font.PLAIN, 19));
		nametf.setBounds(170, 95, 200, 40);
		studentnumbertf.setBounds(170, 145, 200, 40);
		password1tf.setBounds(170, 195, 200, 40);
		password2tf.setBounds(170, 245, 200, 40);

		user.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 17));
		Administrator.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 17));
		user.setBounds(150, 300, 80, 100);
		Administrator.setBounds(250, 300, 80, 100);
		register.setBounds(145, 400, 170, 50);
		register.setFont(new Font("Œ¢»Ì—≈∫⁄", Font.PLAIN, 23));
		// register.setVisible(true);
		// register.setHorizontalAlignment(SwingConstants.CENTER);
		this.setLayout(null);
		this.add(register);
		this.add(name);
		this.add(nametf);
		this.add(studentnumber);
		this.add(studentnumbertf);
		this.add(password1);
		this.add(password1tf);
		this.add(password2);
		this.add(password2tf);

		this.add(user);
		this.add(Administrator);
		this.add(register);

		// this.setBounds(0, 0, 500, 700);
		this.setVisible(true);
		this.setSize(500, 700);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		register.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == register) {
			ClientUser clientUser = new ClientUser();
			user.setName(new String(nametf.getText()));
			user.setNumber(new String(studentnumbertf.getText()));
			user.setPassword(new String(password1tf.getPassword()));
			String[] s = clientUser.checkUser(user);
			if (s[2].equals("true")) {

				this.dispose();
			} else {
				JOptionPane.showMessageDialog(this, "”√ªß√˚√‹¬Î¥ÌŒÛ");
			}
		}
	}
}

//		if (e.getSource() == register) {
//
//			String name = nametf.getText();
//			int studentNumber = Integer.parseInt(studentnumbertf.getText());
//			String password = new String(password1tf.getPassword());
//			try {
//				dao.addString2(name, studentNumber, password);
//			} catch (Exception ex) {
//				ex.printStackTrace();
//			}
//
//			register.setText("succeed!");
//			// ss.dispose();
//		}
//	}
