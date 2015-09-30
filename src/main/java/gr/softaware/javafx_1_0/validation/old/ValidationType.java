package gr.softaware.javafx_1_0.validation.old;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author siggouroglou@gmail.com
 */
public enum ValidationType implements ValidationOperation {

    REQUIRED() {
                /**
                 *
                 * @param value The value to be validated. Values allowed are
                 * null, Boolean, String, Date or Number.
                 * @param argument The string true case insensitive is required
                 * to validate the value. In other cases returns true.
                 * @return true if the argument is not true or if the argument
                 * is true and the value is not null and a boolean or a non
                 * empty string or a Date or a Number.
                 */
                @Override
                public boolean isValid(Object value, String argument) {
                    // Arg validation and parsing.
                    boolean isRequired = Boolean.valueOf(argument);
                    if (!isRequired) {
                        return true;
                    }

                    // value validation.
                    if (value == null) {
                        return false;
                    } else if (value instanceof Boolean) {
                        return true;
                    } else if (value instanceof String) {
                        return !String.valueOf(value).isEmpty();
                    } else if (value instanceof Date) {
                        return true;
                    } else if (value instanceof Number) {
                        return true;
                    }

                    // If value is not a valid type.
                    throw new ValidationOperationArgException("The value of ValidationType(REQUIRED) is not valid instance.");
                }

                @Override
                public String getErrorMessageGr() {
                    return "Υποχρεωτικό πεδίο.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Required field.";
                }
            },
    EMAIL() {
                /**
                 *
                 * @param value a string representation of an email.
                 * @param argument The string true case insensitive is required
                 * to validate the value. In other cases returns true.
                 * @return true if the argument is not true or when argument is
                 * true and the value is a valid email as String.
                 */
                @Override
                public boolean isValid(Object value, String argument) {
                    // Arg validation and parsing.
                    boolean isEmail = Boolean.valueOf(argument);
                    if (!isEmail) {
                        return true;
                    }

                    if (value instanceof String) {
                        // Check with regular expression.
                        final String EMAIL_PATTERN = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\\\[[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}\\\\])|(([a-zA-Z\\\\-0-9]+\\\\.)+[a-zA-Z]{2,}))$";
                        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
                        Matcher matcher = pattern.matcher(String.valueOf(value));
                        return matcher.matches();
                    }

                    // If value is not a valid type.
                    throw new ValidationOperationArgException("The value of ValidationType(EMAIL) is not valid instance.");
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται ένα έγκυρο email.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter a valid email.";
                }
            },
    DATE_WITH_DOT() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται μια έγκυρη Ημερομηνία (ΗΗ-ΜΜ-ΕΕ).";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter a valid date (DD-MM-YY).";
                }
            },
    NUMBER() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται έναν έγκυρο αριθμό.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter a valid number.";
                }
            },
    FLOAT() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται έναν έγκυρο δεκαδικό αριθμό.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter a valid decimal number.";
                }
            },
    EQUAL_TO() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται τον ίδιο κείμενο ξανά.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter the same text again.";
                }
            },
    MAX_LENGTH() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται λιγότερους από {0} χαρακτήρες.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter less than {0} characters.";
                }
            },
    MIN_LENGTH() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται περισσότερους από {0} χαρακτήρες.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter more than {0} characters.";
                }
            },
    MAX() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται μια τιμή μεγαλύτερη ή ίση απο {0}.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter a value greater than or equal to {0}.";
                }
            },
    MIN() {
                @Override
                public boolean isValid(Object value, String argument) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public String getErrorMessageGr() {
                    return "Παρακαλώ εισάγεται μια τιμή μικρότερη ή ίση απο {0}.";
                }

                @Override
                public String getErrorMessageEn() {
                    return "Please enter a value less than or equal to {0}.";
                }
            };
}
