package timer;

import exceptions.MediaTimerAlreadyDefinedException;
import exceptions.MediaTimerStateException;
import exceptions.UndefinedMediaTimerException;
import observable.ObservableSubject;

import java.util.Timer;
import java.util.TimerTask;

public class StdMediaTimer extends ObservableSubject implements MediaTimer {

    private static MediaTimer instance = null;

    private Timer timer;
    private TimerTask task;
    private int time;

    private TimerState state;

    protected StdMediaTimer(){
        super();

        time = -1;
        state = TimerState.NOT_STARTED;
    }

    public static synchronized MediaTimer getInstance() throws UndefinedMediaTimerException {
        if (instance == null) throw new UndefinedMediaTimerException("The timer is not defined");
        return instance;
    }

    protected static synchronized void setInstance(MediaTimer mediaTimer) throws MediaTimerAlreadyDefinedException {
        if (instance != null) throw new MediaTimerAlreadyDefinedException("The timer is already defined");
        instance = mediaTimer;
    }

    public static void setInstance() throws MediaTimerAlreadyDefinedException {
        setInstance(new StdMediaTimer());
    }

    @Override
    public void start() throws MediaTimerStateException {
        if (state != TimerState.NOT_STARTED) throw new MediaTimerStateException("The timer is already running");

        time = -1;
        timer = new Timer();
        task = new Task();
        timer.scheduleAtFixedRate(task, 0, 1000);
        state = TimerState.RUNNING;
    }

    @Override
    public void restart() throws MediaTimerStateException {
        if (state == TimerState.NOT_STARTED) throw new MediaTimerStateException("The timer has not been initialized");

        timer.cancel();
        time = -1;
        timer = new Timer();
        task = new Task();
        timer.scheduleAtFixedRate(task, 0, 1000);
        state = TimerState.RUNNING;
    }

    @Override
    public void pause() throws MediaTimerStateException {
        if (state != TimerState.RUNNING) throw new MediaTimerStateException("The timer is not running");

        timer.cancel();
        state = TimerState.PAUSED;
    }

    @Override
    public void resume() throws MediaTimerStateException {
        if (state != TimerState.PAUSED) throw new MediaTimerStateException("The timer has not been paused");

        timer = new Timer();
        task = new Task();
        timer.scheduleAtFixedRate(task, 0, 1000);
    }

    @Override
    public void stop() throws MediaTimerStateException {
        if (state == TimerState.NOT_STARTED) throw new MediaTimerStateException("No timer to stop");

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
