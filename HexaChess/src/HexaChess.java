import acm.graphics.GPoint;
import acm.graphics.GPolygon;
import acm.program.GraphicsProgram;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

public class HexaChess extends GraphicsProgram {
    Tile selected;
    int turn = 0;
    boolean three = false;
    HashMap<GPoint, Tile> tiles = new HashMap<>();
    @Override
    public void run() {
        setBackground(Color.gray);
        {
            Color[] colors = new Color[]{Color.black, Color.gray, Color.white};
            int color = 0;
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j <= i; j++) {
                    Tile t = new Tile(colors[color]);
                    int y = (j + ((5 - i) / 2)) * 2 + (i + 1) % 2;
                    tiles.put(new GPoint(i, y), t);
                    add(t, getPostition(i, (j + ((5 - i) / 2))));
                }
                color++;
                if (color > 2) {
                    color = 0;
                }
            }
            for (int i = 0; i < 6; i++) {
                for (int j = 5; j < 7; j++) {

                    for (int k = 0; k < j; k++) {
                        Tile t = new Tile(colors[color]);
                        int y = k * 2 + (i * 2 + j) % 2;
                        tiles.put(new GPoint(i * 2 + j - 1, y), t);
                        add(t, getPostition(i * 2 + j - 1, k));
                    }
                    color++;
                    if (color > 2) {
                        color = 0;
                    }
                }
            }
            for (int i = 4; i >= 0; i--) {

                for (int j = 0; j <= i; j++) {
                    Tile t = new Tile(colors[color]);
                    int y = (j + ((5 - i) / 2)) * 2 + (21 - i) % 2;
                    tiles.put(new GPoint(20 - i, y), t);
                    add(t, getPostition(20 - i, (j + ((5 - i) / 2))));
                }
                color++;
                if (color > 2) {
                    color = 0;
                }
            }
        }
        {
            for (int i = 20; i > 15; i -= 2) {
                tiles.get(new GPoint(i, 5)).setPiece(Color.red, "b");
            }
            for (int i = 12; i <= 16; i++) {
                tiles.get(new GPoint(i, 17 - i)).setPiece(Color.red, "p");
            }
            for (int i = 13; i <= 16; i++) {
                tiles.get(new GPoint(i, i - 7)).setPiece(Color.red, "p");
            }
            tiles.get(new GPoint(19,4)).setPiece(Color.red,"k");
            tiles.get(new GPoint(19,6)).setPiece(Color.red,"q");
            tiles.get(new GPoint(18,7)).setPiece(Color.red,"n");
            tiles.get(new GPoint(18,3)).setPiece(Color.red,"n");
            tiles.get(new GPoint(17,8)).setPiece(Color.red,"r");
            tiles.get(new GPoint(17,2)).setPiece(Color.red,"r");
        }
        if (!three) {
            {
                for (int i = 4; i > -1; i -= 2) {
                    tiles.get(new GPoint(i, 5)).setPiece(Color.blue, "b");
                }
                for (int i = 4; i <= 8; i++) {
                    tiles.get(new GPoint(i, 13 - i)).setPiece(Color.blue, "p");
                }
                for (int i = 4; i <= 7; i++) {
                    tiles.get(new GPoint(i, i - 3)).setPiece(Color.blue, "p");
                }
                tiles.get(new GPoint(1, 4)).setPiece(Color.blue, "k");
                tiles.get(new GPoint(1, 6)).setPiece(Color.blue, "q");
                tiles.get(new GPoint(2, 7)).setPiece(Color.blue, "n");
                tiles.get(new GPoint(2, 3)).setPiece(Color.blue, "n");
                tiles.get(new GPoint(3, 8)).setPiece(Color.blue, "r");
                tiles.get(new GPoint(3, 2)).setPiece(Color.blue, "r");
            }
        }
        else{
            {
                tiles.get(new GPoint(7, 0)).setPiece(Color.blue, "k");
                tiles.get(new GPoint(4, 1)).setPiece(Color.blue, "q");
                tiles.get(new GPoint(3, 2)).setPiece(Color.blue, "n");
                tiles.get(new GPoint(9, 0)).setPiece(Color.blue, "n");
                tiles.get(new GPoint(2, 3)).setPiece(Color.blue, "r");
                tiles.get(new GPoint(11, 0)).setPiece(Color.blue, "r");
                for (int i = 0; i < 3; i++) {
                    tiles.get(new GPoint(5+i,i)).setPiece(Color.blue,"b");
                }
                for (int i = 0; i < 4; i++) {
                    tiles.get(new GPoint(13-i,i)).setPiece(Color.blue,"p");
                }
                for (int i = 1; i < 10; i+=2) {
                    tiles.get(new GPoint(i,4)).setPiece(Color.blue,"p");
                }
            }
            {
                tiles.get(new GPoint(7, 10)).setPiece(Color.green, "k");
                tiles.get(new GPoint(4, 9)).setPiece(Color.green, "q");
                tiles.get(new GPoint(3, 8)).setPiece(Color.green, "n");
                tiles.get(new GPoint(9, 10)).setPiece(Color.green, "n");
                tiles.get(new GPoint(2, 7)).setPiece(Color.green, "r");
                tiles.get(new GPoint(11, 10)).setPiece(Color.green, "r");
                for (int i = 0; i < 3; i++) {
                    tiles.get(new GPoint(5+i,10-i)).setPiece(Color.green,"b");
                }
                for (int i = 0; i < 4; i++) {
                    tiles.get(new GPoint(13-i,10-i)).setPiece(Color.green,"p");
                }
                for (int i = 1; i < 10; i+=2) {
                    tiles.get(new GPoint(i,6)).setPiece(Color.green,"p");
                }
            }
        }
        for (GPoint z: tiles.keySet()) {
            Tile t = tiles.get(z);
            t.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (t.isHighlighted()){
                        t.setPiece(selected.piece.getColor(),selected.piece.getLabel());
                        selected.setPiece(Color.black,"");
                        for (Tile v: tiles.values()) {
                            v.setHighlighted(false);
                        }
                        selected = null;
                        turn++;
                        if (turn == (three ? 3 : 2)){
                            turn = 0;
                        }
                        return;
                    }
                    if (t.isPiece() && t.getColor() == switch(turn){
                        case 0 -> Color.red;
                        case 1 -> Color.blue;
                        case 2 -> Color.green;
                        default -> null;
                    }){
                        if (selected==t){
                            for (Tile v: tiles.values()) {
                                v.setHighlighted(false);
                            }
                            selected = null;
                            return;
                        }
                        selected = t;
                        for (GPoint p: tiles.keySet()) {
                            tiles.get(p).setHighlighted(isMoveable(z,p,t.piece.getLabel()));
                        }
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        /*for (GPoint z: tiles.keySet()) {
            tiles.get(z).setPiece(Color.green,z.toString());
        }*/
    }
    public GPoint getPostition(int x, int y){
        return new GPoint(y*60+(x%2==0?30:0),x*20);
    }



    public boolean isMovablePawn(GPoint from, GPoint to, boolean forward){
        if (from.getY()==to.getY()){
            return from.getX() == to.getX()+(forward ? 2 : -2);
        }
        return false;
    }
    public boolean isTakeablePawn(GPoint from, GPoint to, boolean forward){
        if (from.getX()==to.getX()+(forward?1:-1)){
            return Math.abs(from.getY()-to.getY())==1;
        }
        return false;
    }
    public boolean isMoveableRook(GPoint from, GPoint to){


        if (to.getY() == from.getY()){
            if ((to.getX()- from.getX())%2==0){
                if (to.getX() > from.getX()) {
                    for (int i = (int) to.getX()-2; i != from.getX(); i-=2) {
                        if (tiles.get(new GPoint(i,to.getY())).isPiece()){
                            return false;
                        }
                    }
                }
                else{
                    for (int i = (int) to.getX()+2; i != from.getX(); i+=2) {
                        if (tiles.get(new GPoint(i,to.getY())).isPiece()){
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        int z = (int) (to.getX()-from.getX());
        if (to.getY()-from.getY()==z){
            if (z > 0) {
                for (int i = z-1; i > 0; i--) {
                    if (tiles.get(new GPoint(from.getX()+i,from.getY()+i)).isPiece()){
                        return false;
                    }
                }
            }
            else{
                for (int i = z+1; i < 0; i++) {
                    if (tiles.get(new GPoint(from.getX()+i,from.getY()+i)).isPiece()){
                        return false;
                    }
                }
            }
            return true;
        }
        if (to.getY()-from.getY()==-z){
            if (z > 0) {
                for (int i = z-1; i > 0; i--) {
                    if (tiles.get(new GPoint(from.getX()+i,from.getY()-i)).isPiece()){
                        return false;
                    }
                }
            }
            else{
                for (int i = z+1; i < 0; i++) {
                    if (tiles.get(new GPoint(from.getX()+i,from.getY()-i)).isPiece()){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public boolean isMoveableBishop(GPoint from, GPoint to){


        if (to.getX() == from.getX()){
            if ((to.getY()- from.getY())%2==0){
                if (to.getY() > from.getY()) {
                    for (int i = (int) to.getY()-2; i != from.getY(); i-=2) {
                        if (tiles.get(new GPoint(to.getX(),i)).isPiece()){
                            return false;
                        }
                    }
                }
                else{
                    for (int i = (int) to.getY()+2; i != from.getY(); i+=2) {
                        if (tiles.get(new GPoint(to.getX(),i)).isPiece()){
                            return false;
                        }
                    }
                }
                return true;
            }
            return false;
        }
        int z = (int) (to.getY()-from.getY());
        if (to.getX()-from.getX()==3*z){
            if (z > 0) {
                for (int i = z-1; i > 0; i--) {
                    if (tiles.get(new GPoint(from.getX()+(3*i),from.getY()+i)).isPiece()){
                        return false;
                    }
                }
            }
            else{
                for (int i = z+1; i < 0; i++) {
                    if (tiles.get(new GPoint(from.getX()+(3*i),from.getY()+i)).isPiece()){
                        return false;
                    }
                }
            }
            return true;
        }
        if (to.getX()-from.getX()==-3*z){
            if (z > 0) {
                for (int i = z-1; i > 0; i--) {
                    if (tiles.get(new GPoint(from.getX()-(3*i),from.getY()+i)).isPiece()){
                        return false;
                    }
                }
            }
            else{
                for (int i = z+1; i < 0; i++) {
                    if (tiles.get(new GPoint(from.getX()-(3*i),from.getY()+i)).isPiece()){
                        return false;
                    }
                }
            }
            return true;
        }
        return false;
    }
    public boolean isMoveableKing(GPoint from, GPoint to){

        if (to.getY() == from.getY()){
            return Math.abs(to.getX()- from.getX())==2;
        }
        return Math.abs(to.getX()- from.getX())==1 && Math.abs(to.getY()- from.getY())==1;
    }
    public boolean isMoveableQueen(GPoint from, GPoint to){
        return isMoveableRook(from,to) || isMoveableBishop(from,to);
    }
    public boolean isMoveableKnight(GPoint from, GPoint to){
        if (Math.abs(to.getY()- from.getY())==3){
            return Math.abs(to.getX()- from.getX())==1;
        }
        if (Math.abs(to.getY()- from.getY())==2){
            return Math.abs(to.getX()- from.getX())==4;
        }
        if (Math.abs(to.getY()- from.getY())==1){
            return Math.abs(to.getX()- from.getX())==5;
        }
        return false;
    }
    public boolean isMoveable(GPoint from, GPoint to, String piece){
        if (tiles.get(to).getColor() == switch(turn){
            case 0 -> Color.red;
            case 1 -> Color.blue;
            case 2 -> Color.green;
            default -> null;
        }){
            return false;
        }
        return switch (piece){
            case "p" -> isPawnMovable(from,to);
            case "r" -> isMoveableRook(from,to);
            case "k" -> isMoveableKing(from,to);
            case "q" -> isMoveableQueen(from,to);
            case "n" -> isMoveableKnight(from,to);
            case "b" -> isMoveableBishop(from,to);
            default ->  false;
        };
    }
    public boolean isPawnMovable(GPoint from, GPoint to){
        if (turn == 0){
            return tiles.get(to).isPiece() ? isTakeablePawn(from,to,true) : isMovablePawn(from,to,true);
        }
        if (!three && turn == 1){
            return tiles.get(to).isPiece() ? isTakeablePawn(from,to,false) : isMovablePawn(from,to,false);

        }
        return tiles.get(to).isPiece() ? isTakeablePawnDiagonal(from,to,turn==1) : isMovablePawnDiagonal(from,to,turn==1);

    }
    public boolean isMovablePawnDiagonal(GPoint from, GPoint to, boolean forward){
        if (from.getX()+1==to.getX()){
            return from.getY() == to.getY()+(forward ? -1 : 1);
        }
        return false;
    }
    public boolean isTakeablePawnDiagonal(GPoint from, GPoint to, boolean forward){
        if (from.getY()==to.getY()){
            return from.getX()+2==to.getX();
        }
        if (from.getX()==to.getX()+1){
            return from.getY() == to.getY()+(forward ? -1 : 1);
        }
        return false;
    }

}
