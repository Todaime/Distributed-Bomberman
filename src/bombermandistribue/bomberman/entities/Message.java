package bombermandistribue.bomberman.entities;

import java.awt.Color;

import bombermandistribue.bomberman.graphics.Screen;

public class Message extends Entity {

	protected String _message;
	protected double _duration;
	protected Color _color;
	protected int _size;
	
	public Message(String message, int x, int y, double duration, Color color, int size) {
		_x =x;
		_y = y;
		_message = message;
		_duration = duration * 60.0; //seconds
		_color = color;
		_size = size;
	}

	public double getDuration() {
		return _duration;
	}

	public void setDuration(double _duration) {
		this._duration = _duration;
	}

	public String getMessage() {
		return _message;
	}

	public Color getColor() {
		return _color;
	}

	public int getSize() {
		return _size;
	}

	public void setPos(int xNew, int yNew){
		_x = xNew;
		_y = yNew;
	}

	@Override
	public void update() {
	}

	@Override
	public void render(Screen screen) {
	}

	@Override
	public boolean collide(Entity e) {
		return true;
	}
	
	
}
