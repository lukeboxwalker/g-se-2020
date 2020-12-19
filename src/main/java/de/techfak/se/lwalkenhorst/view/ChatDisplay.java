package de.techfak.se.lwalkenhorst.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatDisplay extends ScrollPane {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final VBox messages = new VBox();

    public ChatDisplay() {
        super();
        setContent(messages);
        setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-color: transparent; -fx-padding: 5px");
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void info(final String message) {
        addMessage(message);
    }

    private void addMessage(final String message) {
        final Text text = new Text("[" + formatter.format(LocalDateTime.now()) + "]: " + message);
        text.setFont(Font.font("arial", 15));
        messages.getChildren().add(text);
        setVvalue(Double.MAX_VALUE);
    }
}
