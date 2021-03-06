/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.neoterux.proyecto2p.model;

import com.neoterux.proyecto2p.App;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * <h1> Point </h1>
 * <p>
 * Clase que representa un punto 2D </p>
 *
 * @author neoterux
 */
public class Point implements Serializable {

    private static final long serialVersionUID = 222233334444l;

    private static final Logger logger = LogManager.getLogger(Point.class);
    /**
     * Lista de puntos cargados del archivo.
     */
    private static List<Point> points;

    /**
     * Coordenada x
     */
    private double x;
    /**
     * Coordenada y
     */
    private double y;

    /**
     * Crea un nuevo objeto punto con sus respectivas coordenadas.
     *
     * @param x coordenada x.
     * @param y coordenada y.
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * obtiene la coordenada x del punto
     *
     * @return coordenada x.
     */
    public double getX() {
        return this.x;
    }

    /**
     * Obtiene la coordenada y del punto.
     *
     * @return coordenada y.
     */
    public double getY() {
        return this.y;
    }

    /**
     * Coloca la coordenada x
     *
     * @param x coordenada x
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Coloca la coordenada y
     *
     * @param y coordenada y
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * <p>
     * Calcula la distancia euclidiana entre 2 puntos. </p>
     *
     * @param p0 punto inicial
     * @param pf punto final
     * @return distancia entre 2 puntos.
     */
    public static double distancia(Point p0, Point pf) {
        return Math.sqrt(Math.pow((pf.x - p0.x), 2) + Math.pow((pf.y - p0.y), 2));
    }

    /**
     * Carga los puntos del archivo lugares.txt
     *
     * @return lista con los puntos parseados del archivo.
     */
    public static List<Point> loadPoints() {
        if (points == null) {
            var plist = new ArrayList<Point>();
            logger.info("reading lugares.txt");
            var file = Paths.get(App.FILES_PATH.toString(), "lugares.txt").toFile();
            
            try (var reader = new BufferedReader(new FileReader(file))) {
                reader.lines()
                        .map(it -> it.split("-"))
                        .filter(it -> it.length == 2)
                        .map(par -> new Point(Double.parseDouble(par[0]), Double.parseDouble(par[1])))
                        .forEach(plist::add);
                logger.info("lugares.txt successfully readed");
            } catch (FileNotFoundException fnf) {
                logger.error("archivo lugares.txt no se encuentra en la capeta data");
                new Alert(Alert.AlertType.ERROR, "Archivo lugares.txt no se encuentra en la carpeta data, cerrando.").showAndWait();
                Platform.exit();
            } catch (IOException ioe) {
                logger.error("IOException ocured when trying to read lugares.txt", ioe);
            }
            points = plist;
        }
        return points;
    }
}
