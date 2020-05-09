package timer;

import exceptions.MediaTimerStateException;
import observable.Observable;

public interface MediaTimer extends Observable {
    void start() throws MediaTimerStateException;
    void restart() throws MediaTimerStateException;
    void pause() throws MediaTimerStateException;
    void resume() throws MediaTimerStateException;
    void stop() throws MediaTimerStateException;
    int getTime();
    TimerState getState();
}
