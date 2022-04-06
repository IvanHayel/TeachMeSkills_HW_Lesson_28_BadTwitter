package by.teachmeskills.sweater.email;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;

import java.util.Properties;

public class EmailSender {
    private static final String MAIL_SMTP_HOST = "mail.smtp.host";
    private static final String MAIL_SMTP_AUTH = "mail.smtp.auth";
    private static final String MAIL_SMTP_PORT = "mail.smtp.socketFactory.port";
    private static final String MAIL_SMTP_SSL = "mail.smtp.ssl.enable";
    private static final String MAIL_SMTP_SSL_CHECK_SERVER_IDENTITY = "mail.smtp.ssl.checkserveridentity";
    private static final String MAIL_SMTP_SOCKET_FACTORY_CLASS = "mail.smtp.socketFactory.class";

    private static final String TRUE = "true";
    private static final String JAVAX_SSL_SOCKET_FACTORY = "javax.net.ssl.SSLSocketFactory";

    private final Mailer MAILER;
    private final Properties PROPERTIES;
    private final Session SESSION;

    public EmailSender(Mailer mailer) {
        this.MAILER = mailer;
        PROPERTIES = new Properties();
        PROPERTIES.put(MAIL_SMTP_HOST, MAILER.getHost());
        PROPERTIES.put(MAIL_SMTP_AUTH, TRUE);
        PROPERTIES.put(MAIL_SMTP_PORT, MAILER.getPort());
        PROPERTIES.put(MAIL_SMTP_SSL, TRUE);
        PROPERTIES.put(MAIL_SMTP_SSL_CHECK_SERVER_IDENTITY , JAVAX_SSL_SOCKET_FACTORY);
        PROPERTIES.put(MAIL_SMTP_SOCKET_FACTORY_CLASS, true);
        SESSION = Session.getDefaultInstance(PROPERTIES,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(MAILER.getUsername(), MAILER.getPassword());
                    }
                });
    }

    @SneakyThrows(MessagingException.class)
    public void sendTextEmail(String toEmail, String subject, String text) {
        Message message = new MimeMessage(SESSION);
        message.setFrom(new InternetAddress(MAILER.getEmailAddress()));
        message.setRecipient(Message.RecipientType.TO, new InternetAddress(toEmail));
        message.setSubject(subject);
        message.setText(text);
        Transport.send(message);
    }
}