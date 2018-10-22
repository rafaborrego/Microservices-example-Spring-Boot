package com.rafaborrego.message.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(JUnit4.class)
public class XssValidatorTest {

    private final static String MESSAGE_WITH_PLAIN_TEXT = "Sample message";
    private final static String MESSAGE_WITH_XSS = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";

    @Test
    public void shouldReturnTrueWhenMessageContainsPlainText() {

        assertTrue(XssValidator.isSafeString(MESSAGE_WITH_PLAIN_TEXT));
    }

    @Test
    public void shouldReturnFalseWhenMessageContainsHtmlWithXssScript() {

        assertFalse(XssValidator.isSafeString(MESSAGE_WITH_XSS));
    }
}
