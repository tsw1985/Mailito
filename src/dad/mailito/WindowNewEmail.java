package dad.mailito;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class WindowNewEmail extends JDialog {
	
	private JLabel asuntoLabel;
	private JLabel destinatarioLabel;
	private JLabel arroba;
	
	private JPanel mensajeTextAreaJPanel;
	private JPanel botonesPanel;
	private JPanel principalPanel;
	
	private JTextField asuntoTextField;
	
	private JTextField destinatarioUsuarioTextField;
	private JTextField destinatarioDominioTextField;
	private JTextArea mensajeArea;
	
	private JButton enviarButton;
	private JButton cancerlaButton;
	
	
	public WindowNewEmail(){
		
		initFrame();
		initComponent();
		
	}


	private void initComponent() {
		
		Insets margin = new Insets(10,9,0,10);
		
		
		principalPanel = new JPanel(new GridBagLayout());
		
		asuntoLabel = new JLabel("Asunto:");
		destinatarioLabel = new JLabel("Destinatario:");
		arroba = new JLabel("@");
		
		asuntoTextField = new JTextField();
		destinatarioUsuarioTextField = new JTextField(11);
		destinatarioUsuarioTextField.setMinimumSize(destinatarioUsuarioTextField.getPreferredSize());
		
		destinatarioDominioTextField = new JTextField(11);
		destinatarioDominioTextField.setMinimumSize(destinatarioDominioTextField.getPreferredSize());
		
		
		JPanel emailPanel = new JPanel(new GridBagLayout());
		//emailPanel.setBackground(Color.red);
		emailPanel.add(asuntoLabel      ,           new GridBagConstraints ( 0,0 , 1,1 , 0.0 , 0.0 , GridBagConstraints.NORTHEAST , GridBagConstraints.NONE       , margin , 0 , 0 ));
		emailPanel.add(asuntoTextField  ,           new GridBagConstraints ( 1,0 , 3,1 , 1.0 , 0.0 , GridBagConstraints.EAST      , GridBagConstraints.HORIZONTAL , margin , 0 , 0 ));
		emailPanel.add(destinatarioLabel,           new GridBagConstraints ( 0,1 , 1,1 , 0.0 , 0.0 , GridBagConstraints.NORTHWEST , GridBagConstraints.NONE       , margin , 0 , 0 ));
		
		emailPanel.add(destinatarioUsuarioTextField,new GridBagConstraints ( 1,1 , 1,1 , 0.0 , 0.0 , GridBagConstraints.WEST      , GridBagConstraints.NONE , margin , 0 , 0 ));
		emailPanel.add(arroba,                      new GridBagConstraints ( 2,1 , 1,1 , 0.0 , 0.0 , GridBagConstraints.EAST      , GridBagConstraints.NONE , new Insets(8,0,0,0) , 0 , 0 ));
		emailPanel.add(destinatarioDominioTextField,new GridBagConstraints ( 3,1 , 1,1 , 0.0 , 0.0 , GridBagConstraints.WEST      , GridBagConstraints.NONE , margin , 0 , 0 ));
		
		
		principalPanel.add( emailPanel , new GridBagConstraints ( 1,1 , 1,1 , 1.0 , 0.0 , GridBagConstraints.EAST , GridBagConstraints.HORIZONTAL , margin , 0 , 0 ));
		
		mensajeArea = new JTextArea();
		
		mensajeTextAreaJPanel = new JPanel(new GridBagLayout());
		mensajeTextAreaJPanel.setBorder(BorderFactory.createTitledBorder("Mensaje:"));
		mensajeTextAreaJPanel.add(new JScrollPane(mensajeArea),new GridBagConstraints ( 0,0 , 1,1 , 1.0 , 1.0 , GridBagConstraints.CENTER , GridBagConstraints.BOTH , new Insets(0,0,0,0) , 0 , 0 ));
		
		principalPanel.add( mensajeTextAreaJPanel , new GridBagConstraints ( 0,2 , 2,1 , 1.0 , 1.0 , GridBagConstraints.NORTH , GridBagConstraints.BOTH , new Insets(10,10,10,10) , 0 , 0 ));
		

		enviarButton = new JButton("Enviar");
		enviarButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				enviarEmailButton(e);
				
			}
		});
		
		
		cancerlaButton = new JButton("Cancelar");
		cancerlaButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cancelarButton(e);
				
			}
		});
		
		botonesPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		botonesPanel.add(enviarButton );
		botonesPanel.add(cancerlaButton);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(principalPanel , BorderLayout.CENTER);
		getContentPane().add(botonesPanel , BorderLayout.SOUTH);
		
		getRootPane().setDefaultButton(enviarButton);
		
	}


	protected void enviarEmailButton(ActionEvent e) {
		
		String asunto = asuntoTextField.getText();
		String mensaje = mensajeArea.getText();
		String destinatarioNombre = destinatarioUsuarioTextField.getText();
		String destinatarioDominio = destinatarioDominioTextField.getText();
		
		if(asunto.equals("") || mensaje.equals("") || destinatarioNombre.equals("") || destinatarioDominio.equals("")){
			JOptionPane.showMessageDialog(this, "Está dejando campos en blanco.");
			return;
		}
		
		
		WindowEmailFrame.buzon.enviarMensaje(destinatarioNombre.concat("@").concat(destinatarioDominio), asunto, mensaje);

		dispose();
		
	}
	
	protected void cancelarButton(ActionEvent e){
		dispose();
	}


	private void initFrame() {
		
		setTitle("Enviar Mensaje");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(435,440);
		setLocationRelativeTo(null);
		setModal(true);
	}

}
