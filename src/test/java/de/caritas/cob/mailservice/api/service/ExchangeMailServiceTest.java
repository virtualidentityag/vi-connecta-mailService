package de.caritas.cob.mailservice.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import de.caritas.cob.mailservice.api.exception.ExchangeMailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class ExchangeMailServiceTest {

  final String SENDER = "name@domain.de";
  final String RECIPIENT = "name@domain.de";
  final String SUBJECT = "subject";
  final String TEMPLATE = "test";
  final String BODY = "test";

  final String EXCHANGE_USER_FIELD_NAME = "exchangeUser";
  final String EXCHANGE_PASSWORD_FIELD_NAME = "exchangePassword";
  final String EXCHANGE_URL_FIELD_NAME = "exchangeUrl";
  final String EXCHANGE_VERSION_FIELD_NAME = "exchangeVersion";
  final String EXCHANGE_USER_VALUE = "dummyUser";
  final String EXCHANGE_PASSWORD_VALUE = "dummyPassword";
  final String EXCHANGE_URL_VALUE = "dummyULR";
  final String EXCHANGE_VERSION_VALUE = "Exchange2007_SP1";

  private ExchangeMailService mailService;

  @BeforeEach
  void setup() throws Exception {
    this.mailService = new ExchangeMailService();
    ReflectionTestUtils.setField(mailService, EXCHANGE_USER_FIELD_NAME, EXCHANGE_USER_VALUE);
    ReflectionTestUtils.setField(
        mailService, EXCHANGE_PASSWORD_FIELD_NAME, EXCHANGE_PASSWORD_VALUE);
    ReflectionTestUtils.setField(mailService, EXCHANGE_URL_FIELD_NAME, EXCHANGE_URL_VALUE);
    ReflectionTestUtils.setField(mailService, EXCHANGE_VERSION_FIELD_NAME, EXCHANGE_VERSION_VALUE);
  }

  @Test
  void prepareAndSendHtmlMail_Should_ThrowServiceException_When_SenderMailAddressIsNotSet() {
    try {
      mailService.prepareAndSendHtmlMail(RECIPIENT, SUBJECT, TEMPLATE, null);
      fail("Expected exception: ServiceException");
    } catch (ExchangeMailServiceException serviceException) {
      assertTrue(true, "Excepted ServiceException thrown");
      assertEquals("No sender mail address set", serviceException.getMessage());
    }
  }

  @Test
  void prepareAndSendHtmlMail_Should_ThrowServiceException_When_MailCouldNotBeSend() {
    assertThrows(ExchangeMailServiceException.class, () -> {

      ReflectionTestUtils.setField(mailService, "mailSender", SENDER);

      mailService.prepareAndSendHtmlMail(RECIPIENT, SUBJECT, TEMPLATE, null);
    });
  }

  @Test
  void prepareAndSendTextMail_Should_ThrowServiceException_When_SenderMailAddressIsNotSet() {
    try {
      mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
      fail("Expected exception: ServiceException");
    } catch (ExchangeMailServiceException serviceException) {
      assertTrue(true, "Excepted ServiceException thrown");
      assertEquals("No sender mail address set", serviceException.getMessage());
    }
  }

  @Test
  void prepareAndSendTextMail_Should_ThrowServiceException_When_MailCouldNotBeSend() {
    assertThrows(ExchangeMailServiceException.class, () -> {

      ReflectionTestUtils.setField(mailService, "mailSender", SENDER);
      mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
    });
  }

  @Test
  void
      prepareAndSendTextMail_Should_ThrowExchangeMailServiceException_When_MailUrlIsInvalid() {
    assertThrows(ExchangeMailServiceException.class, () -> {
      ReflectionTestUtils.setField(mailService, EXCHANGE_URL_FIELD_NAME, "Invalid");
      mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
    });
  }

  @Test
  void
      prepareAndSendTextMail_Should_ThrowExchangeMailServiceException_When_ParametersAreNull() {
    assertThrows(ExchangeMailServiceException.class, () -> {
      ReflectionTestUtils.setField(mailService, "mailSender", SENDER);
      mailService.prepareAndSendTextMail(null, null, null);
    });
  }
}
