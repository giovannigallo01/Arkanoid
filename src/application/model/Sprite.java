package application.model;

import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {

    protected int x;
    protected int y;
    protected int imageWidth;
    protected int imageHeight;
    protected Image image;
    
    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public Image getImage() {
        return image;
    }

    public Rectangle getRect() {
        return new Rectangle(x, y, image.getWidth(null), image.getHeight(null));
    }

    public void getImageDimensions() {
        imageWidth = image.getWidth(null);
        imageHeight = image.getHeight(null);
    }
}
