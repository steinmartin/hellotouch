package com.martin;

import com.sun.javafx.stage.StageHelper;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TouchEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.PopupWindow;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application implements ListChangeListener<Window> {

    @Override
    public void start(Stage stage) {

//        var label = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
//        var scene = new Scene(new StackPane(label), 640, 480);
//        stage.setScene(scene);
//        stage.show();

        stage.addEventFilter(TouchEvent.ANY, e -> System.out.println("touch event: " + e.getEventType()));
        stage.addEventFilter(MouseEvent.ANY, e -> System.out.println("mouse event: " + e.getEventType()));
        final ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll("Test1", "Test2", "Test3");

        Parent root = null;
        Scene scene = null;
        try {
            root = FXMLLoader.load(getClass().getResource("app.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println(e);
        }

        System.err.println("Root from FXML is: " + root);

        scene = new Scene(root,800,800);
        //stage.setScene(new Scene(new Pane(comboBox)));
        stage.setScene(scene);

        scene.setOnTouchPressed(touchEvent -> System.out.println("touch event: " + touchEvent.getEventType()));
        stage.show();
        // Java 9 and above
        Window.getWindows().addListener(this);

        // Java 8
        //StageHelper.getStages().addListener(this);
    }

    @Override
    public void onChanged(ListChangeListener.Change<? extends Window> c) {
        if (!c.next()) return;
        for (Window w : c.getAddedSubList()) {
            if (w instanceof PopupWindow) {
                w.addEventFilter(TouchEvent.ANY,
                        e -> System.out.println("touch event (PopupWindow): " + e.getEventType()));
                w.addEventFilter(MouseEvent.ANY,
                        e -> System.out.println("mouse event (PopupWindow): " + e.getEventType()));
                // Java 9 and above
                Window.getWindows().removeListener(this);

                // Java 8
                // StageHelper.getStages().removeListener(this);

            }
        }
    }

    public static void main(String[] args){
            launch();
    }
}