/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thelegendofselda;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;

/**
 *
 * @author marieleger
 */
public class Score {
    public void draw(Graphics graphics){
        graphics.setColor(Color.WHITE);
        graphics .drawString("Score: " + value , position.x, position.y);
        graphics.setFont(font);
        
    }
//<editor-fold defaultstate="collapsed" desc="Properties ">
    private int value = 0;
    private Point position;
    private Font font = new Font("Calibri", Font.ROMAN_BASELINE , 44);
    /**
     * @return the value
     */
    public int getValue() {
        return value;
    }
    
    /**
     * @param value the value to set
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    public void addToValue(int amount) {
        this.value += amount;
    }
//</editor-fold>

    /**
     * @return the position
     */
    public Point getPosistion() {
        return position;
    }

    /**
     * @param posistion the position to set
     */
    public void setPosistion(Point posistion) {
        this.position = posistion;
    }
}
