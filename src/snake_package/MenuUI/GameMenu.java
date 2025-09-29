package snake_package.MenuUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import snake_package.Core.GameManager;
import snake_package.Util.SoundManager;
import snake_package.Util.TexturesManager;

public class GameMenu extends JPanel {
    private GameManager gameManager;
    private SoundManager soundManager;
    private TexturesManager texturesManager;
    private Image backgroundImage;
    
    public GameMenu(GameManager gameManager) {
        this.gameManager = gameManager;
        this.soundManager = SoundManager.getInstance();
        this.texturesManager = TexturesManager.getInstance();
        setMenu();
    }
    
    private void setMenu() {
    	// Usa GridBagLayout per centrare i bottoni
        setLayout(new GridBagLayout());  
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;  
        gbc.gridy = GridBagConstraints.RELATIVE;  
        gbc.insets = new Insets(10, 0, 10, 0);  
        gbc.anchor = GridBagConstraints.CENTER;  
        gbc.fill = GridBagConstraints.NONE;  

        // Bottone per avviare il gioco
        JButton startButton = createMenuButton("Nuova partita", e -> gameManager.startGame());
        add(startButton, gbc);

        // Bottone per opzioni audio
        JButton optionsButton = createMenuButton("Opzioni audio", e -> showAudioOptions());
        add(optionsButton, gbc);

        // Bottone per uscire dal gioco
        JButton exitButton = createMenuButton("Esci dal gioco", e -> gameManager.exitGame());
        add(exitButton, gbc);
        
        
        
        loadBackgroundImage();
    }
    
    private void loadBackgroundImage() {
        try {
            backgroundImage = texturesManager.getMenuBackgrounds().get("menu");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private JButton createMenuButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 24));
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setOpaque(false);
        button.setForeground(Color.WHITE);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        Dimension buttonSize = new Dimension(250, 40);  
        button.setPreferredSize(buttonSize);
        button.addMouseListener(new MouseAdapter() {
        	@Override
            public void mouseClicked(MouseEvent e) {
                action.actionPerformed(null);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 28));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setFont(new Font("Arial", Font.BOLD, 24));
            }
        });

        return button;
    }
    
    public void showAudioOptions() {
        // Ottieni la finestra principale a cui il JPanel è associato
        Window parentWindow = SwingUtilities.getWindowAncestor(this);

        // Crea un JDialog modale relativo alla finestra principale
        JDialog optionsDialog = new JDialog(parentWindow, "Opzioni", Dialog.ModalityType.APPLICATION_MODAL);
        optionsDialog.setSize(400, 300);
        optionsDialog.setLocationRelativeTo(parentWindow);  // Centrato rispetto alla finestra principale

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15)); // Padding intorno al pannello

        // Slider per il volume della musica
        JLabel musicVolumeLabel = new JLabel("Volume Musica:");
        musicVolumeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JSlider musicVolumeSlider = new JSlider(0, 100, (int) (soundManager.getMusicVolume() * 100));
        musicVolumeSlider.setMajorTickSpacing(20);
        musicVolumeSlider.setMinorTickSpacing(5);
        musicVolumeSlider.setPaintTicks(true);
        musicVolumeSlider.setPaintLabels(true);
        musicVolumeSlider.setBackground(Color.WHITE);
        musicVolumeSlider.setForeground(Color.BLUE); 
        musicVolumeSlider.addChangeListener(e -> {
            int value = musicVolumeSlider.getValue();
            soundManager.setMusicVolume(value / 100f);
        });

        // Slider per il volume degli effetti
        JLabel effectsVolumeLabel = new JLabel("Volume Effetti:");
        effectsVolumeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JSlider effectsVolumeSlider = new JSlider(0, 100, (int) (soundManager.getEffectsVolume() * 100));
        effectsVolumeSlider.setMajorTickSpacing(20);
        effectsVolumeSlider.setMinorTickSpacing(5);
        effectsVolumeSlider.setPaintTicks(true);
        effectsVolumeSlider.setPaintLabels(true);
        effectsVolumeSlider.setBackground(Color.WHITE);
        effectsVolumeSlider.setForeground(Color.RED);
        effectsVolumeSlider.addChangeListener(e -> {
            int value = effectsVolumeSlider.getValue();
            soundManager.setEffectsVolume(value / 100f);
        });

        // Aggiungi componenti al pannello
        panel.add(musicVolumeLabel);
        panel.add(musicVolumeSlider);
        panel.add(Box.createVerticalStrut(15)); // Spazio tra i componenti
        panel.add(effectsVolumeLabel);
        panel.add(effectsVolumeSlider);

        // Aggiungi il pannello al dialogo delle opzioni
        optionsDialog.add(panel);
        optionsDialog.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Disegna lo sfondo
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}