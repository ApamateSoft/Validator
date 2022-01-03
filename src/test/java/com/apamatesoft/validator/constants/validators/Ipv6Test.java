package com.apamatesoft.validator.constants.validators;

import org.junit.jupiter.api.Test;

import static com.apamatesoft.validator.constants.Validators.ipv6;
import static org.junit.jupiter.api.Assertions.*;

public class Ipv6Test {

    @Test
    void notPermit() {
        final String[] strings = {
            null,
            "",
            "text",
            "128",
            "10.0.0.256",
            "10.0.0.0.1",
            "127.0.0.1",
            "192.168.0.109",
            "10.0.0.1",
            "ffff::ffff::ffff",
            "fffff::ffff",
            "fffg::ffff"
        };
        for (String string : strings) {
            if (ipv6(string)) {
                fail();
                break;
            }
        }
        assertFalse(false);
    }

    @Test
    void permit() {
        final String[] strings = {
            "ffff:ffff:ffff:ffff:ffff:ffff:ffff:ffff",
            "ffff::",
            "ffff::ffff",
            "ffff:ffff::ffff"
        };
        for (String string : strings) {
            if (!ipv6(string)) {
                fail();
                break;
            }
        }
        assertTrue(true);
    }

}