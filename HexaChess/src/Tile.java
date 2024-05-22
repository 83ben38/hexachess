import acm.graphics.GCompound;
import acm.graphics.GLabel;
import acm.graphics.GPolygon;

import java.awt.*;

public class Tile extends GCompound {
    GPolygon hexagon = new GPolygon();
    GPolygon highlight = new GPolygon();
    GLabel piece = new GLabel("");
    public Tile(Color c){
        hexagon.addVertex(10,0);
        hexagon.addVertex(30,0);
        hexagon.addVertex(40,20);
        hexagon.addVertex(30,40);
        hexagon.addVertex(10,40);
        hexagon.addVertex(0,20);
        hexagon.setFillColor(c);
        hexagon.setFilled(true);
        piece.setFont(new Font(Font.SERIF,Font.BOLD,30));
        add(hexagon,0,0);
        add(piece,0,0);
        highlight.addVertex(10,0);
        highlight.addVertex(30,0);
        highlight.addVertex(40,20);
        highlight.addVertex(30,40);
        highlight.addVertex(10,40);
        highlight.addVertex(0,20);
        highlight.setFillColor(new Color(0,255,125,100));
        highlight.setFilled(true);
        add(highlight,0,0);
        highlight.setVisible(false);
    }
    public void setPiece(Color c, String name){
        piece.setLabel(name);
        piece.setColor(c);
        piece.setLocation(getWidth()/2-piece.getWidth()/2,piece.getHeight()*3/4);
    }
    public void setHighlighted(boolean highlighted){
        highlight.setVisible(highlighted);
    }
    public boolean isHighlighted(){
        return highlight.isVisible();
    }
    public Color getColor(){
        return piece.getColor();
    }
    public boolean isPiece(){
        return !piece.getLabel().isEmpty();
    }
}
