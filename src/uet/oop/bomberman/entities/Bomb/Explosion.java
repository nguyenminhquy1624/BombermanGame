package uet.oop.bomberman.entities.Bomb;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

public class Explosion extends Entity {
    private boolean last;
    private int direction;

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Explosion(int x, int y, boolean last, int direction) {
        this.x = x;
        this.y = y;
        this.last = last;
        this.direction = direction;
        switch (this.direction) {
            case 1: {
                img = last ? Sprite.explosion_horizontal_left_last2.getFxImage() : Sprite.explosion_horizontal2.getFxImage();
            }
            break;
            case 2: {
                img = last ? Sprite.explosion_horizontal_right_last2.getFxImage() : Sprite.explosion_horizontal2.getFxImage();

            }
            break;
            case 3: {
                img = last ? Sprite.explosion_vertical_top_last2.getFxImage() : Sprite.explosion_vertical2.getFxImage();

            }
            break;
            case 4: {
                img = last ? Sprite.explosion_vertical_down_last2.getFxImage() : Sprite.explosion_vertical2.getFxImage();

            }
            break;
            default:
                break;
        }
    }

    public void update(int time) {
        if(Board.getPlayer().getFpass() <= 0)
            Board.getPlayer().collidetoDie(this);

        int sizeEnemy = BombermanGame.board.getEnemies().size();
        for (int i = 0; i < sizeEnemy; i++) {
            BombermanGame.board.getEnemies().get(i).collidetodie(this);
        }
        switch (this.direction) {
            case 1: {
                img = last ?
                        Sprite.movingSprite(Sprite.explosion_horizontal_left_last,Sprite.explosion_horizontal_left_last1, Sprite.explosion_horizontal_left_last2, time, 30).getFxImage():
                        Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, time, 30).getFxImage();
            }
            break;
            case 2: {
                img = last ?
                        Sprite.movingSprite(Sprite.explosion_horizontal_right_last,Sprite.explosion_horizontal_right_last1, Sprite.explosion_horizontal_right_last2, time, 30).getFxImage():
                        Sprite.movingSprite(Sprite.explosion_horizontal,Sprite.explosion_horizontal1, Sprite.explosion_horizontal2, time, 30).getFxImage();
            }
            break;
            case 3: {
                img = last ?
                        Sprite.movingSprite(Sprite.explosion_vertical_top_last,Sprite.explosion_vertical_top_last1, Sprite.explosion_vertical_top_last2, time, 30).getFxImage():
                        Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1, Sprite.explosion_vertical2, time, 30).getFxImage();
            }
            break;
            case 4: {
                img = last ?
                        Sprite.movingSprite(Sprite.explosion_vertical_down_last,Sprite.explosion_vertical_down_last1, Sprite.explosion_vertical_down_last2, time, 30).getFxImage():
                        Sprite.movingSprite(Sprite.explosion_vertical,Sprite.explosion_vertical1, Sprite.explosion_vertical2, time, 30).getFxImage();
            }
            break;
            default:
                break;
        }
    }

    @Override
    public void update() {

    }

    public void render(GraphicsContext gc) {
        super.render(gc);
    }
}
