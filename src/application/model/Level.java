package application.model;

public interface Level {

	public int getLevelNumber();
    public int getPaddleSpeed();
    public int getNumOfBricks();
    public void generateMap(Brick[] bricks);
}
