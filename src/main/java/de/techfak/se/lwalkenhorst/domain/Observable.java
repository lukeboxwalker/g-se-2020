package de.techfak.se.lwalkenhorst.domain;

import java.beans.PropertyChangeListener;

public interface Observable {

    void addPropertyChangeListener(PropertyChangeListener observer);

    void removePropertyChangeListener(PropertyChangeListener observer);
}
