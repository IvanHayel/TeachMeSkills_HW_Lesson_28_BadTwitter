package by.teachmeskills.sweater.email;

// TODO: MailService MAilConfig MailController e.t.c
public interface Mailer {
    String getHost();
    String getPort();
    String getUsername();
    String getPassword();
    String getEmailAddress();
}