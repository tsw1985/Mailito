package dad.mailito;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JToolBar;

import dad.mailito.iconos.Iconos;
import dad.mailito.utils.Buzon;
import dad.mailito.utils.Mensaje;

@SuppressWarnings("serial")
public class WindowEmailFrame extends JFrame {
	
	private JSplitPane divisorJSplitPane;
	
	private JPanel panelArriba;
	private JToolBar barraHerramientasToolBar;
	
	private JToolBarButton recuperarJButton;
	private JToolBarButton enviarJButton;
	private JToolBarButton eliminarJButton;
	
	private JTable tablaEmailsJTable;

	private JPanel panelAbajo;
	private JPanel cabeceraEmailTextAreaPanel;
	
	private JLabel remitenteLabel;
	private JLabel fechaLabel;
	private JLabel asuntoLabel;

	private JLabel remitenteContenidoJLabel;
	private JLabel fechaContenidoJLabel;
	private JLabel asuntoContenidoJLabel;
	
	private JTextArea contenidoEmailJTextArea;
	private MouseAdapter tableMouseModelListener;
	
	private InboxMail inbox;
	private EmailTableModel emailTableModel;
	
	private List<Mensaje> mensajesNuevos;
	public static Buzon buzon;
	
	public WindowEmailFrame(){
		initFrame();
		initComponent();
	}


