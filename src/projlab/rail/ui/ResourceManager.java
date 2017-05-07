package projlab.rail.ui;

import com.sun.corba.se.spi.ior.Writeable;
import projlab.rail.GameEngine;
import projlab.rail.logic.Color;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static projlab.rail.ui.Direction.*;

public class ResourceManager {

    private static final BufferedImage[] MAPS = new BufferedImage[GameEngine.MAP_COUNT];

    private static final BufferedImage RAIL_STRAIGHT_VERT;
    private static final BufferedImage RAIL_STRAIGHT_HORI;
    private static final BufferedImage[] RAIL_CURVES = new BufferedImage[4];
    private static final BufferedImage CROSS_RAIL;
    private static final BufferedImage[] SWITCH_AS = new BufferedImage[4];
    private static final BufferedImage[] SWITCH_BS = new BufferedImage[4];
    private static final BufferedImage[] OPEN_TUNNELS = new BufferedImage[4];
    private static final BufferedImage[] CLOSED_TUNNELS = new BufferedImage[4];

    private static final Map<Color, BufferedImage[]> CARS = new HashMap<>();
    private static final BufferedImage[] LOCOMOTIVES = new BufferedImage[8];

    static {
        for (int i = 0; i < GameEngine.MAP_COUNT; i++) {
            MAPS[i] = read("/map/" + (i + 1) + ".png");
        }

        RAIL_STRAIGHT_HORI = read("/rail_straight.png");
        RAIL_STRAIGHT_VERT = rotate(RAIL_STRAIGHT_HORI, 2);

        readRotated(RAIL_CURVES, "/rail_curve.png", true);
        CROSS_RAIL = read("/crossrail.png");
        readRotated(SWITCH_AS, "/switch_a.png", true);
        readRotated(SWITCH_BS, "/switch_b.png", true);
        readRotated(OPEN_TUNNELS, "/tunnel_open.png", true);
        readRotated(CLOSED_TUNNELS, "/tunnel_closed.png", true);
        readRotated(LOCOMOTIVES, "/train/locomotive.png", false);

        for (Color c : Color.values()) {
            BufferedImage[] cars = new BufferedImage[8];
            readRotated(cars, "/train/" + c.name().toLowerCase() + ".png", false);
            CARS.put(c, cars);
        }

        BufferedImage[] cars = new BufferedImage[8];
        readRotated(cars, "/train/coal.png", false);
        CARS.put(null, cars);
    }

    /** force load static initializer **/
    public static void init() {}

