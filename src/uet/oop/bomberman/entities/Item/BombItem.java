package uet.oop.bomberman.entities.Item;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class BombItem extends  Item {
    public BombItem(int x, int y, Image img) {
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
