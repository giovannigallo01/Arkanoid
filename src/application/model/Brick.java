package application.model;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Brick extends Sprite {

    private boolean destroyed;
    private int countHit = 0;
    private List<Image> imagesBrick = new ArrayList<Image>();

    public Brick(int x, int y, List<String> colorsBrick) {
    	this.x = x;
        this.y = y;
        destroyed = false;
        loadImage(colorsBrick);
        getImageDimensions();
    }
    
    private void loadImage(List<String> colorsBrick) {
    	ImageIcon imageIcon = null;
    	for(String color : colorsBrick) {
	    	switch(color) {
			case "blueBrick":
				imageIcon = new ImageIcon("src/application/resources/images/blueBrick.png");
				break;
			case "darkBlueBrick":
				imageIcon = new ImageIcon("src/application/resources/images/darkBlueBrick.png");
				break;
			case "pinkBrick":
				imageIcon = new ImageIcon("src/application/resources/images/pinkBrick.png");
				break;
			case "darkPinkBrick":
				imageIcon = new ImageIcon("src/application/resources/images/darkPinkBrick.png");
				break;
	    	default:
	    		throw new IllegalArgumentException();
	    	}
	    	imagesBrick.add(imageIcon.getImage());
    	}
        image = imagesBrick.get(0);    
    }

    public boolean isDestroyed() {
        return destroyed;
    }

    public void setDestroyed(boolean val) {
        destroyed = val;
    }
    
    public void addHit() {
    	countHit++;
    	if(countHit < imagesBrick.size()) {
    		image = imagesBrick.get(countHit);
    	}
    	else
    		destroyed = true;
    }
    
    public int getBrickScore() {
    	return 10 * imagesBrick.size();
    }
}
