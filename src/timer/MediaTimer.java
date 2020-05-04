package timer;

import exceptions.MediaTimerException;
import observable.Observable;

public interface MediaTimer extends Observable {
    void start() throws MediaTimerException;
    void restart() throws MediaTimerException;
    void pause() throws MediaTimerException;
    void resume() throws MediaTimerException;
    void stop() throws MediaTimerException;
    int getTime();
    TimerState getState();
}
