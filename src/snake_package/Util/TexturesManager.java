package snake_package.Util;

import java.util.Map;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.HashMap;

public class TexturesManager {
	
    private Map<Integer, Image> levelBackgrounds;
    private Map<String, Image> textures;
    private Map<String, Image> menuBackgrounds;

    private TexturesManager() {
        loadLevelBackgrounds();
        loadTextures();
        loadMenuBackgrounds();
    }

 // Classe interna statica per gestire l'istanza Singleton
    private static class Holder {
        private static final TexturesManager INSTANCE = new TexturesManager();
    }

    // Metodo pubblico per accedere all'istanza Singleton
    public static TexturesManager getInstance() {
        return Holder.INSTANCE;
    }

    private void loadLevelBackgrounds() {
        levelBackgrounds = new HashMap<>();
        // Carica le immagini di sfondo per i livelli
        levelBackgrounds.put(1, loadImage("/assets/backgrounds/backgroundLevel1.png"));
        levelBackgrounds.put(2, loadImage("/assets/backgrounds/backgroundLevel2.png"));
        levelBackgrounds.put(3, loadImage("/assets/backgrounds/backgroundLevel3.png"));
    }

    private void loadTextures() {
        textures = new HashMap<>();
        textures.put("apple", loadImage("/assets/textures/Apple.png"));
        textures.put("snake_head", loadImage("/assets/textures/SnakeHead.png"));
        textures.put("obstacle", loadImage("/assets/textures/Obstacle.png"));
        textures.put("fireball", loadImage("/assets/textures/Fireball.png"));
        textures.put("trap", loadImage("/assets/textures/Trap.png"));
        textures.put("trapActivated", loadImage("/assets/textures/TrapActivated.png")); 
    }
    
    private void loadMenuBackgrounds() {
    	menuBackgrounds = new HashMap<>();
    	menuBackgrounds.put("menu", loadImage("/assets/backgrounds/backgroundMenu.png"));
    	menuBackgrounds.put("gameOver", loadImage("/assets/backgrounds/gameOverBackground.png"));
    	menuBackgrounds.put("youWin", loadImage("/assets/backgrounds/youWinBackground.png"));
    	
    	
    }

    private Image loadImage(String path) {
        try {
            return Toolkit.getDefaultToolkit().getImage(getClass().getResource(path));
        } catch (Exception e) {
            e.printStackTrace();
            return null; // In caso di errore di caricamento, restituisce null
        }
    }

    public Map<Integer, Image> getLevelBackgrounds() {
        return levelBackgrounds;
    }

    public Map<String, Image> getTextures() {
        return textures;
    }
    
    public Map<String, Image> getMenuBackgrounds(){
    	return menuBackgrounds;
    }
}