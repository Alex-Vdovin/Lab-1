package com.lab1.lab1.model;

import com.lab1.lab1.interfaces.IBehaviour;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public abstract class Pet implements IBehaviour {
    private long n;
    private double p;
    private ImageView imageView;

    public long getN() {
        return n;
    }

    public void setN(long n) {
        this.n = n;
    }

    public double getP() {
        return p;
    }

    public void setP(double p) {
        this.p = p;
    }
    public Pet(int x, int y, String path) throws FileNotFoundException {
        Image image = new Image(new FileInputStream(path));
        imageView = new ImageView(image);
        imageView.setX(x);
        imageView.setY(y);
        imageView.setFitHeight(300);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);
    }
    public ImageView getImageView() {
        return imageView;
    }

}
