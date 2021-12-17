package uet.oop.bomberman;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.Bomb.Bomb;
import uet.oop.bomberman.entities.Enemy.Balloon;
import uet.oop.bomberman.entities.Enemy.Doll;
import uet.oop.bomberman.entities.Enemy.Enemy;
import uet.oop.bomberman.entities.Enemy.Kodoria;
import uet.oop.bomberman.entities.Enemy.Oneal;
import uet.oop.bomberman.entities.Item.*;
import uet.oop.bomberman.graphics.Sprite;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Board {
	public static final int default_level_time = 240*60;
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;
    public static final int MAX_LEVEL = 3;
    public static int countDownTime = default_level_time;
    public static int score = 0;

    private static ArrayList<Entity> entities = new ArrayList<Entity>(); //Brick
    private static ArrayList<Entity> stillObjects = new ArrayList<Entity>(); // Grass and Wall
    private static ArrayList<Enemy> enemies = new ArrayList<Enemy>();//Enemy
    private static ArrayList<Item> items = new ArrayList<Item>();
    private static Bomber player;
    private int level;

    public static char[][] map;
    private static boolean play;
    public boolean validate(int x, int y) {
        return ((x >= 0 && y >= 0) && (x < WIDTH * Sprite.SCALED_SIZE && y < HEIGHT * Sprite.SCALED_SIZE));
    }
    public Board() {
       
        player = new Bomber(1, 1, Sprite.player_right.getFxImage(),2);
        level = 1;
        play = true;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void removeItemAt(int x, int y) {
        if(validate(x, y)) {

            for (int i = 0; i < items.size(); i++) {
                Entity temp = items.get(i);
                if ((temp.getX() <= x && temp.getY() <= y) &&
                        (temp.getX() + Sprite.SCALED_SIZE > x && temp.getY() + Sprite.SCALED_SIZE > y)) {
                    items.remove(i);
                    break;
                }
            }
        }
    }

    public ArrayList<Item> getItems() {
        return items;
    }
    public ArrayList<Entity> getEntities() {
        return entities;
    }
    public void addEntity(Entity object) {
        entities.add(object);
    }
//    public void addBomb(Bomb bomb) {
//        bombs.add(bomb);
//    }
//
//    public void removeBombAt(int x, int y) {
//        if(validate(x, y)) {
//            for (int i = 0; i < bombs.size(); i++) {
//                Bomb temp = bombs.get(i);
//                if (temp.getX() == x && temp.getY() == y) {
//                    bombs.remove(i);
//                    break;
//                }
//            }
//        }
//    }
    public Entity getEntityAt(int x, int y) {
        if(validate(x, y)) {

            for (int i = 0; i < entities.size(); i++) {
                Entity temp = entities.get(i);
                if ((temp.getX() <= x && temp.getY() <= y) &&
                        (temp.getX() + Sprite.SCALED_SIZE > x && temp.getY() + Sprite.SCALED_SIZE > y)) {
                    return temp;
                }
            }
        }
        return null;
    }
public Entity getstillObjectAt(int x, int y) {
        if(validate(x, y)) {

            for (int i = 0; i < stillObjects.size(); i++) {
                Entity temp = stillObjects.get(i);
                if ((temp.getX() <= x && temp.getY() <= y) &&
                        (temp.getX() + Sprite.SCALED_SIZE > x && temp.getY() + Sprite.SCALED_SIZE > y)) {
                    return temp;
                }
            }
        }
        return null;
    }
    public static ArrayList<Entity> getStillObject() {
        return stillObjects;
    }
//    public Bomb getBombtAt(int x, int y) {
//        if(validate(x, y)) {
//
//            for (int i = 0; i < bombs.size(); i++) {
//                Bomb temp = bombs.get(i);
//                if ((temp.getX() <= x && temp.getY() <= y) &&
//                        (temp.getX() + Sprite.SCALED_SIZE > x && temp.getY() + Sprite.SCALED_SIZE > y)) {
//                    return temp;
//                }
//            }
//        }
//        return null;
//    }
    public void removeEntityAt(int x, int y) {
        if(validate(x, y)) {
            for (int i = 0; i < entities.size(); i++) {
                Entity temp = entities.get(i);
                if (temp.getX() == x && temp.getY() == y) {
                    entities.remove(i);
                    break;
                }
            }
        }
    }

//    public ArrayList<Bomb> getBombs() {
//        return bombs;
//    }


    public  ArrayList<Enemy> getEnemies() {
        return enemies;
    }
    public static void removeEnemyAt(int x, int y) {
        int n = enemies.size();
        for(int i = 0; i  < n; i++) {
            if(x == enemies.get(i).getX() && y == enemies.get(i).getY()) {
                enemies.remove(i);
                break;
            }
        }
    }
    public static Bomber getPlayer() {
        return player;
    }

    public  void setPlay(boolean isplay) {
       play = isplay;
    }

    public  boolean isPlay() {
        return play;
    }

    public void change_level(int level) {
        stillObjects.clear();
        entities.clear();
        enemies.clear();
        items.clear();
        this.level = level;
        try {
            loadlevel();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
    public int CountDown() {
        countDownTime--;
        return countDownTime;
    }
    public void loadlevel() throws FileNotFoundException {
        String path = "res/levels/Level" + String.valueOf(level) + ".txt";
        File file = new File(path);
        map = new char[HEIGHT][WIDTH];
        int row = 0;
        Scanner scanner = new Scanner(file);
        while (scanner.hasNextLine()) {
            String tmp = scanner.nextLine();
            for (int i = 0; i < tmp.length(); i++) {

                map[row][i] = tmp.charAt(i);
            }
            row++;
        }
        for(int i = 0; i < 13; i++) {
            for(int j = 0; j < 31; j++) {
//                if(map[i][j] == '#') {
//                    Wall wall = new Wall(j, i, Sprite.wall.getFxImage());
//                    stillObjects.add(wall);
//                } else {
//                    Grass grass = new Grass(j, i, Sprite.grass.getFxImage());
//                    stillObjects.add(grass);
//                }
                switch (map[i][j]) {
                    case 'p' :{
                        map[i][j] = ' ';
                        player.setX(j * Sprite.SCALED_SIZE);
                        player.setY(i * Sprite.SCALED_SIZE);
                    }
                    break;

                    case '1': {
                        map[i][j] = ' ';
                        Enemy enemy = new Balloon(j, i, Sprite.balloom_left1.getFxImage(), 1);
                        enemies.add(enemy);
                    }
                    break;

                    case '2': {
                        map[i][j] = ' ';
                        Enemy enemy = new Oneal(j, i, Sprite.oneal_right1.getFxImage(), 1);
                        enemies.add(enemy);
                    }
                    break;

                    case '3': {
                        map[i][j] = ' ';
                        Enemy enemy = new Doll(j, i, Sprite.doll_left1.getFxImage(), 1);
                        enemies.add(enemy);
                    }
                    break;

                    case '4': {
                        map[i][j] = ' ';
                        Enemy enemy = new Kodoria(j, i, Sprite.kondoria_left1.getFxImage(), 1);
                        enemies.add(enemy);
                    }
                    break;

                    case '#' :{
                        Wall wall = new Wall(j, i, Sprite.wall.getFxImage());
                        stillObjects.add(wall);
                    }
                    break;

                    case '*': {
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), null);
                        entities.add(brick);
                    }
                    break;

                    case 'x': {
                        map[i][j] = '*';
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), new Portal(j,i, Sprite.portal.getFxImage()));
                        entities.add(brick);
                    }
                    break;

                    case 'b': {
                        map[i][j] = '*';
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), new BombItem(j,i, Sprite.powerup_bombs.getFxImage()));
                        entities.add(brick);
                    }
                    break;

                    case 'f': {
                        map[i][j] = '*';
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), new FlameItem(j,i, Sprite.powerup_flames.getFxImage()));
                        entities.add(brick);
                    }
                    break;
