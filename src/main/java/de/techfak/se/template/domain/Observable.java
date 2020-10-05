package de.techfak.se.template.domain;

import java.beans.PropertyChangeListener;

public interface Observable {

    void addPropertyChangeListener(PropertyChangeListener observer);

    void removePropertyChangeListener(PropertyChangeListener observer);
}
