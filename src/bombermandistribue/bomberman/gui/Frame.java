package bombermandistribue.bomberman.gui;

import java.awt.BorderLayout;
import java.awt.event.*;

import javax.swing.*;

import bombermandistribue.bomberman.Game;
import bombermandistribue.bomberman.gui.menu.Menu;

import server.ServerGame;

public class Frame extends JFrame {
	
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	public GamePanel _gamepane;
	private JPanel _containerpane;
	private InfoPanel _infopanel;
	
	private Game _game;
	public int _playerNum;
	public ServerGame _serverGame;
	public Frame(ServerGame serverGame, int playerNum, int level) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			@Override
			public void windowClosing(WindowEvent e){
				JOptionPane.showMessageDialog(null,"You left the game...");
				_game.getBoard().endRunningState();
			}
		});
		setJMenuBar(new Menu(this));
		_serverGame = serverGame;
		_playerNum = playerNum;;
		_containerpane = new JPanel(new BorderLayout());
		_gamepane = new GamePanel(this, level);
		_infopanel = new InfoPanel(_gamepane.getGame());
		
		_containerpane.add(_infopanel, BorderLayout.PAGE_START);
		_containerpane.add(_gamepane, BorderLayout.PAGE_END);
		
		_game = _gamepane.getGame();
		
		add(_containerpane);
		
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);	
		
		_game.start();
		setVisible(false);
        dispose();
	}
	
	/*
	|--------------------------------------------------------------------------
	| Game Related
	|--------------------------------------------------------------------------
	 */
	public void newGame() {
		_game.getBoard().newGame();
	}
	
	public void changeLevel(int i) {
		_game.getBoard().changeLevel(i);
	}
	
	public void pauseGame() {
		_game.getBoard().gamePause();
	}
	
	public void resumeGame() {
		_game.getBoard().gameResume();
	}
	
	public boolean isRunning() {
		return _game.isRunning();
	}
	
	public void setTime(int time) {
		_infopanel.setTime(time);
	}
	
	public void setLives(int lives) {
		_infopanel.setLives(lives);
	}	
}
