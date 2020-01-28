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

import javax.imageio.ImageIO;

import principal.PanelJuego;
/**
 * PantallaInicio, es el mensaje que se muestra al abrir el programa.
 * @author Noah Gonzalez Sanz
 *
 */
public class PantallaInicio implements Pantalla{
	
	/** PINICIAL COLOR **/
	Color colorLetraInicio = Color.WHITE;
	PanelJuego panelJuego;
	BufferedImage bufferInicio;
	Image imgInicio;
	Font fuenteInicio = null;
	InputStream rutaFuente= null;
	
	public PantallaInicio() {
		// TODO Auto-generated constructor stub
	}
	
	public PantallaInicio(PanelJuego panel) {
		this.panelJuego = panel;
		try {
			rutaFuente = new BufferedInputStream(new FileInputStream("Fuentes/FuegoFatuo.ttf"));
			bufferInicio = ImageIO.read(new File("imagenes/fondoInicio.jpg"));
			fuenteInicio = Font.createFont(Font.TRUETYPE_FONT, rutaFuente);
			fuenteInicio = fuenteInicio.deriveFont(Font.PLAIN, 26);
		} catch (IOException e) {
			e.printStackTrace();
		} catch(FontFormatException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void inializarPantalla(PanelJuego panel) {
	}

	@Override
	public void pintarPantalla(Graphics g) {
		g.drawImage(imgInicio,0,0,null);
		g.setFont(fuenteInicio);
		g.setColor(colorLetraInicio);
		g.drawString("Pulsa en la pantalla para empezar!", panelJuego.getWidth() / 2 - 200, panelJuego.getHeight() / 2 - -(245));
	}

	@Override
	public void ejecutarFrame() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		colorLetraInicio = colorLetraInicio == Color.WHITE ? Color.RED: Color.WHITE;
	}

	@Override
	public void pulsarRaton(MouseEvent event) {
		panelJuego.setPantalla(new PantallaJuego(panelJuego));
		
	}

	@Override
	public void moverRaton(MouseEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void redimensionar() {
		imgInicio = bufferInicio.getScaledInstance(panelJuego.getWidth(), panelJuego.getHeight(), BufferedImage.SCALE_SMOOTH);
		
	}

}
