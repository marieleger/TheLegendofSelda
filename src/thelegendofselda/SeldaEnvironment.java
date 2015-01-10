/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thelegendofselda;

import audio.AudioPlayer;
import environment.Environment;
import environment.GraphicsPalette;
import environment.LocationValidatorIntf;
import grid.Grid;
import images.ResourceTools;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

/**
 *
 * @author marieleger
 */
class SeldaEnvironment extends Environment implements GridDrawData, LocationValidatorIntf {

    private Grid grid;
    private Snake snake;
    private Score score;
    
    private GridDrawData drawData;
    private Image segmentImage;
    private Image triForceImage;

    public int SLOW_SPEED = 7;
    public int MEDIUM_SPEED = 4;
    public int HIGH_SPEED = 2;

    private int moveDelayLimit = MEDIUM_SPEED;
    private int moveDelayCounter = 0;

    private ArrayList<GridObject> gridObjects;
    
//    private Image segmentImage

    public SeldaEnvironment() {
    }

    public Point randomPoint() {
        return new Point((int) (Math.random() * grid.getColumns() - 1), ((int) (Math.random() * grid.getRows() - 1)));
    }

    @Override
    public void initializeEnvironment() {
        score = new Score();
      
        score. setPosition(new Point (10,15));
        
        
        this.setBackground(ResourceTools.loadImageFromResource("resources/ZeldaBackground.jpg").getScaledInstance(1400, 900, Image.SCALE_FAST));

        segmentImage = ResourceTools.loadImageFromResource("resources/RupeeGreen.png");
        triForceImage = ResourceTools.loadImageFromResource("resources/Triforce.gif");
    
        grid = new Grid(30, 25, 25, 25, new Point(50, 100), Color.lightGray);
        grid.setColor(new Color(23, 39, 77));
//        grid.getcells
        snake = new Snake();
        snake.setDirection(Direction.DOWN);
        snake.setDrawData(this);
        snake.setLocationValidator(this);

        ArrayList<Point> body = new ArrayList<>();
        body.add(new Point(3, 1));
        body.add(new Point(3, 2));
        body.add(new Point(2, 2));
        body.add(new Point(2, 3));

        snake.setBody(body);

        gridObjects = new ArrayList<>();
        gridObjects.add(new GridObject(GridObjectType.TRIFORCE, randomPoint()));
        gridObjects.add(new GridObject(GridObjectType.POISONBOTTLE, randomPoint()));
        gridObjects.add(new GridObject(GridObjectType.RUPEE, randomPoint()));
    }

    @Override
    public void timerTaskHandler() {
        if (snake != null) {
            //if the counter  >= limit then reset counter and move snake 
            // else increment coutner
            if (this.moveDelayCounter >= moveDelayLimit) {
                moveDelayCounter = 0;
                snake.move();

            } else {
                moveDelayCounter++;

            }

        }
    }

