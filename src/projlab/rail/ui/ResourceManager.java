package projlab.rail.ui;

import projlab.rail.logic.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static projlab.rail.ui.Direction.*;

public class ResourceManager {

    private static final BufferedImage RAIL_STRAIGHT_VERT;
    private static final BufferedImage RAIL_STRAIGHT_HORI;
    private static final BufferedImage[] RAIL_CURVES = new BufferedImage[4];

    static {
        RAIL_STRAIGHT_HORI = read("/rail_straight.png");
        RAIL_STRAIGHT_VERT = rotate(RAIL_STRAIGHT_HORI, 1);
        RAIL_CURVES[0] = read("/rail_curve.png");
        for (int i = 1; i < 4; i++) {
            RAIL_CURVES[i] = rotate(RAIL_CURVES[0], i);
        }
    }

    private static BufferedImage read(String path) {
        try {
            return ImageIO.read(ResourceManager.class.getResource(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private ResourceManager() {
        throw new AssertionError("Static class!");
    }

    private static BufferedImage rotate(BufferedImage image, int quarter) {
        AffineTransform at = AffineTransform.getRotateInstance(quarter * Math.PI / 2.0, image.getWidth() / 2.0, image.getHeight() / 2.0);
        return transform(image, at);
    }

    private static BufferedImage transform(BufferedImage image, AffineTransform at) {
        BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = newImage.createGraphics();
        g.transform(at);
        g.drawImage(image, 0, 0, null);
        g.dispose();
        return newImage;
    }

    public static BufferedImage getRail(Direction aDir, Direction bDir) {
        if (aDir.value > bDir.value) {
            Direction temp = aDir;
            aDir = bDir;
            bDir = temp;
        }

        if (aDir == EAST && bDir == WEST) {
            return RAIL_STRAIGHT_HORI;
        } else if (aDir == NORTH && bDir == SOUTH) {
            return RAIL_STRAIGHT_VERT;
        } else {
            return RAIL_CURVES[aDir.value];
        }
    }

    public static BufferedImage getSwitch(Direction inDir, Direction aDir, Direction bDir, boolean isAActive) {
        return null;
    }

    public static BufferedImage getStation(Direction aDir, Direction bDir, Color color) {
        return null;
    }

    public static BufferedImage getTunnel(Direction visibleDir, boolean isActive) {
        return null;
    }

    public static BufferedImage getLocomotive(Direction fromDir, Direction toDir) {
        return null;
    }

    public static BufferedImage getCar(Direction fromDir, Direction toDir, Color color, boolean hasPassengers) {
        return null;
    }

}