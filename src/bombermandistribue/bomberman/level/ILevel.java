package bombermandistribue.bomberman.level;


import bombermandistribue.bomberman.exceptions.LoadLevelException;

public interface ILevel {

	public void loadLevel(String path) throws LoadLevelException;
}