	private void initComponent() {
		
		divisorJSplitPane = new JSplitPane();
		divisorJSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		divisorJSplitPane.setDividerLocation(300);
		divisorJSplitPane.setOneTouchExpandable(true);
		
		mensajesNuevos = new ArrayList<Mensaje>();
		
		buzon = new Buzon();
		
		crearPanelArriba();
		crearPanelAbajo();
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(divisorJSplitPane , BorderLayout.CENTER);
		
	}
	
	
	public void crearPanelArriba(){
		
		ActionListener recuperarActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				leerBuzonDeEntrada(e);
				
			}
		};
		
		ActionListener enviarActionListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enviarNuevoEmail(e);
			}
		};
		
		
		panelArriba = new JPanel(new GridBagLayout());
		
		recuperarJButton = new JToolBarButton("Recuperar", Iconos.RECUPERAR_EMAIL , recuperarActionListener);
		recuperarJButton.setText("Recuperar");
		recuperarJButton.setFont(new Font( recuperarJButton.getName() , Font.BOLD, recuperarJButton.getFont().getSize() ));
		recuperarJButton.setHorizontalTextPosition(JButton.CENTER);
		recuperarJButton.setVerticalTextPosition(JButton.BOTTOM);
		
		enviarJButton    = new JToolBarButton("Enviar" , Iconos.ENVIAR_EMAIL , enviarActionListener);
		enviarJButton.setText("Enviar");
		enviarJButton.setFont(new Font( enviarJButton.getName() , Font.BOLD, enviarJButton.getFont().getSize() ));
		enviarJButton.setHorizontalTextPosition(JButton.CENTER);
		enviarJButton.setVerticalTextPosition(JButton.BOTTOM);
		
		eliminarJButton  = new JToolBarButton("Eliminar" , Iconos.ELIMINAR_EMAIL , null);
		eliminarJButton.setText("Eliminar");
		eliminarJButton.setFont(new Font( eliminarJButton.getName() , Font.BOLD, eliminarJButton.getFont().getSize() ));
		eliminarJButton.setHorizontalTextPosition(JButton.CENTER);
		eliminarJButton.setVerticalTextPosition(JButton.BOTTOM);
		
		barraHerramientasToolBar = new JToolBar();
		barraHerramientasToolBar.setFloatable(false);
		barraHerramientasToolBar.add(recuperarJButton);
		barraHerramientasToolBar.add(enviarJButton);
		barraHerramientasToolBar.add(eliminarJButton);
	
		
		tablaEmailsJTable = new JTable(emailTableModel);
				
		tableMouseModelListener = new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				cargaEmail();
			}
		
		};
		
		tablaEmailsJTable.addMouseListener(tableMouseModelListener);
		
		
		
		Insets margin = new Insets(5,5,5,5);
		
		panelArriba.add(barraHerramientasToolBar           , new GridBagConstraints ( 0,0 , 1,1 , 1.0 , 0.0 , GridBagConstraints.NORTH , GridBagConstraints.HORIZONTAL , margin , 0 , 0 ));
		panelArriba.add(new JScrollPane(tablaEmailsJTable) , new GridBagConstraints ( 0,1 , 1,1 , 1.0 , 1.0 , GridBagConstraints.CENTER, GridBagConstraints.BOTH , margin , 0 , 0 ));

		
		divisorJSplitPane.setLeftComponent(panelArriba);
		
	}
	
	
	protected void enviarNuevoEmail(ActionEvent e) {
		 new WindowNewEmail().setVisible(true);
	}


	protected void leerBuzonDeEntrada(ActionEvent e) {
		
		mensajesNuevos = buzon.recuperarMensajes();
		inbox = new InboxMail();
		inbox.setListaDeMensajes(mensajesNuevos);
		
		emailTableModel = new EmailTableModel(inbox);
		tablaEmailsJTable.setModel(emailTableModel);
		
	}


	public void cargaEmail(){

		int fila         = tablaEmailsJTable.getSelectedRow();
		String remitente = tablaEmailsJTable.getModel().getValueAt(fila, 2).toString();
		String fecha     = tablaEmailsJTable.getModel().getValueAt(fila, 1).toString();
		String asunto    = tablaEmailsJTable.getModel().getValueAt(fila, 0).toString();
		
		String contenido = tablaEmailsJTable.getModel().getValueAt(fila, 4).toString();

		remitenteContenidoJLabel.setText(remitente);
		contenidoEmailJTextArea.setText(contenido);
		
		fechaContenidoJLabel.setText(fecha);
		asuntoContenidoJLabel.setText(asunto);
		
	}

	public void crearPanelAbajo(){
		
		panelAbajo = new JPanel(new GridBagLayout());
		
		cabeceraEmailTextAreaPanel = new JPanel(new GridBagLayout());
		
		remitenteLabel = new JLabel("Remitente: ");
		remitenteLabel.setFont(new Font( remitenteLabel.getName() , Font.BOLD, remitenteLabel.getFont().getSize() ));
		
		fechaLabel = new JLabel("Fecha: ");
		fechaLabel.setFont(new Font( fechaLabel.getName() , Font.BOLD, fechaLabel.getFont().getSize() ));
		
		asuntoLabel = new JLabel("Asunto: ");
		asuntoLabel.setFont(new Font( asuntoLabel.getName() , Font.BOLD, asuntoLabel.getFont().getSize() ));
		
		remitenteContenidoJLabel = new JLabel();
		fechaContenidoJLabel = new JLabel();
		asuntoContenidoJLabel = new JLabel();
		
		Insets margin = new Insets(5,5,5,5);
		cabeceraEmailTextAreaPanel.add(remitenteLabel, new GridBagConstraints ( 0,0 , 1,1 , 0.0 , 0.0 , GridBagConstraints.NORTHEAST , GridBagConstraints.NONE , margin , 0 , 0 ));
		cabeceraEmailTextAreaPanel.add(fechaLabel    , new GridBagConstraints ( 0,1 , 1,1 , 0.0 , 0.0 , GridBagConstraints.NORTHEAST , GridBagConstraints.NONE , margin , 0 , 0 ));
		cabeceraEmailTextAreaPanel.add(asuntoLabel   , new GridBagConstraints ( 0,2 , 1,1 , 0.0 , 0.0 , GridBagConstraints.NORTHEAST , GridBagConstraints.NONE , margin , 0 , 0 ));
		
		cabeceraEmailTextAreaPanel.add(remitenteContenidoJLabel, new GridBagConstraints ( 1,0 , 1,1 , 1.0 , 0.0 , GridBagConstraints.NORTHEAST , GridBagConstraints.HORIZONTAL , margin , 0 , 0 ));
		cabeceraEmailTextAreaPanel.add(fechaContenidoJLabel    , new GridBagConstraints ( 1,1 , 1,1 , 1.0 , 0.0 , GridBagConstraints.NORTHEAST , GridBagConstraints.HORIZONTAL , margin , 0 , 0 ));
		cabeceraEmailTextAreaPanel.add(asuntoContenidoJLabel   , new GridBagConstraints ( 1,2 , 1,1 , 1.0 , 0.0 , GridBagConstraints.NORTHEAST , GridBagConstraints.HORIZONTAL , margin , 0 , 0 ));
		
		contenidoEmailJTextArea = new JTextArea();
		
		panelAbajo.add(cabeceraEmailTextAreaPanel, new GridBagConstraints ( 0,0 , 1,1 , 1.0 , 0.0 , GridBagConstraints.NORTH , GridBagConstraints.HORIZONTAL , margin , 0 , 0 ));
		panelAbajo.add(new JScrollPane(contenidoEmailJTextArea)   , new GridBagConstraints ( 0,1 , 1,1 , 1.0 , 1.0 , GridBagConstraints.NORTH , GridBagConstraints.BOTH       , margin , 0 , 0 ));
		
		divisorJSplitPane.setRightComponent(panelAbajo);
		
	}
	
	private void initFrame() {
		setTitle("Mailito : Gabriel González 2º DAM");
		setSize(800,600);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
	}
}
