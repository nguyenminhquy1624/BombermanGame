package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.IntStream;

public class ExplosionDirection extends Entity {

    private int direction;
    private List<Explosion> explosions;
    public ExplosionDirection(int x, int y, int direction,int max_radius) {
        this.x = x*32;
        this.y = y*32;
        this.direction = direction;
        explosions = new ArrayList<Explosion>();
        int n = radius(max_radius);
        if(n > 0) {
            int x1 = this.x;
            int y1 = this.y;
            int speed_X = 0;
            int speed_Y = 0;
            switch (direction) {
                case 1: {
                    speed_X = -32;
                    speed_Y = 0;
                }
                break;
                case 2: {
                    speed_X = 32;
                    speed_Y = 0;
                }
                break;
                case 3: {
                    speed_X = 0;
                    speed_Y = -32;
                }
                break;
                case 4: {
                    speed_X = 0;
                    speed_Y = 32;
                }
                break;
                default:
                    break;
            }
            for(int i = 1; i < n; i++) {
                explosions.add(new Explosion(x1+ i*speed_X, y1 + i*speed_Y, false, this.direction));
            }
            if(n > 0)
            explosions.add(new Explosion(x1+ n*speed_X, y1 + n*speed_Y, true, this.direction));
        }
    }
    public int radius(int r) {
        int radius = 0;
        int x1 = x/32;
        int y1 = y/32;
        while (radius < r) {
            if(direction == 1) x1--;
            if(direction == 2) x1++;
            if(direction == 3) y1--;
            if(direction == 4) y1++;
//            if(Board.map[y1][x1] == ' ') radius++;
//            if(Board.map[y1][x1] == '#') break;
//            if(Board.map[y1][x1] == 'B') break;
//            if(Board.map[y1][x1] != ' ' && Board.map[y1][x1] != '#') {
//                if(BombermanGame.board.getEntityAt(x*32, y*32) != null) {
//                    BombermanGame.board.getEntityAt(x*32, y*32).setRemove(true);
//                }
//                break;
//            }
            if(x1 >= 0 && y1 >= 0 && x1 < Board.WIDTH && y1 < Board.HEIGHT) {
                if(Board.map[y1][x1] == '#') break;
//                || Board.map[y1][x1] == 'x' || Board.map[y1][x1] == 'b'
//                        || Board.map[y1][x1] == 'f'|| Board.map[y1][x1] == 's'
                else if(Board.map[y1][x1] == '*') {
                    BombermanGame.board.getEntityAt(x1*32, y1*32).setRemove(true);
                    break;
                }
                else if(Board.map[y1][x1] == 'B') {
                    if(Board.getPlayer().getBombAt(x1*32, y1*32) != null) {
                        Board.getPlayer().getBombAt(x1*32, y1*32).setTimeToExplode(0);
                        break;
                    }
                }
//                if(Board.map[y1][x1] != '#' && Board.map[y1][x1] != '*' && Board.map[y1][x1] != 'B' && Board.map[y1][x1] != 'x'
//                        && Board.map[y1][x1] != 'b' && Board.map[y1][x1] != 'f' && Board.map[y1][x1] != 's')
                if(Board.map[y1][x1] == ' ')
               radius++;
            }
            else break;
        }
    return radius;
    }
    public int getsize() {
        return explosions.size();
    }
    public void createExplosion() {
//        int x1 =  this.x/32;
//        int y1 =  this.y/32;
//        for (int i = 0; i < explosions.size(); i++) {
//            boolean last = (i == explosions.size() - 1);
//            switch (direction) {
//                case 1:
//                    x1--;
//                    break;
//                case 2:
//                    x1++;
//                    break;
//                case 3:
//                    y1--;
//                    break;
//                case 4:
//                    y1++;
//                    break;
//            }
//            explosions.add(new Explosion(x1, y1,last,this.direction));
//        }
    }

    public void update(int time) {
        for (int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update(time);
        }
    }

    @Override
    public void render(GraphicsContext gc) {

        for (Explosion tmp: explosions) {
            tmp.render(gc);
        }
    }
    public String getdirection() {
        String ans = "";
        int n = explosions.size();
        for(int i = 0; i < n; i++) {
            ans = ans + String.valueOf(explosions.get(i).getDirection()) + " ";
        }
        return ans;
    }
    @Override
    public void update() {

    }

    public HashSet <String> getMask() {
        return super.getMask();
    }
}
