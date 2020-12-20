package de.techfak.se.lwalkenhorst.view;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
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
        setStyle("-fx-background-color: transparent; -fx-border-color: transparent; -fx-focus-color: transparent; -fx-padding: 15px");
        setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
    }

    public void info(final String message) {
        log(message, Paint.valueOf("#000000"));
    }

    public void warn(final String message) {
        log(message, Paint.valueOf("#FF0000"));
    }

    private void log(final String message, final Paint paint) {
        final Text text = new Text(message);
        text.setFont(Font.font("arial", 15));
        text.setFill(paint);
        addMessage(text);
    }

    private void addMessage(final Text message) {
        final Text text = new Text("[" + formatter.format(LocalDateTime.now()) + "]: ");
        text.setFont(Font.font("arial", 15));
        messages.getChildren().add(new HBox(text, message));
        setVvalue(Double.MAX_VALUE);
    }
}
