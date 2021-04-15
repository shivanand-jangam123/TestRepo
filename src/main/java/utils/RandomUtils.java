package utils;

import java.security.SecureRandom;

public class RandomUtils {

  private RandomUtils() {}

  static SecureRandom random = new SecureRandom();
  private static int maxRange = 1000000;

  public static int generateInt() {
    return generateInt(maxRange);
  }

  public static String generateText() {
    return "AutoTest" + generateInt(maxRange);
  }

  public static int generateInt(int n) {
    return random.nextInt(n);
  }
}
