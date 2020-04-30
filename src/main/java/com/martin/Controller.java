package com.martin;

import javafx.event.ActionEvent;
import javafx.scene.input.SwipeEvent;

import javafx.scene.input.TouchEvent;


public class Controller {
    public void handleSwipeDown(SwipeEvent swipeEvent) {
        System.out.println("EVENT: swipeDown");
    }
    public void handleSwipeUp(SwipeEvent swipeEvent) {
        System.out.println("EVENT: swipeUp");
    }

    public void handleSwipeLeft(SwipeEvent swipeEvent) {
        System.out.println("EVENT: SwipeLeft");
    }

    public void handleSwipeRight(SwipeEvent swipeEvent) {
        System.out.println("EVENT: SwipeRight");
    }

    public void btnClick(ActionEvent actionEvent) {
        System.out.println("EVENT: Click");
    }
}
