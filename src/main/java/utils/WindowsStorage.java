package utils;

import java.util.List;

public class WindowsStorage {

  private WindowsStorage() {

  }

  private static KeyStorage<String> windows = new KeyStorage<>();

  public static void addWindow(String windowHandle) {
    windows.add(windowHandle);
  }

  public static String getActiveWindow() {
    return windows.getLastKey();
  }

  public static String getPreviousWindow() {
    return windows.getKey(windows.size() - 2);
  }

  public static List<String> getAllWindows() {
    return windows.getAllKeys();
  }

  public static String popWindow() {
    String handle = getActiveWindow();
    windows.removeKey(handle);
    removeActiveWindow();
    return handle;
  }

  public static void removeActiveWindow() {
    windows.removeLastKey();
  }

  public static void clear() {
    windows.clear();
  }
}
