package by.teachmeskills.sweater.email;

public interface Mailer {
    String getHost();
    String getPort();
    String getUsername();
    String getPassword();
    String getEmailAddress();
}