/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.utils;

import com.neoterux.proyecto2p.model.Point;
import com.neoterux.proyecto2p.model.shape.Mark;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 *
 * @author luis_
 */
public class ThreadMapa implements Runnable {

    Thread t;
    Label texto;
    StackPane area_interactiva;
    Point c_pos;
    double x_offset;
    double y_offset;
    Button btn_registrar;

    public ThreadMapa(Label texto, StackPane area_interactiva, Point c_pos, double x_offset, double y_offset, Button btn_registrar) {
        this.texto = texto;
        this.area_interactiva = area_interactiva;
        this.c_pos = c_pos;
        this.x_offset = x_offset;
        this.y_offset = y_offset;
        this.btn_registrar = btn_registrar;
        btn_registrar.setDisable(true);
        t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        Counter contador = new Counter(0);
        var matches = Point.loadPoints().stream()
                .filter(point -> Point.distancia(point, c_pos) <= 100)
                .peek(point -> {
                    try {
                        var infected = new Mark("rgb(0,50,200)").getPath();
                        infected.setTranslateX(point.getX() - x_offset);
                        infected.setTranslateY(point.getY() - y_offset);
                        Platform.runLater(() -> {
                            area_interactiva.getChildren().add(infected);
                            contador.step();
                            texto.setText("Se han encontrado " + contador.toString() + " personas cercanas a tu ubicacion que han registrado positivos");

                        });
                        
                        Thread.sleep((long) (Math.random() * 1));
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }).count();
        Platform.runLater(() -> {
            btn_registrar.setDisable(false);
        });
    }
}