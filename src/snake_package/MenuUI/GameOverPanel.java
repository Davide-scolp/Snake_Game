package snake_package.MenuUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import snake_package.Core.GameManager;
import snake_package.Util.TexturesManager;

public class GameOverPanel extends JPanel {
    private Image gameOverBackgroundImage;
    private TexturesManager texturesManager;

    public GameOverPanel(GameManager gameManager, boolean playerWon) {
    	this.texturesManager = TexturesManager.getInstance();
        setLayout(null);
        drawGameOverPanel(playerWon);
        addOption("Ricomincia Partita", 160, 200, e -> gameManager.restartGame());
        addOption("Torna al Menu", 160, 250, e -> gameManager.returnToMenu());
    }
    
    private void drawGameOverPanel(boolean playerWon) {
    	if (playerWon) {
    		drawPanel("You win!");
    	}else {
	    	drawPanel("Game over");
    	}
    	
    	try {
    		if (playerWon) {
    			gameOverBackgroundImage = texturesManager.getMenuBackgrounds().get("youWin");
    		} else {
    			gameOverBackgroundImage = texturesManager.getMenuBackgrounds().get("gameOver");
    		}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void drawPanel(String WorL) {
    	setLayout(null);
        JLabel gameOverLabel = new JLabel(WorL);
        gameOverLabel.setFont(new Font("Arial", Font.BOLD, 48));
        gameOverLabel.setForeground(Color.RED);
        gameOverLabel.setBounds(180, 100, 300, 100);
        gameOverLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(gameOverLabel);
    }

    private void addOption(String text, int x, int y,ActionListener actionListener) {
        JLabel optionLabel = new JLabel(text);
        optionLabel.setFont(new Font("Arial", Font.BOLD, 24));
        optionLabel.setForeground(Color.BLACK);
        optionLabel.setBounds(x, y, 300, 40);
        optionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        optionLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        optionLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                actionListener.actionPerformed(null);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                optionLabel.setFont(new Font("Arial", Font.BOLD, 28));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                optionLabel.setFont(new Font("Arial", Font.BOLD, 24));
            }
        });

        add(optionLabel);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameOverBackgroundImage != null) {
            g.drawImage(gameOverBackgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    
}