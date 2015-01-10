package thelegendofselda;

import environment.LocationValidatorIntf;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author marieleger
 */
public class Snake {

    private ArrayList<Point> body = new ArrayList<>();
    private Direction direction = Direction.RIGHT;
    private GridDrawData drawData;
    private LocationValidatorIntf locationValidator;
    private boolean paused;
    private Image segmentImage;
    private int growthCounter;

//    grow
//    eat
//    move
//    die
//    draw
    {
        segmentImage = ResourceTools.loadImageFromResource("resources/Navi.png");
    }

    public void draw(Graphics graphics) {

        Image segment = segmentImage.getScaledInstance(drawData.getCellWidth(), drawData.getCellHieght(), Image.SCALE_FAST);
        for (Point bodySegmentLocation : getBody()) {

//            System.out.println("Location = " + bodySegmentLocation);
//            System.out.println("System Loc = " + getDrawData().getCellSystemCoordinate(bodySegmentLocation));
            graphics.setColor(Color.GREEN);
//            Point topLeft = drawData.getCellSystemCoordinate (bodySegmentLocation);
//            graphics.drawOval (topLeft.x, topLeft.y,drawData.getCellWidth(), drawData.getCellHieght());
//
            Point topLeft = drawData.getCellSystemCoordinate(bodySegmentLocation);
//            graphics.fillOval(topLeft.x, topLeft.y, drawData.getCellWidth(), drawData.getCellHieght());

            graphics.drawImage(segment, topLeft.x, topLeft.y, null);
        }

    }

    /**
     * @return the body
     */
    public ArrayList<Point> getBody() {
        return body;
    }

    /**
     * @param body the body to set
     */
    public void setBody(ArrayList<Point> body) {
        this.body = body;
    }

    /**
     * @return the direction
     */
    public Direction getDirection() {
        return direction;
    }

    /**
     * @param direction the direction to set
     */
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * @return the drawData
     */
    public GridDrawData getDrawData() {
        return drawData;
    }

    /**
     * @param drawData the drawData to set
     */
    public void setDrawData(GridDrawData drawData) {
        this.drawData = drawData;
    }
    private final int HEAD_POSITION = 0;

//<editor-fold defaultstate="collapsed" desc="Move">
    public void move() {
        // make the snake move, please!
        if (!paused) {
            
            Point newHead = (Point) getHead().clone();
            if (direction == Direction.DOWN) {
                newHead.y++;
            } else if (direction == Direction.RIGHT) {
                newHead.x++;
                
            } else if (direction == Direction.LEFT) {
                newHead.x--;
            } else if (direction == Direction.UP) {
                newHead.y--;
            }
            
            if (this.getLocationValidator() != null) {
                body.add(HEAD_POSITION, getLocationValidator().validateLocation(newHead));
            }
            
            if (growthCounter == 0) {
                body.remove(body.size() - 1);
            } else if (growthCounter < 0) {
                while (growthCounter < 0) {
                    body.remove(body.size() - 1);
                    growthCounter++;
                }
            } else {
                growthCounter--;
            }
        }
    }
//</editor-fold>

    public Point getHead() {
        return body.get(HEAD_POSITION);
    }

    /**
     * @return the locationValidator
     */
    public LocationValidatorIntf getLocationValidator() {
        return locationValidator;
    }

    /**
     * @param locationValidator the locationValidator to set
     */
    public void setLocationValidator(LocationValidatorIntf locationValidator) {
        this.locationValidator = locationValidator;
    }

    /**
     * @return the paused
     */
    public boolean isPaused() {
        return paused;
    }

    /**
     * @param paused the paused to set
     */
    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    public void togglePaused() {
        paused = !paused;
    }

    /**
     * @return the growthCounter
     */
    public int getGrowthCounter() {
        return growthCounter;
    }

    /**
     * @param growthCounter the growthCounter to set
     */
    public void setGrowthCounter(int growthCounter) {
        this.growthCounter = growthCounter;
    }

    public void grow(int length) {
        growthCounter += length;
    }

}
