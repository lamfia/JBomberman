package model;

import java.time.Duration;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Observable;
import java.util.Timer;

public class Time extends Observable {



    public Duration currentTime;




     public Time(){
         currentTime =  Duration.ZERO;
     }

    public void notifyCurrentTime() throws InterruptedException {

        Thread.sleep(1000);
        notifica();
    }

    private void notifica()
    {
        currentTime= currentTime.plusSeconds(1);
        setChanged();
        notifyObservers(getTempo());

    }


    public String getTempo() {
        // Get hours, minutes, and seconds from the Duration
        long hours = currentTime.toHours();
        long minutes = currentTime.toMinutes() % 60;
        long seconds = currentTime.getSeconds() % 60;

        // Format as "HH:mm:ss"
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }
}
