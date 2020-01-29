package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import principal.PanelJuego;
import principal.Sprite;

/**
 * PantallaJuego , es la clase donde pintamos los sprites y agregamos todos los
 * listeners
 * 
 * @author Noah Gonzalez Sanz
 *
 */
public class PantallaJuego implements Pantalla {

	/** SPRITES **/
	// Personaje Principal;
	Sprite personaje;
	Image imagenPersonaje;
	boolean enSuelo = false;
	static int ALTURA_SUELO_PERSONAJE = 260;
	static int velOrco = -5;
	int contPantalla = 1;
	int generadorDeNivel = 0;
	// Orco
	ArrayList<Sprite> orco = new ArrayList<Sprite>();
	Image imagenOrco;
	static int ALTURA_SUELO_ORCO = 270;

	// Meta
	Sprite meta;
	Image imagenMeta;
	/** PANEL JUEGO **/
	PanelJuego panelJuego;

	/** FONDO **/
	private Sprite fondoJuego;
	private Sprite fondoJuego2;
	private Image imagenFondoJuego;
	private Image imagenFondoJuego2;
	private Image fondoEscalado;

	/** SONIDOS **/
	private Clip sonidoInicial;
	private Clip sonidoChoque;
	private Clip sonidoMeta;

	public PantallaJuego(PanelJuego panel) {
		inializarPantalla(panel);
	}
	/**
	 * Inicializamos la imagenes,sprites,sonidos...
	 */
	@Override
	public void inializarPantalla(PanelJuego panel) {
		this.panelJuego = panel;
		// IMAGENES
		try {
			imagenPersonaje = ImageIO.read(new File("imagenes/personaje.png"));
			imagenOrco = ImageIO.read(new File("imagenes/orco.png"));
			imagenFondoJuego = ImageIO.read(new File("imagenes/fondoJuego.jpg"));
			imagenFondoJuego2 = ImageIO.read(new File("imagenes/fondoJuego.jpg"));
			imagenMeta = ImageIO.read(new File("imagenes/meta.png"));
			// INICIALIZAR SPRITES
			// Imagenes
			fondoJuego = new Sprite(0, 0, panelJuego.getWidth(), panelJuego.getHeight(), 3, 0, imagenFondoJuego, true);
			fondoJuego2 = new Sprite(0 - panelJuego.getWidth(), 0, panelJuego.getWidth(), panelJuego.getHeight(), 3, 0,
					imagenFondoJuego, true);
			// Personaje
			personaje = new Sprite(0, panelJuego.getHeight() - ALTURA_SUELO_PERSONAJE, 80, 80, 9, 0, imagenPersonaje,true);
			enSuelo = true;
			// Orco
			orco.add(new Sprite(1500, panelJuego.getHeight() - ALTURA_SUELO_ORCO, 80, 80, velOrco, 0, imagenOrco, true));

			// Meta
			meta = new Sprite(1298, 360, 120, 120, 0, 0, imagenMeta, true);
			// Generar Niveles
			generadorDeNivel = (int) (Math.random() * 5 + 2);
			// Inicializar sonidos
			sonidoInicial();
			sonidoChoque();
			sonidoGanador();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("PROBLEMAS AL CARGAR LAS IMAGENES. FIN DEL PROGRAMA");
			System.exit(1);
		}

	}
	/**
	 * Pintamos por pantalla los sprites
	 */
	@Override
	public void pintarPantalla(Graphics g) {

		fondoJuego.pintarEnMundo(g);
		fondoJuego2.pintarEnMundo(g);
		if (personaje != null) {
			personaje.pintarEnMundo(g);
		}
		for (int i = 0; i < orco.size(); i++) {
			orco.get(i).pintarEnMundo(g);

		}
		if (contPantalla == generadorDeNivel) {
			meta.pintarEnMundo(g);
		}
	}

	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(25);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (personaje != null) {
			comprobarAltura();
			comprobarColisiones();

		}
		comprobarFondo();
		moverSprites();
		generarOrco();
	}

	/**
	 * Generamos un orco/Obstaculo
	 */
	private void generarOrco() {

		for (int i = 0; i < orco.size(); i++) {
			if ((orco.get(i).getPosX() < panelJuego.getWidth() / 2) && (orco.size() <= 1)) {
				orco.add(new Sprite(panelJuego.getWidth(), panelJuego.getHeight() - ALTURA_SUELO_ORCO, 80, 80, velOrco,0, imagenOrco, true));
			}
		}

	}

	/**
	 * Cada vez que pulso el raton , sube el sprite (simulando salto)
	 */
	@Override
	public void pulsarRaton(MouseEvent e) {
		if (personaje != null && enSuelo) {
			personaje.setVelY(-10);
		}
	}

	@Override
	public void moverRaton(MouseEvent e) {

	}

	/**
	 * Mueve todos los sprites del PanelJuego
	 */
	private void moverSprites() {
		// Personaje
		if (personaje != null) {
			personaje.aplicarVelocidad();
			if (personaje.getPosY() > 400) {
				personaje.setPosY(400);
				enSuelo = true;
			}
		}
		// Orco
		for (int i = 0; i < orco.size(); i++) {
			orco.get(i).aplicarVelocidad();
		}

		// Fondo
		fondoJuego.aplicarVelocidad();
		fondoJuego2.aplicarVelocidad();
	}

	/**
	 * Comprobamos las colisiones de los sprites y de la ventana
	 */
	private void comprobarColisiones() {
		// Si el personaje sobrepasa la ventana aparece de nuevo
		if (personaje.getPosX() > 1400) {
			personaje.setPosX(-200);
			contPantalla++;
		}
		// Si el personaje colisiona con la meta y aparece , cambia de ventana
		if (personaje.colisiona(meta) && contPantalla == generadorDeNivel) {
			panelJuego.setPantalla(new PantallaGanadora(panelJuego));
			sonidoInicial.stop();
			sonidoMeta.start();

		}

		// Si la posicion del orco es menor que cero desaparece y sumo 1 la velocidad
		for (int i = 0; i < orco.size(); i++) {
			if (orco.get(i).getPosX() <= 0) {
				orco.remove(i);
				velOrco--;
			}
		}

		// Si el personaje colisona con el orco desaparece y cambia de ventana
		for (int i = 0; i < orco.size() && personaje != null; i++) {
			if (personaje.colisiona(orco.get(i))) {
				personaje = null;
				panelJuego.setPantalla(new PantallaFinal(panelJuego));
				sonidoInicial.stop();
				sonidoChoque.start();
			}
		}
	}

	/**
	 * Comprobamos en todo momento a que altura esta el sprite personaje
	 */
	private void comprobarAltura() {
		if (personaje.getPosY() < 230) {
			personaje.setVelY(-1 * personaje.getVelY());
			enSuelo = false;
		}
	}

	/**
	 * Comprobamos en todo momento si la posicion del fondo es mayor que el ancho de
	 * la ventana para que vuelva a aparecer
	 */
	private void comprobarFondo() {

		if (fondoJuego.getPosX() > panelJuego.getWidth()) {
			fondoJuego.setPosX(0 - panelJuego.getWidth());
		}
		if (fondoJuego2.getPosX() > panelJuego.getWidth()) {
			fondoJuego2.setPosX(0 - panelJuego.getWidth());
		}
	}

	public void sonidoInicial() {
		try {
			sonidoInicial = AudioSystem.getClip();
			sonidoInicial.open(AudioSystem.getAudioInputStream(new File("Sonidos/sonidoJuego.wav")));
			sonidoInicial.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sonidoChoque() {
		try {
			sonidoChoque = AudioSystem.getClip();
			sonidoChoque.open(AudioSystem.getAudioInputStream(new File("Sonidos/sonidoChoque.wav")));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void sonidoGanador() {
		try {
			sonidoMeta = AudioSystem.getClip();
			sonidoMeta.open(AudioSystem.getAudioInputStream(new File("Sonidos/sonidoGanador.wav")));
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void redimensionar() {

	}

}