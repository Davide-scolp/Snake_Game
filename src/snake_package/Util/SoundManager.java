package snake_package.Util;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SoundManager {
	
    private Clip menuClip;
    private Clip appleEatenClip;
    private Clip trapHitClip;
    private Clip fireballHitClip;
    private Clip gameOverClip;
    private Clip obstacleHitClip;
    private Clip youWinClip;
    
    private FloatControl musicVolumeControl;
    private Map<Clip, FloatControl> effectsVolumeControls;
    
    private float musicVolume;
    private float effectsVolume;

    private SoundManager() {
    	this.musicVolume = 0.5f;
        this.effectsVolume = 0.5f;
        loadAndControlEffects();
    }
    
 // Classe interna statica per gestire l'istanza Singleton
    private static class Holder {
        private static final SoundManager INSTANCE = new SoundManager();
    }

    // Metodo pubblico per accedere all'istanza Singleton
    public static SoundManager getInstance() {
        return Holder.INSTANCE;
    }
    
    private void loadAndControlEffects() {
    	effectsVolumeControls = new HashMap<>();
        try {
            menuClip = loadClip("/assets/Sound/MenuSound.wav");
            appleEatenClip = loadClip("/assets/Sound/AppleEatenSound.wav");
            trapHitClip = loadClip("/assets/Sound/TrapSound.wav");
            fireballHitClip = loadClip("/assets/Sound/FireballHitSound.wav");
            gameOverClip = loadClip("/assets/Sound/GameOverSound.wav");
            obstacleHitClip = loadClip("/assets/Sound/ObstacleHitSound.wav");
            youWinClip = loadClip("/assets/Sound/YouWinSound.wav");
            
            // Ottieni il controllo del volume per la musica di menu
            if (menuClip != null) {
                musicVolumeControl = (FloatControl) menuClip.getControl(FloatControl.Type.MASTER_GAIN);
                setMusicVolume(musicVolume);
            }

            // Aggiungi i controlli del volume per gli effetti sonori
            addEffectControl(appleEatenClip);
            addEffectControl(trapHitClip);
            addEffectControl(fireballHitClip);
            addEffectControl(gameOverClip);
            addEffectControl(obstacleHitClip);
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Clip loadClip(String path) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        URL url = getClass().getResource(path);
        if (url == null) {
            throw new IOException("Resource not found: " + path);
        }
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(url);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        return clip;
    }
    
    private void addEffectControl(Clip clip) {
        if (clip != null) {
        	FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        	effectsVolumeControls.put(clip, control);
        }
    }
    
    private float convertVolumeToGain(float volume) {
        return 20f * (float) Math.log10(volume <= 0.0f ? 0.0001f : volume); // Logarithmic conversion
    }
    
    private void playClip(Clip clip) {
        if (clip != null) {
            clip.setFramePosition(0); // Rewind to the beginning
            clip.start();
        }
    }

    public void setMusicVolume(float volume) {
        this.musicVolume = volume;
        if (musicVolumeControl != null) {
            float gain = convertVolumeToGain(volume);
            musicVolumeControl.setValue(gain);
            System.out.println("Music volume set to: " + volume); // Debug
        }
    }

    public void setEffectsVolume(float volume) {
        this.effectsVolume = volume;
        for (FloatControl control : effectsVolumeControls.values()) {
            if (control != null) {
                float gain = convertVolumeToGain(volume);
                control.setValue(gain);
                System.out.println("Effects volume set to: " + volume); // Debug
            }
        }
    }

    public float getMusicVolume() {
        return musicVolume;
    }

    public float getEffectsVolume() {
        return effectsVolume;
    }

    public void playMusic() {
        if (menuClip != null) {
        	menuClip.setFramePosition(0);
            menuClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stopMusic() {
        if (menuClip != null && menuClip.isRunning()) {
            menuClip.stop();
        }
    }

    public void playAppleEatenSound() {
        playClip(appleEatenClip);
    }
    
    public void playTrapSound() {
        playClip(trapHitClip);
    }
    
    public void playFireballHitSound() {
        playClip(fireballHitClip);
    }
    
    public void playGameOverSound() {
        playClip(gameOverClip);
    }
    
    public void playObstacleHitSound() {
        playClip(obstacleHitClip);
    }
    
    public void playYouWinSound() {
    	playClip(youWinClip);
    }
   
}