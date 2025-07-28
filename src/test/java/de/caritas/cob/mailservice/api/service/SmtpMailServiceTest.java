package de.caritas.cob.mailservice.api.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.doThrow;

import de.caritas.cob.mailservice.api.exception.SmtpMailServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class SmtpMailServiceTest {

  final String SENDER = "name@domain.de";
  final String RECIPIENT = "name@domain.de";
  final String RECIPIENTS = "name@domain.de,name2@domain.de";
  final String SUBJECT = "subject";
  final String TEMPLATE = "test";
  final String BODY = "test";

  @Mock private JavaMailSender javaMailSender;

  private SmtpMailService mailService;

  @BeforeEach
  void setup() {
    this.mailService = new SmtpMailService(javaMailSender);
  }

  @Test
  void prepareAndSendHtmlMail_Should_ThrowServiceException_WhenSenderMailAddressIsNotSet() {

    try {
      mailService.prepareAndSendHtmlMail(RECIPIENT, SUBJECT, TEMPLATE, null);
      fail("Expected exception: ServiceException");
    } catch (SmtpMailServiceException serviceException) {
      assertTrue(true, "Excepted ServiceException thrown");
    }
  }

  @Test
  void prepareAndSendHtmlMail_Should_ThrowServiceException_WhenMailCouldNotBeSend()
      throws NoSuchFieldException, SecurityException {

    ReflectionTestUtils.setField(mailService, "mailSender", String.valueOf(SENDER));

    @SuppressWarnings("serial")
    MailException mailException = new MailException("reason") {};
    doThrow(mailException).when(javaMailSender).send(Mockito.any(MimeMessagePreparator.class));

    try {
      mailService.prepareAndSendHtmlMail(RECIPIENT, SUBJECT, TEMPLATE, null);
      fail("Expected exception: ServiceException");
    } catch (SmtpMailServiceException serviceException) {
      assertTrue(true, "Excepted ServiceException thrown");
    }
  }

  @Test
  void prepareAndSendTextMail_Should_ThrowServiceException_WhenSenderMailAddressIsNotSet() {

    try {
      mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
      fail("Expected exception: ServiceException");
    } catch (SmtpMailServiceException serviceException) {
      assertTrue(true, "Excepted ServiceException thrown");
    }
  }

  @Test
  void prepareAndSendTextMail_Should_ThrowServiceException_WhenMailCouldNotBeSend()
      throws NoSuchFieldException, SecurityException {

    ReflectionTestUtils.setField(mailService, "mailSender", String.valueOf(SENDER));

    @SuppressWarnings("serial")
    MailException mailException = new MailException("reason") {};
    doThrow(mailException).when(javaMailSender).send(Mockito.any(MimeMessagePreparator.class));

    try {
      mailService.prepareAndSendTextMail(RECIPIENT, SUBJECT, BODY);
      fail("Expected exception: ServiceException");
    } catch (SmtpMailServiceException serviceException) {
      assertTrue(true, "Excepted ServiceException thrown");
    }
  }
}
