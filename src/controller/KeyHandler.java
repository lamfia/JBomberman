package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

public class KeyHandler implements KeyListener  {

    public boolean upPressed, downPressed, leftPressed, rightPressed;
    private int pos_x;

    private int pos_y;

    @Override
    public void keyTyped(KeyEvent e) {

    }
    @Override
    public void keyPressed(KeyEvent e) {

        int code= e.getKeyCode();

        if(code==KeyEvent.VK_W){
           ApplicationManager.movimento.goUp();
        }
        if(code==KeyEvent.VK_A){
            leftPressed=true;
            ApplicationManager.movimento.goLeft();
        }
        if(code==KeyEvent.VK_S){
            downPressed=true;
            ApplicationManager.movimento.goDown();
        }
        if(code==KeyEvent.VK_D){
            rightPressed=true;
            ApplicationManager.movimento.goRight();
        }

    }
    public Map<Integer,Integer> getPos() {
        Map<Integer, Integer> Pos = new HashMap<>();
        Pos.put(pos_x,pos_y);
        return Pos;
    }
    @Override
    public void keyReleased(KeyEvent e) {

        int code= e.getKeyCode();

        if(code==KeyEvent.VK_W){
            upPressed=false;
        }
        if(code==KeyEvent.VK_A){
            leftPressed=false;
        }
        if(code==KeyEvent.VK_S){
            downPressed=false;
        }
        if(code==KeyEvent.VK_D){
            rightPressed=false;
        }

    }



}
