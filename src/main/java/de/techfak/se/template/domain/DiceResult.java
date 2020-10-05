package de.techfak.se.template.domain;

import java.util.ArrayList;
import java.util.List;


public class DiceResult {

    private List<Integer> rolledNumbers;
    private List<Color> rolledColors;

    public DiceResult() {
        this.rolledColors = new ArrayList<>();
        this.rolledNumbers = new ArrayList<>();
    }

    public List<Integer> getRolledNumbers() {
        return rolledNumbers;
    }

    public void setRolledNumbers(List<Integer> rolledNumbers) {
        this.rolledNumbers = rolledNumbers;
    }

    public List<Color> getRolledColors() {
        return rolledColors;
    }

    public void setRolledColors(List<Color> rolledColors) {
        this.rolledColors = rolledColors;
    }
}
