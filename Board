package proj.sprite;

import proj.sprite.Igralec;
import proj.sprite.Strel;
import proj.sprite.vesoljcek;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class Board extends JPanel {

    private Dimension d;
    private List<vesoljcek> aliens;
    private Igralec player;
    private Strel shot;
    
    private int smer = -2;
    private int deaths = 0;

    private boolean inGame = true;
    private String explImg = "src/images/explosion.png";
    private String message = "Game Over";

    private Timer timer;


    public Board() {

        initBoard();
        gameInit();
    }

    private void initBoard() {

        addKeyListener(new TAdapter());
        setFocusable(true);
        d = new Dimension(nastavitve.BOARD_WIDTH, nastavitve.BOARD_HEIGHT);
        setBackground(Color.BLACK);

        timer = new Timer(nastavitve.DELAY, new GameCycle());
        timer.start();

        gameInit();
    }


    private void gameInit() {

        aliens = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {

                var alien = new vesoljcek(nastavitve.ALIEN_INIT_X + 30 * j,
                        nastavitve.ALIEN_INIT_Y + 30 * i);
                aliens.add(alien);
            }
        }

        player = new Igralec();
        shot = new Strel();
    }

    private void drawAliens(Graphics g) {

        for (vesoljcek alien : aliens) {

            if (alien.isVisible()) {

                g.drawImage(alien.getImage(), alien.getX(), alien.getY(), nastavitve.ALIEN_WIDTH, nastavitve.ALIEN_HEIGHT, this);
            }

            if (alien.isDying()) {

                alien.die();
            }
        }
    }

    private void drawPlayer(Graphics g) {

        if (player.isVisible()) {

            g.drawImage(player.getImage(), player.getX(), player.getY(), nastavitve.PLAYER_WIDTH, nastavitve.PLAYER_HEIGHT, this);
        }

        if (player.isDying()) {

            player.die();
            inGame = false;
        }
    }

    private void drawStrel(Graphics g) {

        if (shot.isVisible()) {

            g.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
        }
    }

    private void drawBombing(Graphics g) {

        for (vesoljcek a : aliens) {

            vesoljcek.Bomb b = a.getBomb();

            if (!b.isDestroyed()) {

                g.drawImage(b.getImage(), b.getX(), b.getY(), this);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
    	final Color background = new Color(35,31,32);
        g.setColor(background);
        g.fillRect(0, 0, d.width, d.height);
        g.setColor(Color.green);

        if (inGame) {

            g.drawLine(0, nastavitve.GROUND,
                    nastavitve.BOARD_WIDTH, nastavitve.GROUND);

            drawAliens(g);
            drawPlayer(g);
            drawStrel(g);
            drawBombing(g);

        } else {

            if (timer.isRunning()) {
                timer.stop();
            }

            gameOver(g);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void gameOver(Graphics g) {

        g.setColor(Color.black);
        g.fillRect(0, 0, nastavitve.BOARD_WIDTH, nastavitve.BOARD_HEIGHT);

        g.setColor(new Color(0, 32, 48));
        g.fillRect(50, nastavitve.BOARD_WIDTH / 2 - 30, nastavitve.BOARD_WIDTH - 100, 50);
        g.setColor(Color.white);
        g.drawRect(50, nastavitve.BOARD_WIDTH / 2 - 30, nastavitve.BOARD_WIDTH - 100, 50);

        var small = new Font("Helvetica", Font.BOLD, 14);
        var fontMetrics = this.getFontMetrics(small);

        g.setColor(Color.white);
        g.setFont(small);
        g.drawString(message, (nastavitve.BOARD_WIDTH - fontMetrics.stringWidth(message)) / 2,
                nastavitve.BOARD_WIDTH / 2);
    }

    private void update() {

        if (deaths == nastavitve.NUMBER_OF_ALIENS_TO_DESTROY) {

            inGame = false;
            timer.stop();
            message = "Game won!";
        }

        // player
        player.act();

        // shot
        if (shot.isVisible()) {

            int shotX = shot.getX();
            int shotY = shot.getY();

            for (vesoljcek alien : aliens) {

                int alienX = alien.getX();
                int alienY = alien.getY();

                if (alien.isVisible() && shot.isVisible()) {
                    if (shotX >= (alienX)
                            && shotX <= (alienX + nastavitve.ALIEN_WIDTH)
                            && shotY >= (alienY)
                            && shotY <= (alienY + nastavitve.ALIEN_HEIGHT)) {

                        var ii = new ImageIcon(explImg);
                        alien.setImage(ii.getImage());
                        alien.setDying(true);
                        deaths++;
                        shot.die();
                    }
                }
            }

            int y = shot.getY();
            y -= 4;

            if (y < 0) {
                shot.die();
            } else {
                shot.setY(y);
            }
        }

        // aliens

        for (vesoljcek alien : aliens) {

            int x = alien.getX();

            if (x >= nastavitve.BOARD_WIDTH - 30 && smer != -2) {

                smer = -2;

                Iterator<vesoljcek> i1 = aliens.iterator();

                while (i1.hasNext()) {

                    vesoljcek a2 = i1.next();
                    a2.setY(a2.getY() + nastavitve.GO_DOWN);
                }
            }

            if (x <= 5 && smer != 2) {

                smer = 2;

                Iterator<vesoljcek> i2 = aliens.iterator();

                while (i2.hasNext()) {

                    vesoljcek a = i2.next();
                    a.setY(a.getY() + nastavitve.GO_DOWN);
                }
            }
        }

        Iterator<vesoljcek> it = aliens.iterator();

        while (it.hasNext()) {

            vesoljcek alien = it.next();

            if (alien.isVisible()) {

                int y = alien.getY();

                if (y > nastavitve.GROUND - nastavitve.ALIEN_HEIGHT) {
                    inGame = false;
                    message = "Invasion!";
                }

                alien.act(smer);
            }
        }

        // bombs
        var generator = new Random();

        for (vesoljcek alien : aliens) {

            int shot = generator.nextInt(15);
            vesoljcek.Bomb bomb = alien.getBomb();

            if (shot == nastavitve.CHANCE && alien.isVisible() && bomb.isDestroyed()) {

                bomb.setDestroyed(false);
                bomb.setX(alien.getX());
                bomb.setY(alien.getY());
            }

            int bombX = bomb.getX();
            int bombY = bomb.getY();
            int playerX = player.getX();
            int playerY = player.getY();

            if (player.isVisible() && !bomb.isDestroyed()) {

                if (bombX >= (playerX)
                        && bombX <= (playerX + nastavitve.PLAYER_WIDTH)
                        && bombY >= (playerY)
                        && bombY <= (playerY + nastavitve.PLAYER_HEIGHT)) {

                    var ii = new ImageIcon(explImg);
                    player.setImage(ii.getImage());
                    player.setDying(true);
                    bomb.setDestroyed(true);
                }
            }

            if (!bomb.isDestroyed()) {

                bomb.setY(bomb.getY() + 1);

                if (bomb.getY() >= nastavitve.GROUND - nastavitve.BOMB_HEIGHT) {

                    bomb.setDestroyed(true);
                }
            }
        }
    }

    private void doGameCycle() {

        update();
        repaint();
    }

    private class GameCycle implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            doGameCycle();
        }
    }

    private class TAdapter extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent e) {

            player.keyReleased(e);
        }

        @Override
        public void keyPressed(KeyEvent e) {

            player.keyPressed(e);

            int x = player.getX();
            int y = player.getY();

            int key = e.getKeyCode();

            if (key == KeyEvent.VK_SPACE) {

                if (inGame) {

                    if (!shot.isVisible()) {

                        shot = new Strel(x, y);
                    }
                }
            }
        }
    }
}
