package proyecto.tercera.nota.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender mailSender;

	public void enviarEmailRecuperacion(String to, String token) {
		try {
			SimpleMailMessage message = new SimpleMailMessage();
			message.setTo(to);
			message.setSubject("Recuperación de Contraseña");
			message.setText("Haz clic en el siguiente enlace para recuperar tu contraseña: "
					+ "http://127.0.0.1:4455/cambio-contrasena/index.html?token=" + token);
			mailSender.send(message);
			System.out.println("Correo enviado a: " + to);
		} catch (Exception e) {
			System.err.println("Error al enviar el correo: " + e.getMessage());
			e.printStackTrace(); // Imprimir stack trace para análisis
			throw new RuntimeException("Error al enviar el correo: " + e.getMessage(), e);
		}
	}
}
