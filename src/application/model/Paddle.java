package application.model;

import javax.swing.ImageIcon;

import application.Settings;

public class Paddle extends Sprite {

    private int directionX;
    private int speed = 5;

    public Paddle() {
        loadImage();
        getImageDimensions();
        resetState();
    }
    
    private void loadImage() {
        ImageIcon imageIcon = new ImageIcon("src/application/resources/images/paddle.png");
        image = imageIcon.getImage();        
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void move() {
        x += directionX;
        
        if (x <= 0)
            x = 0;
        if (x >= Settings.WIDTH - imageWidth - Settings.CONSTANT)
            x = Settings.WIDTH - imageWidth - Settings.CONSTANT;
    }

    public void moveRight() {
        directionX = speed;
    }

    public void moveLeft() {
        directionX = -speed;
    }

    public void stopMove() {
        directionX = 0;
    }

    public void resetState() {

        x = Settings.INIT_PADDLE_X;
        y = Settings.INIT_PADDLE_Y;
    }
}
