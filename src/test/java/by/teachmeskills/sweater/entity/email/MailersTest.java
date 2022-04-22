package by.teachmeskills.sweater.entity.email;

import by.teachmeskills.sweater.email.Mailers;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MailersTest {
    @Test
    void testValueOf() {
        Mailers actualValueOfResult = Mailers.valueOf("SWEATER_MAILER");
        assertEquals("tms.homework.test@yandex.ru", actualValueOfResult.getEmailAddress());
        assertEquals("smtp.yandex.ru", actualValueOfResult.getHost());
        assertEquals("lltopkqdusemlwha", actualValueOfResult.getPassword());
        assertEquals("465", actualValueOfResult.getPort());
        assertEquals("tms.homework.test@yandex.ru", actualValueOfResult.getUsername());
    }
}

