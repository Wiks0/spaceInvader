package proj.sprite;

import proj.sprite.nastavitve;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Igralec extends objektIgre {

    private int width;

    public Igralec() {

        ustvariIgralca();
    }

    private void ustvariIgralca() {

        var playerImg = "src/images/rsz_heroship.png";
        var ii = new ImageIcon(playerImg);

        width = ii.getImage().getWidth(null);
        setImage(ii.getImage());

        int START_X = 270;
        setX(START_X);

        int START_Y = nastavitve.GROUND - nastavitve.PLAYER_HEIGHT;
        setY(START_Y);
    }

    public void act() {

        x += dx;

        if (x <= 3) {

            x = 3;
        }

        if (x >= nastavitve.BOARD_WIDTH - 2 * width) {

            x = nastavitve.BOARD_WIDTH - 2 * width;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = -5;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 5;
        }
    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {

            dx = 0;
        }

        if (key == KeyEvent.VK_RIGHT) {

            dx = 0;
        }
    }
}
