package application.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import application.Settings;
import application.controller.GameEndListener;
import application.controller.GameWinListener;
import application.sound.SoundHandler;

public class Game {

    private Ball ball;
    private Paddle paddle;
    private Brick bricks[];
    private int numOfBricks;
    private int life = 3;
    private int currentScore = 0;
    private int contBricks = 0;
    private boolean inGame = true;
    private boolean started = false;
    private List<Level> levels = new ArrayList<>();
    private int currentLevel = 1;
    private SoundHandler soundHandler = SoundHandler.getInstance();
    private List<GameEndListener> gameEndListeners =  new ArrayList<>();
    private List<GameWinListener> gameWinListeners = new ArrayList<>();
    private int lastBrick = -1;

    public Game() {
        levels.add(new Level() {
        	@Override
        	public int getLevelNumber() {
        		return 1;
        	}
        	
            @Override
            public int getPaddleSpeed() {
                return 2;
            }

            @Override
            public int getNumOfBricks() {
                return 30;
            }

            @Override
            public void generateMap(Brick[] bricks) {
                int k = 0;
                for(int i=0; i<5; i++) {
                    for(int j=0; j<6; j++) {
                    	if(i==0 || j==0 || i==4 || j==5)
                    		bricks[k] = new Brick(j * 57 + 30, i * 20 + 50,
                    				List.of("pinkBrick", "darkPinkBrick"));
                    	else
                    		bricks[k] = new Brick(j * 57 + 30, i * 20 + 50,
                    				List.of("darkPinkBrick"));
                        k++;
                    }
                }
            }
        });
        levels.add(new Level() {
        	@Override
        	public int getLevelNumber() {
        		return 2;
        	}
        	
            @Override
            public int getPaddleSpeed() {
                return 4;
            }

            @Override
            public int getNumOfBricks() {
                return 48;
            }

            @Override
            public void generateMap(Brick[] bricks) {
                int k = 0;
                for(int i=0; i<8; i++) {
                    for(int j=0; j<6; j++) {
                    	if(i==0 || j==0 || i==7 || j==5) 
                    		bricks[k] = new Brick(j * 57 + 30, i * 20 + 50,
                    				List.of("blueBrick", "darkBlueBrick"));
                    	else
                    		bricks[k] = new Brick(j * 57 + 30, i * 20 + 50,
                    				List.of("darkBlueBrick"));
                        k++;
                    }
                }
            }
        });
        gameInit(levels.get(currentLevel));
    }

    public void addGameEndListener(GameEndListener toAdd) {
        gameEndListeners.add(toAdd);
    }
    
    public void addGameWinListener(GameWinListener toAdd) {
        gameWinListeners.add(toAdd);
    }

    private void gameEnd() {
        for(GameEndListener listener : gameEndListeners)
            listener.gameEnd();
    }
    
    public void gameWin() {
    	for(GameWinListener listener : gameWinListeners)
    		listener.gameWin();
    }

    public int getNumOfBricks() {
        return numOfBricks;
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }

    public Brick[] getBricks() {
        return bricks;
    }
    
    public int getLife() {
		return life;
	}
    
    public void setLife(int life) {
		this.life = life;
	}
    
    public int getCurrentScore() {
		return currentScore;
	}
    
    public void setCurrentScore(int score) {
		currentScore = score;
	}

    public boolean isInGame() {
        return inGame;
    }

    public void setInGame(boolean inGame) {
        this.inGame = inGame;
    }
    
    public void setStarted(boolean started) {
		this.started = started;
	}
    
    public boolean isStarted() {
    	return started;
    }
    
    public boolean hasNext() {
    	if(currentLevel+1 >= levels.size())
    		return false;
    	return true;
    }
    
    public void nextLevel() {
    	currentLevel++;
    }
    
    public Level getCurrentLevel() {
    	if(currentLevel >= levels.size())
    		return null;
		return levels.get(currentLevel);
	}

    public void gameInit(Level level) {
        ball = new Ball();
        paddle = new Paddle();
        paddle.setSpeed(level.getPaddleSpeed());
        numOfBricks = level.getNumOfBricks();
        bricks = new Brick[numOfBricks];
        level.generateMap(bricks);
        contBricks = 0;
        inGame = true;
    }

    public void moveBall() {
        ball.move();
    }

    public void stopPaddle() {
        paddle.stopMove();
    }

    public void setPaddleRight() {
        paddle.moveRight();
    }

    public void setPaddleLeft() {
        paddle.moveLeft();
    }

    public void movePaddle() {
        paddle.move();
    }

    public void checkCollision() {
        if(ball.getRect().getMaxY() > Settings.BOTTOM_EDGE) {
        	life--;
        	paddle.stopMove();
        	setStarted(false);
        	ball.resetState();
        	paddle.resetState();
        	if(life <= 0) {
        		setInGame(false);
        		soundHandler.stop("game");
        		gameEnd();
        	}
        }
        
        if(contBricks == numOfBricks) { 
        	setInGame(false);
        	soundHandler.stop("game");
        	gameWin();
        	started = false;
        }

        // collisione con il paddle
        if((ball.getRect()).intersects(paddle.getRect())) {
            int posPaddle = (int) paddle.getRect().getMinX();
            int posBall = (int) ball.getRect().getMinX();
       
            int first = posPaddle + 20;
            int second = posPaddle + 26;

            if(posBall < first) {
                ball.setDirectionX(-1);
                ball.setDirectionY(-1);
            }

            if(posBall >= first && posBall < second) {
            	 ball.setDirectionX(0);
                 ball.setDirectionY(-1);
            } 

            if(posBall >= second) {
                ball.setDirectionX(1);
                ball.setDirectionY(-1);
            }
            lastBrick = -1;
        }

        // collisione con il brick
        for(int i=0; i<numOfBricks; i++) {
            if((ball.getRect()).intersects(bricks[i].getRect())) {
                int ballLeft = (int) ball.getRect().getMinX();
                int ballHeight = (int) ball.getRect().getHeight();
                int ballWidth = (int) ball.getRect().getWidth();
                int ballTop = (int) ball.getRect().getMinY();

                Point pointRight = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);

                if(!bricks[i].isDestroyed()) {
                    if(bricks[i].getRect().contains(pointRight))
                        ball.setDirectionX(-1);
                    else if(bricks[i].getRect().contains(pointLeft))
                        ball.setDirectionX(1);

                    if(bricks[i].getRect().contains(pointTop))
                        ball.setDirectionY(1);
                    else if(bricks[i].getRect().contains(pointBottom))
                        ball.setDirectionY(-1);
                    
                    if(i != lastBrick) {
	                    bricks[i].addHit();
	                    soundHandler.start("collision");
	                    lastBrick = i;
	                    if(bricks[i].isDestroyed()) {
	                    	contBricks++;
	                        setCurrentScore(getCurrentScore() + bricks[i].getBrickScore());
	                    }
                    }
                }
            }
        }
    }
    
    public void startBall() {
    	getBall().setDirectionX(1);
    	getBall().setDirectionY(-1);
    }
    
    public void restartGame() {
    	life = 3;
        currentScore = 0;
        contBricks = 0;
        inGame = true;
        started = false;
        currentLevel = 0;
        gameInit(levels.get(currentLevel));
    }
}