case 's': {
                        map[i][j] = '*';
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), new SpeedItem(j,i, Sprite.powerup_speed.getFxImage()));
                        entities.add(brick);
                    }
                    break;

                    case 'w': {
                        map[i][j] = '*';
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), new WallPass(j,i, Sprite.powerup_wallpass.getFxImage()));
                        entities.add(brick);
                    }
                    break;

                    case 'h': {
                        map[i][j] = '*';
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), new HeartItem(j,i, Sprite.powerup_detonator.getFxImage()));
                        entities.add(brick);
                    }
                    break;

                    case 'F': {
                        map[i][j] = '*';
                        Brick brick= new Brick(j, i, Sprite.brick.getFxImage(), new FlamePass(j,i, Sprite.powerup_flamepass.getFxImage()));
                        entities.add(brick);
                    }
                    break;

                    default:
                        break;
                }
            }
        }
    }

    public void render() {
        BombermanGame.gc.clearRect(0,32,BombermanGame.canvas.getWidth(), BombermanGame.canvas.getHeight());
        BombermanGame.gc.setFill(Color.GREEN);
        BombermanGame.gc.fillRect(0,32,BombermanGame.canvas.getWidth(),BombermanGame.canvas.getHeight());
//        BombermanGame.gc.setFill(Color.BLACK);
//        BombermanGame.gcforplayer.fillRect(0,0,BombermanGame.canvas.getWidth(),Sprite.DEFAULT_SIZE);
        Board.stillObjects.forEach(g -> g.render(BombermanGame.gc));
        Board.entities.forEach(g -> g.render(BombermanGame.gc));
        Board.enemies.forEach(g -> g.render(BombermanGame.gc));
        Board.items.forEach(g -> g.render(BombermanGame.gc));
        player.render(BombermanGame.gc);
    }

    public void update() {
        for(int i = 0; i < stillObjects.size(); i++) {
            stillObjects.get(i).update();
        }
        for(int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        for(int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
        for(int i = 0; i < items.size(); i++) {
            items.get(i).update();
        }
        player.update();


    }
}
