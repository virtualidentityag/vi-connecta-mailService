package de.caritas.cob.mailservice.api.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import de.caritas.cob.mailservice.api.exception.InternalServerErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class HelperTest {

  private Helper helper;

  private static final String TEXT = "Lorem Ipsum";
  private static final String TEXT_WITH_NEWLINE = "Lorem Ipsum\nLorem Ipsum";
  private static final String TEXT_WITH_NEWLINE_AND_HTML_AND_JS =
      "<b>Lorem Ipsum</b>\nLorem Ipsum<script>alert('1');</script>";
  private static final String TEXT_WITH_HTML = "<strong>Lorem Ipsum</strong>";
  private static final String TEXT_WITH_JS = "Lorem Ipsum<script>alert('1');</script>";
  private static final String TEXT_WITH_HTML_ENTITY = "Hallo &amp;";
  private static final String TEXT_WITH_UNESCAPED_HTML_ENTITY = "Hallo &";

  @BeforeEach
  void setup() {
    helper = new Helper();
  }

  @Test
  void removeHTMLFromText_Should_RemoveHtmlFromText() {
    assertEquals(TEXT, helper.removeHTMLFromText(TEXT_WITH_HTML));
  }

  @Test
  void removeHTMLFromText_Should_RemoveJavascriptFromText() {
    assertEquals(TEXT, helper.removeHTMLFromText(TEXT_WITH_JS));
  }

  @Test
  void removeHTMLFromText_ShouldNot_RemoveNewlinesFromText() {
    assertEquals(TEXT_WITH_NEWLINE, helper.removeHTMLFromText(TEXT_WITH_NEWLINE));
  }

  @Test
  void
      removeHTMLFromText_Should_RemoveHtmlAndJavascriptFromText_And_ShouldNot_RemoveNewlines() {
    assertEquals(TEXT_WITH_NEWLINE, helper.removeHTMLFromText(TEXT_WITH_NEWLINE_AND_HTML_AND_JS));
  }

  @Test
  void unescapeHtml_Should_ConvertHtmlEntity() {
    assertEquals(TEXT_WITH_UNESCAPED_HTML_ENTITY, helper.unescapeHtml(TEXT_WITH_HTML_ENTITY));
  }

  @Test
  void removeHTMLFromText_Should_ThrowInternalServerErrorException_When_removeTextFromNull() {
    assertThrows(InternalServerErrorException.class, () -> helper.removeHTMLFromText(null));
  }
}
