package dev.foltz;

import dev.foltz.cell.*;
import processing.core.PApplet;

import static dev.foltz.Quanta.WATER;
import static dev.foltz.Util.CELL_SIZE;
import static dev.foltz.Util.EMPTY_CELL;

public class BonsaiSketch extends PApplet {
    public static final int FPS = 24;
    public static BonsaiSketch INSTANCE;
    boolean mouseLeft = false;
    boolean mouseRight = false;
    public static final char KEY_RESET = 'r';
    public static final char KEY_WATER = 'w';
    public static final char KEY_DIRT = 'd';
    public static final char KEY_SAND = 's';
    public static final char KEY_RAIN = 'q';
    boolean keyWater = false;
    boolean keyDirt = false;
    boolean keySand = false;
    boolean keyRain = false;
    boolean toggleRain = false;

    public BonsaiSketch() {
        INSTANCE = this;
    }

    private World world;

    public void init() {
        randomSeed((int) random(1000));
        int w = displayWidth / CELL_SIZE / 2;
        int h = displayHeight / CELL_SIZE;
        world = new World(w, h);

        int seedStone = (int) random(1000);
        noiseSeed(seedStone);
        for (int i = 0; i < w; i++) {
            for (int j = 0; j < h; j++) {
                float p = j / (float) h;
                float v = noise(100 + i * 0.3f, 230 + j * 0.1f, (1-p) * 50f);
                float stoneFactor = v * p;
                if (stoneFactor > 0.5 || j > h - 5) {
                    world.setCell(i, j, new CellStone());
                }
            }
        }
    }

    @Override
    public void mousePressed() {
        if (mouseButton == LEFT) mouseLeft = true;
        else if (mouseButton == RIGHT) mouseRight = true;
    }

    @Override
    public void mouseReleased() {
        if (mouseButton == LEFT) mouseLeft = false;
        else if (mouseButton == RIGHT) mouseRight = false;
    }

    @Override
    public void keyPressed() {
        switch (key) {
            case KEY_RESET:
                init();
                break;
            case KEY_WATER:
                keyWater = true;
                break;
            case KEY_DIRT:
                keyDirt = true;
                break;
            case KEY_SAND:
                keySand = true;
                break;
            case KEY_RAIN:
                if (!keyRain) {
                    keyRain = true;
                    toggleRain = !toggleRain;
                    System.out.println("Toggling rain!");
                }
                break;
        }
    }

    @Override
    public void keyReleased() {
        switch (key) {
            case KEY_WATER:
                keyWater = false;
                break;
            case KEY_DIRT:
                keyDirt = false;
                break;
            case KEY_SAND:
                keySand = false;
                break;
            case KEY_RAIN:
                keyRain = false;
                break;
        }
    }

    @Override
    public void setup() {
        getSurface().setResizable(true);
        getSurface().setTitle("Bonsai");
        frameRate(FPS);

        init();
    }

    @Override
    public void draw() {
        if (mouseLeft) {
            int radius = 3;
            int mx = pmouseX;
            int my = pmouseY;
            int vx = mouseX - mx;
            int vy = mouseY - my;
            float totalDist = (float) Math.sqrt(vx*vx + vy*vy);
            int segcount = (int) (totalDist / radius) + 1;
            for (int seg = 0; seg < segcount; seg++) {
                float p = Util.map(seg, 0, segcount - 1, 0, 1);
                int segx = (int) (((mx + vx * p) - world.renderOffsetX) / CELL_SIZE);
                int segy = (int) (((my + vy * p) - world.renderOffsetY) / CELL_SIZE);
                if (segcount == 1) {
                    segx = (mx - world.renderOffsetX) / CELL_SIZE;
                    segy = (my - world.renderOffsetY) / CELL_SIZE;
                }
                for (int i = -radius; i <= radius; i++) {
                    for (int j = -radius; j <= radius; j++) {
                        int x = segx + i;
                        int y = segy + j;
                        int distsq = i*i + j*j;
                        if (distsq <= radius*radius) {
                            world.setCell(x, y, EMPTY_CELL);
                        }
                    }
                }
            }
        }
        else if (mouseRight) {
            int radius = 2;
            int mx = pmouseX;
            int my = pmouseY;
            int vx = mouseX - mx;
            int vy = mouseY - my;
            float totalDist = (float) Math.sqrt(vx*vx + vy*vy);
            int segcount = (int) (totalDist / radius) + 1;
            for (int seg = 0; seg < segcount; seg++) {
                float p = Util.map(seg, 0, segcount - 1, 0, 1);
                int segx = (int) (((mx + vx * p) - world.renderOffsetX) / CELL_SIZE);
                int segy = (int) (((my + vy * p) - world.renderOffsetY) / CELL_SIZE);
                if (segcount == 1) {
                    segx = (mx - world.renderOffsetX) / CELL_SIZE;
                    segy = (my - world.renderOffsetY) / CELL_SIZE;
                }
                for (int i = -radius; i <= radius; i++) {
                    for (int j = -radius; j <= radius; j++) {
                        int x = segx + i;
                        int y = segy + j;
                        int distsq = i*i + j*j;
                        if (distsq <= radius*radius) {
                            world.setCell(x, y, new CellStone());
                        }
                    }
                }
            }
        }
        else if (keyWater) {
            for (int i = -1; i <= 1; i++) {
                int x = (int) (mouseX - world.renderOffsetX) / CELL_SIZE + i;
                int y = (int) (mouseY - world.renderOffsetY) / CELL_SIZE;
                if (world.getCell(x, y).isEmpty()) {
                    world.setCell(x, y, new CellWater(5));
                }
            }
        }
        else if (keyDirt) {
            for (int i = -1; i <= 1; i++) {
                int x = (int) (mouseX - world.renderOffsetX) / CELL_SIZE + i;
                int y = (int) (mouseY - world.renderOffsetY) / CELL_SIZE;
                if (world.getCell(x + i, y).isEmpty()) {
                    world.setCell(x + i, y, new CellDirt());
                }
            }
        }
        else if (keySand) {
            for (int i = -1; i <= 1; i++) {
                int x = (int) (mouseX - world.renderOffsetX) / CELL_SIZE + i;
                int y = (int) (mouseY - world.renderOffsetY) / CELL_SIZE;
                if (world.getCell(x, y).isEmpty()) {
                    world.setCell(x, y, new CellSand());
                }
            }
        }

        // Rain
        if (toggleRain) {
            int raindropCount = Util.random(5, 15);
            for (int i = 0; i < raindropCount; i++) {
                world.setCell(Util.random(world.width), 0, new CellWater(Util.random(1, 5)));
            }
        }
        world.update();
        //System.out.println("Hi!");

        background(60, 40, 40);
        fill(255);
        text("FPS: " + frameRate, 24, 24);
        world.render(this);
    }

    @Override
    public void settings() {
        size((int) (displayWidth * 0.75f), (int) (displayHeight * 0.75f));
        noSmooth();
    }

    public static void main(String[] args) {
        String[] sketchArgs = { "dev.foltz.BonsaiSketch" };
        PApplet sketch = new BonsaiSketch();
        PApplet.runSketch(sketchArgs, sketch);
    }
}
