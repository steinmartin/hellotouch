        package com.martin;

        import javafx.animation.PathTransition;
        import javafx.application.Application;
        import javafx.collections.ListChangeListener;
        import javafx.event.EventHandler;
        import javafx.fxml.FXMLLoader;
        import javafx.geometry.Insets;
        import javafx.geometry.Point2D;
        import javafx.scene.Group;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.ComboBox;
        import javafx.scene.control.Label;
        import javafx.scene.effect.DropShadow;
        import javafx.scene.effect.Lighting;
        import javafx.scene.image.Image;
        import javafx.scene.image.ImageView;
        import javafx.scene.input.MouseEvent;
        import javafx.scene.input.SwipeEvent;
        import javafx.scene.input.TouchEvent;
        import javafx.scene.input.TouchPoint;
        import javafx.scene.layout.HBox;
        import javafx.scene.layout.Pane;
        import javafx.scene.layout.VBox;
        import javafx.scene.paint.Color;
        import javafx.scene.paint.CycleMethod;
        import javafx.scene.paint.RadialGradient;
        import javafx.scene.paint.Stop;
        import javafx.scene.shape.*;
        import javafx.stage.PopupWindow;
        import javafx.stage.Stage;
        import javafx.stage.Window;
        import javafx.util.Duration;

        import java.io.IOException;

        /**
         * JavaFX App
         */
        public class App extends Application implements ListChangeListener<Window> {


            @Override
            public void start(Stage stage) {

                var label = new Label("Hello, JavaFX " + SystemInfo.javafxVersion() + ", running on Java " + SystemInfo.javaVersion() + ".");
        //        var scene = new Scene(new StackPane(label), 640, 480);
        //        stage.setScene(scene);
        //        stage.show();

                stage.addEventFilter(TouchEvent.ANY, event -> System.out.println("TouchPoint: " + event.getTouchPoint()));
                stage.addEventFilter(MouseEvent.ANY, event -> System.out.println("Mouse Event: " + event.getEventType()));
                stage.addEventFilter(SwipeEvent.ANY, event -> System.out.println("Swipe Event: " + event.getEventType()));
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

                //scene = new Scene(root,800,800);
                //scene = new Scene(new Pane(comboBox),800,800);

                //scene = sceneOnePath();
                //scene = sceneTwoPanes();
                scene = sceneBalls();

               //scene = sceneExample();


                stage.setScene(scene);
                stage.show();

                //scene.setOnTouchPressed(touchEvent -> System.out.println("touch event: " + touchEvent.getEventType()));
                // Java 9 and above
                Window.getWindows().addListener(this);

                // Java 8
                //StageHelper.getStages().addListener(this);
            }

            Scene sceneExample() {

                Scene scene = new Scene(new Group());

                Path path = new Path();
                path.getElements().add(new MoveTo(0.0f, 50.0f));
                path.getElements().add(new LineTo(100.0f, 100.0f));

                VBox vbox = new VBox();
                vbox.getChildren().addAll(path);
                vbox.setSpacing(5);

                HBox root = new HBox();
                root.getChildren().add(vbox);
                root.setSpacing(40);
                root.setPadding(new Insets(20, 10, 10, 20));

                ((Group) scene.getRoot()).getChildren().add(root);
                return scene;
            }


            Scene sceneBalls() {
                Group root = new Group();
                final TouchImage img1 = new TouchImage(50, 20,
                        new Image(getClass().getResource(
                                "images/folder002.png").toExternalForm(), false));
                final TouchImage img2 = new TouchImage(350, 20,
                        new Image(getClass().getResource(
                                "images/folder004.png").toExternalForm(), false));
                final TouchImage img3 = new TouchImage(150, 200,
                        new Image(getClass().getResource(
                                "images/folder006.png").toExternalForm(), false));
                final TouchImage img4 = new TouchImage(450, 200,
                        new Image(getClass().getResource(
                                "images/folder007.png").toExternalForm(), false));

                root.getChildren().addAll(img1, img2, img3, img4);

                for (int i = 1; i <= 5; i++) {
                    Rectangle pad = new Rectangle(100, 85, Color.BLANCHEDALMOND);
                    pad.setTranslateX((i*125-100));
                    pad.setTranslateY(470);
                    pad.setStroke(Color.GRAY);

                    root.getChildren().add(pad);
                }

                root.getChildren().add(new Ball(75, 512));

                Scene scene = new Scene(root, 650, 600);
                scene.setFill(Color.GAINSBORO);

                return scene;

            }

            Scene sceneTwoPanes() {

                final Path leftPath = new Path();
                final Path rightPath = new Path();

                HBox root = new HBox();


                //HBox hbox = new HBox();

                VBox paneLeft = new VBox();
                //paneLeft.setVisible(false);
                VBox paneRight = new VBox();
                //paneRight.setVisible(false);*/

                //hbox.setMargin(paneLeft, new Insets(20, 20, 20, 20));
                //hbox.setMargin(paneRight, new Insets(20, 20, 20, 20));



                paneLeft.getChildren().add(leftPath);
                paneRight.getChildren().add(rightPath);

                root.getChildren().add(paneLeft);
                root.getChildren().add(paneRight);

                Scene scene = new Scene(root, 900, 500, Color.WHITE);



                paneLeft.setOnTouchPressed(touchEvent -> {
                    System.out.println("Touch id pressed: " + touchEvent.getTouchPoint().getId());
                    if (touchEvent.getTouchPoint().getId() == 1) {
                        startPath(leftPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY());
                    } else if (touchEvent.getTouchPoint().getId() == 2) {
                        drawPath(leftPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY());
                    }
                });
//                scene.setOnTouchReleased(touchEvent -> {
//                System.out.println("Touch id released: " + touchEvent.getTouchPoint().getId());
//                    if (touchEvent.getTouchPoint().getId() == 1) {
//
//                        leftPath.getElements().clear();
//                        drawPath(leftPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY());
//                    } else if (touchEvent.getTouchPoint().getId() == 2) {
//                        //drawPath(rightPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY());
//                    }
//                });

                paneRight.setOnTouchPressed(touchEvent -> {
                    System.out.println("Touch id pressed: " + touchEvent.getTouchPoint().getId());
                    if (touchEvent.getTouchPoint().getId() == 1) {
                        startPath(rightPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY());
                    } else if (touchEvent.getTouchPoint().getId() == 2) {
                        drawPath(rightPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY());
                    }
                });

       /*         hbox.setOnTouchPressed(touchEvent ->
                        startPath(rightPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY()));

                hbox.setOnTouchMoved(touchEvent ->
                        drawPath(rightPath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY()));*/

                //hbox.setOnTouchReleased(touchEvent -> endPath(leftPath, pathTransition));
                //hbox.setOnTouchReleased(touchEvent -> endPath(rightPath, pathTransition));

                //groupRoot.getChildren().add(hbox);

                //((Group) scene.getRoot()).getChildren().add(groupRoot);


                return scene;

            }

            Scene sceneOnePath() {
                final Path onePath = new Path();

                Group root = new Group(onePath);
                Scene scene = new Scene(root, 1900, 1000, Color.WHITE);
                RadialGradient gradient1 = new RadialGradient(0, 0.1,100, 100, 20, false, CycleMethod.NO_CYCLE, new Stop(0, Color.RED), new Stop(1, Color.BLACK));
                // create a sphere
                Circle sphere = new Circle(100, 100, 20, gradient1);
                // add sphere
                root.getChildren().add(sphere);
                // animate sphere by following the path.
                PathTransition pathTransition = new PathTransition(Duration.millis(4000), onePath, sphere);
                pathTransition.setCycleCount(1);
                pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                // once finished clear path
                pathTransition.setOnFinished(actionEvent ->
                        onePath.getElements().clear());
                // starting initial path
                scene.setOnMousePressed(mouseEvent ->
                        startPath(onePath, mouseEvent.getX(), mouseEvent.getY()));
                scene.setOnTouchPressed(touchEvent ->
                        startPath(onePath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY()));
                // dragging creates lineTos added to the path
                scene.setOnMouseDragged(mouseEvent ->
                        drawPath(onePath, mouseEvent.getX(), mouseEvent.getY()));
                scene.setOnTouchMoved(touchEvent ->
                        drawPath(onePath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY()));
                /*scene.setOnTouchStationary(touchEvent -> {
                    drawPath(onePath, touchEvent.getTouchPoint().getX(), touchEvent.getTouchPoint().getY());
                });*/
                // end the path when mouse released event
                scene.setOnMouseReleased(mouseEvent -> endPath(onePath, pathTransition));
                scene.setOnTouchReleased(touchEvent -> endPath(onePath, pathTransition));
                return scene;
            }

            private void startPath(Path path, double x, double y) {
                Point2D anchorPoint  = new Point2D(x,y);

                // start point in path
                path.setStrokeWidth(3);
                path.setStroke(Color.BLACK);
                path.getElements().add(new MoveTo(anchorPoint.getX(), anchorPoint.getY()));
            }

            private void drawPath(Path path, double x, double y) {
                path.getElements().add(new LineTo(x, y));
            }

            private void endPath(Path path, PathTransition pathTransition) {
                path.setStrokeWidth(0.2);
                if (path.getElements().size() > 1) {
                    pathTransition.stop();
                    pathTransition.playFromStart();
                }
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

            /**
             * Image that can be dragged by a finger on a touch screen
             */
            public static class TouchImage extends ImageView {
                private long touchId = -1;
                double touchx, touchy;


                public TouchImage(int x, int y, Image img) {
                    super(img);
                    setTranslateX(x);
                    setTranslateY(y);
                    setEffect(new DropShadow(8.0, 4.5, 6.5, Color.DARKSLATEGRAY));

                    setOnTouchPressed(new EventHandler<TouchEvent>() {
                        @Override public void handle(TouchEvent event) {
                            if (touchId == -1) {
                                touchId = event.getTouchPoint().getId();
                                touchx = event.getTouchPoint().getSceneX() - getTranslateX();
                                touchy = event.getTouchPoint().getSceneY() - getTranslateY();
                            }
                            event.consume();
                        }
                    });

                    setOnTouchReleased(new EventHandler<TouchEvent>() {
                        @Override public void handle(TouchEvent event) {
                            if (event.getTouchPoint().getId() == touchId) {
                                touchId = -1;
                            }
                            event.consume();
                        }
                    });

                    setOnTouchMoved(new EventHandler<TouchEvent>() {
                        @Override public void handle(TouchEvent event) {
                            if (event.getTouchPoint().getId() == touchId) {
                                setTranslateX(event.getTouchPoint().getSceneX() - touchx);
                                setTranslateY(event.getTouchPoint().getSceneY() - touchy);
                            }
                            event.consume();
                        }
                    });
                }
            }

            /**
             * Ball that can jump from one rectangle to another by touching the
             * ball on a touch screen with one finger and touching another rectangle
             * with a second finger. As long as the finger currently over the ball is
             * not lifted, the ball can be jumped again.
             */
            private static class Ball extends Circle {
                double touchx, touchy;

                public Ball(int x, int y) {
                    super(35);

                    RadialGradient gradient = new RadialGradient(0.8, -0.5, 0.5, 0.5, 1,
                            true, CycleMethod.NO_CYCLE, new Stop [] {
                            new Stop(0, Color.FIREBRICK),
                            new Stop(1, Color.BLACK)
                    });

                    setFill(gradient);
                    setTranslateX(x);
                    setTranslateY(y);

                    setOnTouchPressed(new EventHandler<TouchEvent>() {
                        @Override public void handle(TouchEvent event) {
                            if (event.getTouchCount() == 1) {
                                touchx = event.getTouchPoint().getSceneX() - getTranslateX();
                                touchy = event.getTouchPoint().getSceneY() - getTranslateY();
                                setEffect(new Lighting());
                            }
                            event.consume();
                        }
                    });

                    setOnTouchReleased(new EventHandler<TouchEvent>() {
                        @Override public void handle(TouchEvent event) {
                            setEffect(null);
                            event.consume();
                        }
                    });

                    // Jump if the first finger touched the ball and is either
                    // moving or still, and the second finger touches a rectangle
                    EventHandler<TouchEvent> jumpHandler = new EventHandler<TouchEvent>() {
                        @Override public void handle(TouchEvent event) {

                            if (event.getTouchCount() != 2) {
                                // Ignore if this is not a two-finger touch
                                return;
                            }

                            TouchPoint main = event.getTouchPoint();
                            TouchPoint other = event.getTouchPoints().get(1);

                            if (other.getId() == main.getId()) {
                                // Ignore if the second finger is in the ball and
                                // the first finger is anywhere else
                                return;
                            }

                            if (other.getState() != TouchPoint.State.PRESSED ||
                                    other.belongsTo(Ball.this) ||
                                    !(other.getTarget() instanceof Rectangle) ){
                                // Jump only if the second finger was just
                                // pressed in a rectangle
                                return;
                            }

                            // Jump now
                            setTranslateX(other.getSceneX() - touchx);
                            setTranslateY(other.getSceneY() - touchy);

                            // Grab the destination touch point, which is now inside
                            // the ball, so that jumping can continue without
                            // releasing the finger
                            other.grab();

                            // The original touch point is no longer of interest, so
                            // call ungrab() to release the target
                            main.ungrab();

                            event.consume();
                        }
                    };

                    setOnTouchStationary(jumpHandler);
                    setOnTouchMoved(jumpHandler);
                }
            }

        }