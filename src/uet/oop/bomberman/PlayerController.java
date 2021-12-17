package uet.oop.bomberman;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.MovingEntity;
import uet.oop.bomberman.graphics.Sprite;

public class PlayerController {

    public boolean left, right, up, down, space, x;
    public PlayerController() {
        left = false;
        right = false;
        up = false;
        down = false;
        space = false;
        x = false;
    }
    public void control(Scene scene) {
        if(BombermanGame.board.isPlay()) {
            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case LEFT : {
                       this.left = true;
                    }
                    break;
                    case RIGHT: {

                        this.right = true;
                    }
                    break;
                    case UP: {
                        this.up = true;
                    }
                    break;
                    case DOWN: {

                        this.down = true;
                    }
                    break;
                    case SPACE: {
                        this.space = true;
                    }
                    break;
                    case X: {
                        this.x = true;
                    }
                    break;
                    default:
                        break;
                }
            });
            scene.setOnKeyReleased(event -> {
                switch (event.getCode()) {
                    case LEFT: {
                        this.left = false;
                        Board.getPlayer().setImg(Sprite.player_left.getFxImage());
                        Board.getPlayer().setLeft(0);
                    }
                    break;
                    case RIGHT: {
                        this.right = false;
                        Board.getPlayer().setImg(Sprite.player_right.getFxImage());
                        Board.getPlayer().setRight(0);
                    }
                    break;
                    case UP: {
                        this.up = false;
                        Board.getPlayer().setUp(0);
                    }
                    break;
                    case DOWN: {
                        this.down = false;
                        Board.getPlayer().setDown(0);
                    }
                    break;
                    case SPACE: {
                        this.space = false;
                    }
                    break;
                    case X: {
                        this.x = false;
                    }
                    default:
                        break;
                }
            });
        }
    }
}
