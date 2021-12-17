package uet.oop.bomberman.entities.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.Board;
import uet.oop.bomberman.BombermanGame;

import java.util.HashSet;

public class FlamePass extends Item{

    public FlamePass(int x, int y, Image img) {
        super(x, y, img);
    }

    public void update() {
        if(active) {
            remove = true;
            BombermanGame.board.removeItemAt(this.x, this.y);
        }
    }
    public void render(GraphicsContext gc) {
        if(!remove) super.render(gc);
    }
}
