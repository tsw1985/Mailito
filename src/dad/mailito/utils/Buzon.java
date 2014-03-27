package dad.mailito.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;


public class Buzon {

	private String servidorPop;
	private String servidorSmtp;
	private String servidorSmtpPort;
	private String direccionEmail;
	private String passwordEmail;
	
	public Buzon() {
		ResourceBundle bundle = ResourceBundle.getBundle("dad/mailito/utils/email");
		servidorPop = bundle.getString("servidor.pop");
		servidorSmtp = bundle.getString("servidor.smtp");
		servidorSmtpPort = bundle.getString("servidor.smtp.port");
		direccionEmail = bundle.getString("email.direccion");
		passwordEmail = bundle.getString("email.password");
	}
	
	
	
	public List<Mensaje> recuperarMensajes() {
		
		List<Mensaje> mensajes = new ArrayList<Mensaje>();

		Properties properties = System.getProperties();
		properties.setProperty("mail.store.protocol", "imaps");

		try {
			
			Session session = Session.getDefaultInstance(properties, null);

			Store store = session.getStore("imaps");
			store.connect(servidorPop, direccionEmail, passwordEmail);

			Folder inbox = store.getFolder("inbox");

			inbox.open(Folder.READ_ONLY);

			Message messages[] = inbox.getMessages();
			
			int cantidadEmails = inbox.getMessageCount();
			
		    if (cantidadEmails == 0) return null;
		   
		    for (int i = messages.length -1  ; i >=0 ; i--) {
		    
				String result = "";
					
				MimeMessage m = (MimeMessage)messages[i];
				
				if (messages[i].getContent() instanceof Multipart){
				
					Multipart content = (Multipart) m.getContent();
		            result = content.getBodyPart(0).getContent().toString();

				}else{
					
					result = m.getContent().toString();
				}
				
				Mensaje mensaje = new Mensaje();
				
				Date fechaEnvio  = messages[i].getSentDate();
				String remitente = messages[i].getFrom()[0].toString();
				String asunto    = messages[i].getSubject();
				
				mensaje.setFecha(fechaEnvio);
				mensaje.setRemitente(remitente);
				mensaje.setAsunto(asunto);
				mensaje.setContenido(result);
				
				mensajes.add(mensaje);

			}
			
		   store.close();
		
				
		}
		catch(ArrayIndexOutOfBoundsException ex){
			
			System.out.println("No hay mas emails " + ex.toString() );
		
		}catch(Exception ex){
			System.out.println("Exception Mensaje " + ex.getMessage());
		}

		return mensajes;
	}
	
	public void enviarMensaje(String destinatario, String asunto, String contenido) {
		
		Properties props = new Properties();
		props.put("mail.smtp.host", servidorSmtp); // smtp.hotmail.com
		props.put("mail.smtp.socketFactory.port", servidorSmtpPort);
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", servidorSmtpPort);


		Session session = Session.getInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(direccionEmail, passwordEmail);
					}
				});
		
		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(direccionEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
			message.setSubject(asunto);
			message.setText(contenido);

			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}
}