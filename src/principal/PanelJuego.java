package principal;


import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;


import javax.swing.JPanel;

import pantallas.Pantalla;
import pantallas.PantallaInicio;

/**
 * PanelJuego, es una clase que extiende de JPanel y implementa Runnable
 * @author Noah Gonzalez Sanz
 *
 */
public class PanelJuego extends JPanel implements Runnable {

	/** PANTALLAS **/
	Pantalla pantallaEjecucion;

	// El contructor
	public PanelJuego() {
		
		pantallaEjecucion = new PantallaInicio(this);
		
		// HILO
		new Thread(this).start();

		// LISTENERS
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				pantallaEjecucion.pulsarRaton(e);
			}
		});

		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentResized(ComponentEvent e) {
				pantallaEjecucion.redimensionar();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				pantallaEjecucion.moverRaton(e);
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				mouseMoved(e);
			}
		});
	}

	// Metodo que se llama automÃ¡ticamente
	@Override
	public void paintComponent(Graphics g) {
		pantallaEjecucion.pintarPantalla(g);
	}


	@Override
	public void run() {
		while (true) {
			repaint();
			Toolkit.getDefaultToolkit().sync();
			pantallaEjecucion.ejecutarFrame();
		}

	}

	public void setPantalla(Pantalla pantalla) {
		pantallaEjecucion = pantalla;
	}
}
