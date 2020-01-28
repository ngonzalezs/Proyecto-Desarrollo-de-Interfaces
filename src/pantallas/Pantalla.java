package pantallas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

import principal.PanelJuego;

public interface Pantalla{
	
	public void inializarPantalla(PanelJuego panel);
	public void pintarPantalla(Graphics g);
	public void ejecutarFrame();
	
	//Listener 
	public void pulsarRaton(MouseEvent event);
	public void moverRaton(MouseEvent event);
	public void redimensionar();
	
}
