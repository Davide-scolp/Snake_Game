package snake_package.Main;

import javax.swing.SwingUtilities;
import snake_package.Core.GameManager;

public class SnakeGame {
    public static void main(String[] args) {
        // Esegue il codice sull'Event Dispatch Thread (EDT), assicurando che l'interfaccia utente
        // venga aggiornata in modo sicuro per l'ambiente grafico Swing.
        SwingUtilities.invokeLater(() -> {
        	// Crea il manager dell'applicazione , che a sua volta creera'  il menu
            new GameManager();
        });
    }
}