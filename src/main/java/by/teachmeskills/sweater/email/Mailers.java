package by.teachmeskills.sweater.email;

public enum Mailers implements Mailer {
    SWEATER_MAILER("smtp.yandex.ru", "465", "tms.homework.test@yandex.ru", "lltopkqdusemlwha", "tms.homework.test@yandex.ru");

    private final String HOST;
    private final String PORT;
    private final String USERNAME;
    private final String PASSWORD;
    private final String MAIL_ADDRESS;

    Mailers(String HOST, String PORT, String USERNAME, String PASSWORD, String MAIL_ADDRESS) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
        this.MAIL_ADDRESS = MAIL_ADDRESS;
    }

    @Override
    public String getHost() {
        return HOST;
    }

    @Override
    public String getPort() {
        return PORT;
    }

    @Override
    public String getUsername() {
        return USERNAME;
    }

    @Override
    public String getPassword() {
        return PASSWORD;
    }

    @Override
    public String getEmailAddress() {
        return MAIL_ADDRESS;
    }
}
