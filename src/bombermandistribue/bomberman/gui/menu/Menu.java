package bombermandistribue.bomberman.gui.menu;

import javax.swing.JMenuBar;

import bombermandistribue.bomberman.gui.Frame;

public class Menu extends JMenuBar {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public Menu(Frame frame) {
		add( new Help(frame) );
	}
	
}
