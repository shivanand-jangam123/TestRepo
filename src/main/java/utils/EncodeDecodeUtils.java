package utils;

import java.util.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncodeDecodeUtils {

  private EncodeDecodeUtils() {}

  private static Logger log = LoggerFactory.getLogger(EncodeDecodeUtils.class);

  public static String getEncodedText(String strInputText) {
    try {
      Base64.Encoder encoder = Base64.getMimeEncoder();
      return encoder.encodeToString(strInputText.getBytes());
    } catch (Exception exception) {
      log.error(exception.getMessage());
      return null;

    }
  }

  public static String getDecodedText(String strInputText) {
    try {
      Base64.Decoder decoder = Base64.getMimeDecoder();
      return new String(decoder.decode(strInputText));

    } catch (Exception exception) {
      log.error(exception.getMessage());
      return null;
    }
  }

  public static boolean isBase64EncriptedText(String strInputText) {
    try {
      new String(Base64.getDecoder().decode(strInputText));
      return true;
    } catch (Exception exception) {
      log.error(exception.getMessage());
      return false;
    }
  }
}
