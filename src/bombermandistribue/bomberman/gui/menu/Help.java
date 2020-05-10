package bombermandistribue.bomberman.gui.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import bombermandistribue.bomberman.gui.Frame;
import bombermandistribue.bomberman.gui.InfoDialog;

public class Help extends JMenu {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Help(Frame frame) {
		super("Help");
		
		/*
		 * How to play
		 */
		JMenuItem instructions = new JMenuItem("How to play");
		instructions.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H, ActionEvent.CTRL_MASK));
		instructions.addActionListener(new MenuActionListener(frame));
		add(instructions);	
	}
	
	class MenuActionListener implements ActionListener {
		public Frame _frame;
		public MenuActionListener(Frame frame) {
			_frame = frame;
		}
		
		  @Override
		public void actionPerformed(ActionEvent e) {
			  
			  if(e.getActionCommand().equals("How to play")) {
				  new InfoDialog(_frame, "How to Play", "Movement: UP,DOWN, RIGHT, LEFT\nPut Bombs: SPACE", JOptionPane.QUESTION_MESSAGE);
			  }
			  
		  }
	}
}
