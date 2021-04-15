package base;

public class ConstantUtils {

  private ConstantUtils() {}

  public static final String ECOLAB_CONNECT_TITLE = "Ecolab";
  private static final String EXCEL_FILE_DIR =
      System.getProperty("user.dir") + "\\recource-files\\excel\\";

  public static final String MORE_THEN_5MB_SIZE_FILE =
      System.getProperty(EXCEL_FILE_DIR) + "\\recource-files\\FileWithMoreThan5MBSize.JPG";
  public static final String FILE_UPLOAD_ERROR_MESSAGE =
      "File size cannot exceed 5MB. Selected file size is";
  public static final String EMAIL_ERROR_MESSAGE =
      "One or more fields are filled out incorrectly. Please check your entries and try again.";
  public static final String INVALID_EMAILID_MESSAGE = "Please enter a valid email address.";
  public static final String ORDER_STATUS = "Completed";
  public static final String CUSTOMER_ORDER_STATUS = "Completed";

  // Common Validation Messages
  public static final String YOUR_SHOPPING_CART_IS_EMPTY = "Your Shopping Cart is empty.";
  public static final String ORDER_INFORMATION_TEXT = "Order Information";
  public static final String ACCOUNTPAGE_ERROR_MESSAGE =
      "If any changes are needed on your Ecolab Account information, please contact Customer Support at866-851-8670";
  public static final String LOGINPAGE_INCORRECT_CREDENTIAL_ERROR_MESSAGE =
      "We cannot find this username or email address.";
  public static final String TERMS_AND_CONDITION_TEXT =
      "By checking out I am agreeing to the Terms and Conditions of Ecolab Inc.";
  public static final String SPECIAL_CHAR_IN_LOCATOR = "\\r\\n|\\r|\\n";
}
