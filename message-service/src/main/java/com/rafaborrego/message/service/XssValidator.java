package com.rafaborrego.message.service;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

/**
 * Checks if a string contains XSS scripts, e.g. "onclick='stealCookies()'"
 */
class XssValidator {

    static boolean isSafeString(String inputString) {

        return Jsoup.isValid(inputString, Whitelist.none());
    }
}
