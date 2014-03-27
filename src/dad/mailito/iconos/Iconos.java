package dad.mailito.iconos;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Iconos {
	
	public static final Icon RECUPERAR_EMAIL  = cargarIcono("correos-recibidos.png");
	public static final Icon ENVIAR_EMAIL     = cargarIcono("nuevo-correo.png");
	public static final Icon ELIMINAR_EMAIL   = cargarIcono("eliminar-correo.png");
	
	public static Icon cargarIcono(String nombre){
		return new ImageIcon(Iconos.class.getResource("/dad/mailito/iconos/"+nombre));
	}

}
