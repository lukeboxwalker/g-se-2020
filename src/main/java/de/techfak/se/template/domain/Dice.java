package de.techfak.se.template.domain;

import java.util.ArrayList;
import java.util.List;

public class Dice<T> {

    private final List<T> faces;

    public Dice(final List<T> faces) {
        this.faces = faces;
    }

    public List<T> rollDice(int times) {
        final List<T> result = new ArrayList<>();
        for (int i = 0; i < times; i++) {
            int randomIndex = (int) (Math.random() * faces.size());
            result.add(faces.get(randomIndex));
        }
        return result;
    }

}

