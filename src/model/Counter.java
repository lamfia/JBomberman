package model;

import java.util.Observable;

/**
 * memorizza un valore int che può essere incrementato decrementato
 * resettato 
 * @author stefanofaralli
 *
 */
public class Counter extends Observable {
  private int valore;

  public void inc()
  {
	  valore++;
	  notifica();
  }

  public void dec()
  {
	  valore--;
	  notifica();
  }
  
  public void reset()
  {
	  valore=0;
	  notifica();
  }
  public int getValore()
  {
	  return valore;
  }

  //Notifica agli observers
  private void notifica()
  {
	  setChanged();
	  notifyObservers(""+valore);
	  
  }
	
}
