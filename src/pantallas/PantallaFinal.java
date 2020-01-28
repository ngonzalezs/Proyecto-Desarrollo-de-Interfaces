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
 * Pantalla Final o Derrota , muestra un mensaje
 * @author Noah Gonzalez Sanz
 *
 */
public class PantallaFinal implements Pantalla{
	
	private PanelJuego panelJuego;
	private BufferedImage buffer;
	private Image imgDerrota;	
	private InputStream rutaFuente; 
	private Font fuente = null;
	
	public PantallaFinal(PanelJuego panel) {
		this.panelJuego = panel;
		try {
			rutaFuente = new BufferedInputStream(new FileInputStream("Fuentes/FuegoFatuo.ttf"));
			buffer = ImageIO.read(new File("Imagenes/perdedor.jpg"));
			fuente = Font.createFont(Font.TRUETYPE_FONT, rutaFuente);
			fuente = fuente.deriveFont(Font.PLAIN, 30);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
		redimensionar();
	}
	
	@Override
	public void inializarPantalla(PanelJuego panel) {
		
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imgDerrota, 0, 0, null);
		g.setColor(Color.CYAN);
		g.setFont(fuente);
		g.drawString("PERDISTES!", panelJuego.getWidth()-600, panelJuego.getHeight()-150);
		
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
		imgDerrota = buffer.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), BufferedImage.SCALE_SMOOTH);
	}
}
