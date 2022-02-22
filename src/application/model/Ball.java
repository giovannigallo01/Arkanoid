package application.model;

import javax.swing.ImageIcon;

import application.Settings;


public class Ball extends Sprite {

    private int directionX;
    private int directionY;

    public Ball() {
    	 directionX = 1;
         directionY = -1;
         loadImage();
         getImageDimensions();
         resetState();
    }

    private void loadImage() {
        ImageIcon imageIcon = new ImageIcon("src/application/resources/images/ball.png");
        image = imageIcon.getImage();
    }

    public void move() {
        x += directionX;
        y += directionY;

        if (x == 0)
            setDirectionX(1);
        if (x == Settings.WIDTH - imageWidth - Settings.CONSTANT) 
        	setDirectionX(-1);
        if (y == 0)
        	setDirectionY(1);
    }

    public void resetState() {
        x = Settings.INIT_BALL_X;
        y = Settings.INIT_BALL_Y;
        directionX = 0;
        directionY = 0;
    }

    public void setDirectionX(int directionX) {
		this.directionX = directionX;
	}
    
    public int getDirectionX() {
		return directionX;
	}
    
    public void setDirectionY(int directionY) {
		this.directionY = directionY;
	}
    
    public int getDirectionY() {
		return directionY;
	}
}
