package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public  class Movimento extends Observable {

    public int pos_x;

    public int pos_y;

    public Movimento(int posX, int posY) {
        pos_x = posX;
        pos_y = posY;
    }

    public ArrayList<Integer> getPos() {
        ArrayList<Integer> Pos = new ArrayList<Integer>();


        Pos.add(pos_x);
        Pos.add(pos_y);

        return Pos;
    }

    public void goUp(){
        pos_y=pos_y-1;
        notifica();
    }
    public void goDown(){
        pos_y=pos_y+1;
        notifica();
    }
    public void goLeft() {

        pos_x=pos_x-1;
        notifica();
    }

    public void goRight() {

        pos_x=pos_x+1;
        notifica();
    }

    public void setPost( Map<Integer, Integer> Pos){

    }

    private void notifica()
    {
        setChanged();
        notifyObservers(getPos());

    }


}
