package observable;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

public abstract class ObservableSubject implements Observable{

    protected ChangeEvent changeEvent;
    protected EventListenerList listenerList;

    protected ObservableSubject(){
        listenerList = new EventListenerList();
    }

    @Override
    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    @Override
    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    protected void fireStateChanged(){
        ChangeListener[] listeners = listenerList.getListeners(ChangeListener.class);

        for (ChangeListener cl : listeners){
            if (changeEvent == null) changeEvent = new ChangeEvent(this);
            cl.stateChanged(changeEvent);
        }
    }
}
