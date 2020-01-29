package pantallas;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;

import javax.imageio.ImageIO;

import principal.PanelJuego;
/**
 * Pantalla Ganadora , muestra un mensaje y el tiempo que has tardado.
 * @author Noah Gonzalez Sanz
 *
 */
public class PantallaGanadora implements Pantalla{
	private PanelJuego panelJuego;
	private BufferedImage buffer;
	private Image imgGanadora;
	private InputStream rutaFuente; 
	private Font fuente = null;
	
	public PantallaGanadora(PanelJuego panel) {
		this.panelJuego = panel;
		try {
			rutaFuente = new BufferedInputStream(new FileInputStream("Fuentes/FuegoFatuo.ttf"));
			buffer = ImageIO.read(new File("Imagenes/ganador.jpg"));
			fuente = Font.createFont(Font.TRUETYPE_FONT, rutaFuente);
			fuente = fuente.deriveFont(Font.PLAIN, 40);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch(FontFormatException e) {
			e.printStackTrace();
		}
		redimensionar();
	}

	@Override
	public void inializarPantalla(PanelJuego panel) {
		
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imgGanadora, 0, 0, null);
		g.setColor(Color.RED);
		g.setFont(fuente);
		g.drawString("¡VICTORIA!", panelJuego.getWidth()-660, panelJuego.getHeight()-220);
		
	}

	@Override
	public void ejecutarFrame() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pulsarRaton(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void moverRaton(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redimensionar() {
		imgGanadora = buffer.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), BufferedImage.SCALE_SMOOTH);
	}

}
