package utils;

import java.util.ArrayList;
import java.util.List;

public class KeyStorage<T> {

  private List<T> storage;

  public KeyStorage() {
    storage = new ArrayList<>();
  }

  public boolean isEmpty() {
    return storage.isEmpty();
  }

  public int size() {
    return storage.size();
  }

  public boolean containsKey(T key) {
    return !isEmpty() && storage.contains(key);
  }

  public void add(T key) {
    if (!containsKey(key)) {
      storage.add(key);
    }
  }

  public T getKey(int index) {
    return storage.get(index);
  }

  public T getLastKey() {
    return storage.get(size() - 1);
  }

  public List<T> getAllKeys() {
    return storage;
  }

  public void removeKey(T key) {
    storage.remove(key);
  }

  public void removeLastKey() {
    storage.remove(getLastKey());
  }

  public void clear() {
    storage.clear();
  }

}
