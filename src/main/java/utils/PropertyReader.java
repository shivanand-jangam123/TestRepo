package utils;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

public class PropertyReader {

  private static Logger log = LoggerFactory.getLogger(PropertyReader.class);
  private static PropertyReader uniqueInstance;

  private PropertyReader() {}

  public static synchronized PropertyReader getInstance() {
    if (uniqueInstance == null) {
      uniqueInstance = new PropertyReader();
    }
    return uniqueInstance;
  }

  public Properties getProperties(String propertyFile) {
    Properties props = new Properties();
    Reader reader = null;
    try {
      reader = Files.newReader(new File(propertyFile), Charsets.UTF_8);
      props.load(reader);
      return props;
    } catch (IOException e) {
      log.info(e.getMessage());
    } finally {
      if (reader != null) {
        try {
          reader.close();
        } catch (IOException e) {
          log.info(e.getMessage());
        }
      }
    }
    return props;
  }

  public String getProperty(String key, String propertyFile) {
    return getProperties(propertyFile).getProperty(key);
  }

  public String[] getProperties(String key, String propertyFile) {
    return getProperties(propertyFile).getProperty(key).split(",");
  }
}