    private static void readRotated(BufferedImage[] array, String path, boolean quarters) {
        array[0] = read(path);
        for (int i = 1; i < (quarters ? 4 : 8); i++) {
            array[i] = rotate(array[0], (quarters ? 2 * i : i));
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

    public static BufferedImage mergeImages(BufferedImage a, BufferedImage b) {
        BufferedImage combined = new BufferedImage(a.getWidth(), a.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics g = combined.getGraphics();
        g.drawImage(a, 0, 0, null);
        g.drawImage(b, 0, 0, null);
        return combined;
    }

    private ResourceManager() {
        throw new AssertionError("Static class!");
    }

    private static BufferedImage rotate(BufferedImage image, int eighth) {
        AffineTransform at = AffineTransform.getRotateInstance(eighth * Math.PI / 4.0, image.getWidth() / 2.0, image.getHeight() / 2.0);
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

    private static BufferedImage shiftMoving(BufferedImage original, Direction from, Direction to) {
        int shiftX = original.getWidth() / 8;
        int shiftY = shiftX;

        if (from == NORTH || to == NORTH) {
            shiftY *= -1;
        }
        if (from == WEST || to == WEST) {
            shiftX *= -1;
        }

        BufferedImage shifted = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = shifted.createGraphics();
        g.drawImage(original, shiftX, shiftY, null);
        return shifted;
    }

    private static BufferedImage addPassenger(BufferedImage original, boolean hasPassenger) {
        if (!hasPassenger) return original;

        BufferedImage ret = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        ret.getGraphics().drawImage(original, 0, 0, null);
        ret.getGraphics().setColor(java.awt.Color.WHITE);
        int rad = original.getWidth() / 10;
        int pos = (original.getWidth() - rad) / 2;
        ret.getGraphics().fillOval(pos, pos, rad, rad);
        return ret;
    }

    public static BufferedImage getMap(int map) {
        return MAPS[map];
    }

    public static BufferedImage getRail(Direction aDir, Direction bDir) {
        if (aDir.value > bDir.value) {
            Direction temp = aDir;
            aDir = bDir;
            bDir = temp;
        }

        if (aDir == WEST && bDir == EAST) {
            return RAIL_STRAIGHT_HORI;
        } else if (aDir == NORTH && bDir == SOUTH) {
            return RAIL_STRAIGHT_VERT;
        } else if (aDir == WEST && bDir == SOUTH) {
            return RAIL_CURVES[3];
        } else {
            return RAIL_CURVES[aDir.value];
        }
    }

    public static BufferedImage getCrossRail() {
        return CROSS_RAIL;
    }

    public static BufferedImage getSwitch(Direction inDir, boolean isAActive) {
        int val = inDir.value;
        return isAActive ? SWITCH_AS[val] : SWITCH_BS[val];
    }

    public static BufferedImage getStation(Direction aDir, Direction bDir, Color color, EnumSet<Color> passengers) {
        BufferedImage rail = getRail(aDir, bDir);
        BufferedImage ret = new BufferedImage(rail.getWidth(), rail.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics graphics = ret.getGraphics();
        graphics.drawImage(rail, 0, 0, rail.getWidth(), rail.getHeight(), null);
        graphics.setColor(java.awt.Color.BLACK);

        boolean horizontal = aDir == WEST || aDir == EAST;
        if (horizontal) {
            graphics.fillRect(10, 10, rail.getWidth() - 10, 100);
            graphics.setColor(color.value);
            graphics.fillRect(20, 20, rail.getWidth() - 30, 80);

        } else {
            graphics.fillRect(10, 10, 100, rail.getHeight() - 10);
            graphics.setColor(color.value);
            graphics.fillRect(20, 20, 80, rail.getHeight() - 30);
        }

        if (passengers.size() > 0) {
            int diam = ret.getWidth() / 10;
            int spacing = ret.getWidth() / 10;
            int movingAxisPos = (ret.getWidth() - passengers.size() * diam - (passengers.size() - 1) * spacing) / 2;
            int staticPos = 10 + (100 - diam) / 2;

            for (Color pc : passengers) {
                int x = horizontal ? movingAxisPos : staticPos;
                int y = !horizontal ? movingAxisPos : staticPos;
                graphics.setColor(pc.value);
                graphics.fillOval(x, y, diam, diam);
                movingAxisPos += diam + spacing;
            }

        }

        return ret;
    }

    public static BufferedImage getTunnel(Direction visibleDir, boolean isActive) {
        BufferedImage[] array = isActive ? OPEN_TUNNELS : CLOSED_TUNNELS;
        return array[visibleDir.value];
    }

    public static BufferedImage getMoving(BufferedImage[] array, Direction fromDir, Direction toDir, boolean hasPassengers) {
        if (fromDir == toDir.invert()) {
            return addPassenger(array[fromDir.value * 2], hasPassengers);
        } else {
            BufferedImage rotated;
            if (fromDir.value == toDir.value + 1 || (fromDir.value == 0 && toDir.value == 3)) {
                rotated = array[fromDir.value * 2 + 1];
            } else {
                rotated = array[fromDir == WEST ? 7 : fromDir.value * 2 - 1];
            }
            return shiftMoving(addPassenger(rotated, hasPassengers), fromDir, toDir);
        }

    }

    public static BufferedImage getLocomotive(Direction fromDir, Direction toDir) {
        return getMoving(LOCOMOTIVES, fromDir, toDir, false);
    }

    public static BufferedImage getCar(Direction fromDir, Direction toDir, Color color, boolean hasPassengers) {
        return getMoving(CARS.get(color), fromDir, toDir, hasPassengers);
    }

}