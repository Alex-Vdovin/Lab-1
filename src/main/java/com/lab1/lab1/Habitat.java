package com.lab1.lab1;

import com.lab1.lab1.model.Cat;
import com.lab1.lab1.model.Dog;
import com.lab1.lab1.model.Pet;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class Habitat extends Application {
    private static volatile Habitat instance;
    private Controller mainController;
    private static Timer timer;
    public boolean timeFlag;
    private boolean statFlag;
    private long startTime;
    private static int p1, n1;
    private static int p2, n2;
    public static boolean startFlag;
    private List<Pet> list;

    public Habitat() {
    }

    public static void main(String[] args) {
        launch();
        timer.cancel();
    }

    @Override
    public void start(Stage stage) throws IOException {
        instance = this;
        FXMLLoader fxmlLoader = new FXMLLoader(Habitat.class.getResource("controller.fxml"));
        Scene root = new Scene(fxmlLoader.load(), 1750, 900);
        mainController = fxmlLoader.getController();
        Image image = new Image(new FileInputStream("src/main/resources/images/classroom_image.png"));
        BackgroundImage backgroundImage = new BackgroundImage(image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        Background background = new Background(backgroundImage);
        mainController.getPane().setBackground(background);
        initialize();
        stage.setTitle("Cats and Dogs in a Classroom");
        stage.setScene(root);
        stage.getIcons().add(new Image(new FileInputStream("src/main/resources/images/mark.png")));
        stage.show();
    }

    private void initialize() {
        n1 = 1;
        n2 = 2;
        p1 = 30;
        p2 = 50;
        list = new ArrayList<>();
        timeFlag = false;
        statFlag = false;
        startFlag = false;
        timer = new Timer();
        startTime = System.currentTimeMillis();
    }

    private void createPet(long currentTime, int p, int n, String pet) {
        Random random = new Random();
        int randomP;
        if ((currentTime / 1000) % n == 0) {
            randomP = random.nextInt(300);
            if (p <= randomP) {
                if (pet.equals("cat")) {
                    try {
                        createCat();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                } else if (pet.equals("dog")) {
                    try {
                        createDog();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }

            }
        }
    }

    private void update(long currentTime) throws IOException {
        if (startFlag) {
            createPet(currentTime, p1, n1, "cat");
            createPet(currentTime, p2, n2, "dog");
        }
    }

    private void createCat() throws FileNotFoundException {
        Random random = new Random();
        Cat cat = new Cat(
                random.nextInt((int) mainController.getPane().getWidth() - 200),
                random.nextInt((int) mainController.getPane().getHeight() - 500),
                "src/main/resources/images/cat_image.png"
        );
        mainController.getPane().getChildren().add(cat.getImageView());
        list.add(cat);
    }

    public void startAction() {
        startFlag = true;
        statFlag = false;
        startTime = System.currentTimeMillis();
        mainController.getStatistic().setVisible(false);
        startCycle();
    }

    private void startCycle() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    try {
                        update(System.currentTimeMillis() - startTime);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

            }
        }, 0, 1000);
    }

    public void stopAction() throws IOException {
        statFlag = true;
        startFlag = false;
        update(System.currentTimeMillis() - startTime);
        showStatLabel();
        if (!startFlag) {
            timer.cancel();
            timer = new Timer();
            clearList();
            startTime = System.currentTimeMillis();
        }
    }

    private void showStatLabel() {
        String statStr;
        if (statFlag) {
            long finalTime = System.currentTimeMillis() - startTime;
            long countCats = list.stream().filter(i -> i instanceof Cat).count();
            long countDogs = list.stream().filter(i -> i instanceof Dog).count();
            statStr = "Cats: " + countCats;
            statStr += "\nDogs: " + countDogs;
            mainController.getStatistic().setVisible(true);
            mainController.printStatistic(statStr);
        }
    }

    public void showTimeLabel() {
        String timeStr = "";
        if (timeFlag && startFlag) {
            //if (startTime != 0) {
            long time = System.currentTimeMillis() - startTime;
            timeStr = "Time: " + time / 1000;
            mainController.getTime().setVisible(true);
            mainController.printLabel(timeStr);
        } else {
            mainController.getTime().setVisible(false);
        }
    }
    public static Habitat getInstance() {
        Habitat localInstance = instance;
        if (localInstance == null) {
            synchronized (Habitat.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new Habitat();
                }
            }
        }
        return localInstance;
    }


    private void clearList() {
        list.forEach((tmp) -> mainController.getPane().getChildren().remove(tmp.getImageView()));
        list.clear();
    }

    private void createDog() throws FileNotFoundException {
        Random random = new Random();
        Dog dog = new Dog(
                random.nextInt((int) mainController.getPane().getWidth() - 200),
                random.nextInt((int) mainController.getPane().getHeight() - 500),
                "src/main/resources/images/dog_image.png"
        );
        mainController.getPane().getChildren().add(dog.getImageView());
        list.add(dog);
    }

}