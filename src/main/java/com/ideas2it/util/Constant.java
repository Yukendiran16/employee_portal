package com.ideas2it.util;

import java.time.LocalDate;

public class Constant {

    static final String YEAR_REGEX = "(19|2[0-5])[0-9]{2}";
    static final String MONTH_REGEX = "(0[1-9]|1[012])";
    static final String DATE_REGEX1 = "(0[1-9]|[12][0-9]|3[0])";
    static final String DATE_REGEX2 = "(0[1-9]|[12][0-9]|3[01])";
    static final String DATE_REGEX3 = "(0[1-9]|[12][0-9])";
    static final String DATE_REGEX4 = "(0[1-9]|[1][0-9]|[2][0-8])";
    static final String MONTH_31_DAYS = YEAR_REGEX + MONTH_REGEX + DATE_REGEX2;
    static final String MONTH_30_DAYS = YEAR_REGEX + MONTH_REGEX + DATE_REGEX1;
    static final String MONTH_29_DAYS = YEAR_REGEX + MONTH_REGEX + DATE_REGEX3;
    static final String MONTH_28_DAYS = YEAR_REGEX + MONTH_REGEX + DATE_REGEX4;
    public static final boolean IS_ACTIVE = false;
    static final String ROLE_1 = "SDE";
    static final String ROLE_2 = "SSD";
    static final String ROLE_3 = "SE";

}
