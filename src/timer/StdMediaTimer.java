package timer;

import exceptions.MediaTimerException;
import observable.ObservableSubject;

import java.util.Timer;
import java.util.TimerTask;

public class StdMediaTimer extends ObservableSubject implements MediaTimer {

    private Timer timer;
    private TimerTask task;
    private int time;

    private TimerState state;

    public StdMediaTimer(){
        super();

        time = -1;
        state = TimerState.NOT_STARTED;
    }

    @Override
    public void start() throws MediaTimerException {
        if (state != TimerState.NOT_STARTED) throw new MediaTimerException("The timer is already running");

        time = -1;
        timer = new Timer();
        task = new Task();
        timer.scheduleAtFixedRate(task, 0, 1000);
        state = TimerState.RUNNING;
    }

    @Override
    public void restart() throws MediaTimerException {
        if (state == TimerState.NOT_STARTED) throw new MediaTimerException("The timer has not been initialized");

        timer.cancel();
        time = -1;
        timer = new Timer();
        task = new Task();
        timer.scheduleAtFixedRate(task, 0, 1000);
        state = TimerState.RUNNING;
    }

    @Override
    public void pause() throws MediaTimerException {
        if (state != TimerState.RUNNING) throw new MediaTimerException("The timer is not running");

        timer.cancel();
        state = TimerState.PAUSED;
    }

    @Override
    public void resume() throws MediaTimerException {
        if (state != TimerState.PAUSED) throw new MediaTimerException("The timer has not been paused");

        timer = new Timer();
        task = new Task();
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    @Override
    public void stop() throws MediaTimerException {
        if (state == TimerState.NOT_STARTED) throw new MediaTimerException("No timer to stop");

        timer.cancel();
        state = TimerState.NOT_STARTED;
    }

    @Override
    public int getTime() {
        return time;
    }

    public TimerState getState(){
        return state;
    }

    private class Task extends TimerTask{
        @Override
        public void run() {
            time++;
            fireStateChanged();
        }
    }
}
