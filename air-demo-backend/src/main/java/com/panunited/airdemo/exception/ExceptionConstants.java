package com.panunited.airdemo.exception;

public final class ExceptionConstants {

    public static final String LOGIN_BAD_CREDENTIAL = "Invalid Username or Password";
    public static final String LOGIN_AUTHENTICATION_SERVICE_ISSUE = "Authentication Service Issue";
    public static final String LOGIN_AUTHENTICATION_CREDENTIALS_NOT_FOUND = "Authentication Token Not Found";
    public static final String LOGIN_AUTHENTICATION_CREDENTIALS_EXPIRED = "Authentication Token Expired";
    public static final String LOGIN_INACTIVE_ACCOUNT = "Inactive Account";
    public static final String NOT_FOUND = "No %s Found";
    public static final String INVALID_PASSWORD = "Invalid Password";
    public static final String INVALID_OLD_PASSWORD = "Invalid Old Password";
    public static final String NEW_PASSWORD_AND_CONFIRMED_PASSWORD_NOT_SAME = "New Password and Confirmed New Password not same";
    public static final String NEW_PASSWORD_SAME_WITH_CURRENT_PASSWORD = "Current password and new password cannot be the same";
    public static final String OLD_PASSWORD_USED = "Password has been used already";
    public static final String INVALID_NEW_PASSWORD = """
				Password must contain the following: \n
				- Minimum length of 4 \n
				- Maximum length of 12 \n
				- Must contain one capital letter (A-Z) \n
				- Must contain one letter (a-z) \n
				- Must contain one number (0-9) \n
				- Must not contain any special characters \n
				- Must start with lower case letter \n
			""";
    public static final String INVALID_EMAIL_PATTERN = "Invalid Email Pattern";
    public static final String INVALID_EMAIL = "Email Address not registered with us";
}

