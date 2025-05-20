package com.thejaxen.thejaxendemobank.utils;

import java.math.BigDecimal;
import java.time.Year;

public class AccountUtils {

    public static final String ACCOUNT_EXISTS_CODE = "001";

    public static final String ACCOUNT_EXISTS_MESSAGE = "Account already exists.";

    public static final String ACCOUNT_CREATION_SUCCESS = "002";

    public static final String ACCOUNT_CREATION_MESSAGE = "Account creation successful.";

    public static final String ACCOUNT_NOT_EXISTS_CODE = "003";

    public static final String ACCOUNT_NOT_EXISTS_MESSAGE = "User with the given account number does not exist.";

    public static final String ACCOUNT_FOUND_CODE = "004";

    public static final String ACCOUNT_FOUND_MESSAGE = "Account found.";

    public static final String ACCOUNT_CREDITED_SUCCESS = "005";

    public static final String ACCOUNT_CREDITED_MESSAGE = "Credit successful.";

    public static String generateAccountNumber() {
        //Takes the year and just random six digits.

        Year currentYear = Year.now();
        int min = 100000;
        int max = 999999;

        //Generating random account number function

        int randNumber = (int) Math.floor(Math.random()*(max-min+1));

        //Converting the current year and random number to string.

        String year = String.valueOf(currentYear);
        String randomNumber = String.valueOf(randNumber);
        StringBuilder accountNumber = new StringBuilder();

        accountNumber.append(year).append(randomNumber);

        return accountNumber.toString();
    }

}