    @Override
    public void keyPressedHandler(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_Q) {
            grid.setShowCellCoordinates(!grid.getShowCellCoordinates());

        }

        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            snake.setDirection(Direction.RIGHT);
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            snake.setDirection(Direction.LEFT);
        }

        if (e.getKeyCode() == KeyEvent.VK_UP) {
            snake.setDirection(Direction.UP);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            snake.setDirection(Direction.DOWN);
        }

        if (e.getKeyCode() == KeyEvent.VK_P) {
            snake.setPaused(!snake.isPaused());
        }
        if (e.getKeyCode() == KeyEvent.VK_1) {
            moveDelayLimit = SLOW_SPEED;
        }
        if (e.getKeyCode() == KeyEvent.VK_2) {
            moveDelayLimit = MEDIUM_SPEED;
        }
        if (e.getKeyCode() == KeyEvent.VK_3) {
            moveDelayLimit = HIGH_SPEED;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            snake.grow(2);
        }

    }

    @Override
    public void keyReleasedHandler(KeyEvent e) {
    }

    @Override
    public void environmentMouseClicked(MouseEvent e) {
    }

    //<editor-fold defaultstate="collapsed" desc="GridDrawData Interface">
    @Override
    public void paintEnvironment(Graphics graphics) {
        if (grid != null) {
            grid.paintComponent(graphics);
        }
        
        if (score != null){
            score.draw(graphics);
        }

        if (snake != null) {
            snake.draw(graphics);

            if (gridObjects != null) {
                for (GridObject gridObject : gridObjects) {
                    if (gridObject.getType() == GridObjectType.TRIFORCE) {

//                        GraphicsPalette.drawTriforce(graphics, grid.getCellSystemCoordinate(gridObject.getLocation()), grid.getCellSize(), Color.CYAN, Color.BLACK);
                        graphics.drawImage(triForceImage, grid.getCellSystemCoordinate(gridObject.getLocation()).x, 
                                                      grid.getCellSystemCoordinate(gridObject.getLocation()).y, 
                                                      grid.getCellWidth(), grid.getCellHeight(), this);
                    }

                }
            }
            if (gridObjects != null) {
                for (GridObject gridObject : gridObjects) {
                    if (gridObject.getType() == GridObjectType.POISONBOTTLE) {

                        GraphicsPalette.drawPoisonBottle(graphics, grid.getCellSystemCoordinate(gridObject.getLocation()), grid.getCellSize(), Color.RED);
                    }
                }
            }
            if (gridObjects !=null) {
             for (GridObject gridObject : gridObjects) {
                 if (gridObject.getType() == GridObjectType.RUPEE) {
//                     segmentImage = ResourceTools.loadImageFromResource("resources/RupeeGreen.png");
                     //segmentImage.getScaledInstance(getDrawData().getCellWidth(), getDrawData().getCellHieght(), Image.SCALE_FAST);
                     graphics.drawImage(segmentImage, grid.getCellSystemCoordinate(gridObject.getLocation()).x, 
                                                      grid.getCellSystemCoordinate(gridObject.getLocation()).y, 
                                                      grid.getCellWidth(), grid.getCellHeight(), this);
                 }
             }
       
            }
        }
    }

    @Override
    public int getCellHieght() {
        return grid.getCellHeight();
    }

    @Override
    public int getCellWidth() {
        return grid.getCellWidth();
    }

    @Override
    public Point getCellSystemCoordinate(Point cellCoordinate) {
        return grid.getCellSystemCoordinate(cellCoordinate);
    }
//</editor-fold>

    //<editor-fold defaultstate="collapsed" desc=" LocationValidatorIntf">
    @Override
    public Point validateLocation(Point point) {
        if (point.x < 0) {
            point.x = grid.getColumns() - 1;
        }
        if (point.x > grid.getColumns() - 1) {
            point.x = 0;
        } 
        if (point.y < 0) {
            point.y = grid.getRows() - 1;
        }
        if (point.y > grid.getRows() - 1) {
            point.y = 0;
        }

//        check if the snake hit a GridObject, thne take appropiate
//        actions
//        Apple - grow snake 
//        posion - make sound, kill snake, lose points
//        look at all the location stored un the gridObject Arraylist
//         for each, compare it to the hea location stored
//          in the "point" parameter
        for (GridObject object : gridObjects) {
            if (object.getLocation().equals(point)) {
                System.out.println("HIT " + object.getType());
                if (object.getType() == GridObjectType.TRIFORCE) {

                    snake.grow(WIDTH);
                    object.setLocation(this.randomPoint());
                    AudioPlayer.play("/resources/RupeeSound 2.wav");
                    this.score.addToValue(+100);
                } else if (object.getType() == GridObjectType.POISONBOTTLE) {
                    snake.grow(-2);
                    object.setLocation(this.randomPoint());
                  this.score.addToValue(-500);

                } else if (object.getType() == GridObjectType.RUPEE) {
                    snake.grow(WIDTH);
                    object.setLocation(this.randomPoint());
                    AudioPlayer.play("/resources/RupeeSound 2.wav");
                    this.score.addToValue(+10);
                }
            }
        }
        return point;

    }

//</editor-fold>

//<editor-fold defaultstate="collapsed" desc="DrawData get & set">
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
}
//</editor-fold>
