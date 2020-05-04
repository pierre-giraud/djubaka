package observable;

import javax.swing.event.ChangeListener;

public interface Observable {
    void addChangeListener(ChangeListener listener);
    void removeChangeListener(ChangeListener listener);
}
