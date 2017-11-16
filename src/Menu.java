import javax.swing.*;

import java.awt.event.*;

public class Menu extends JFrame implements ActionListener {
	
	public void initComponents() {
		 parseButton("Easy");
		 parseButton("Medium");
		 parseButton("Hard");
	}
	
	public void parseButton(String str) {
		JButton button = new JButton(str);
		button.setActionCommand(str);
		button.addActionListener(this);
	}
	
	public Menu() {
		super("Menu");
		initComponents();
	}
	
	public static void main(String[] args) {
		new Tron().run();
	}

	@Override
	public void actionPerformed(ActionEvent ae) {
		String str = ae.getActionCommand();
		if(str.equals("")) {
			
		}
	}
}
