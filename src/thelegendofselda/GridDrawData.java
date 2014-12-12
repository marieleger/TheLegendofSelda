/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package thelegendofselda;

import java.awt.Point;

/**
 *
 * @author marieleger
 */
public interface GridDrawData {

    public int getCellHieght();
    public int getCellWidth();
    public Point getCellSystemCoordinate(Point cellCoordinate);

}
