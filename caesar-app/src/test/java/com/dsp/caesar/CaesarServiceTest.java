package com.dsp.caesar;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for CaesarService — encryption, decryption, wraparound,
 * case preservation, non-letter chars, and brute force.
 */
class CaesarServiceTest {

    private CaesarService service;

    @BeforeEach
    void setUp() {
        service = new CaesarService();
    }

    // ---- Basic encryption ----

    @Test
    @DisplayName("Encrypt 'HELLO' with shift 3 → 'KHOOR'")
    void testBasicEncryption() {
        assertEquals("KHOOR", service.encrypt("HELLO", 3));
    }

    @Test
    @DisplayName("Encrypt 'abc' with shift 1 → 'bcd'")
    void testBasicEncryptionLowercase() {
        assertEquals("bcd", service.encrypt("abc", 1));
    }

    // ---- Decryption round-trip ----

    @Test
    @DisplayName("Decrypt reverses encrypt for any shift")
    void testDecryptionRoundTrip() {
        String original = "The Quick Brown Fox Jumps Over 13 Lazy Dogs!";
        for (int shift = 1; shift <= 25; shift++) {
            String encrypted = service.encrypt(original, shift);
            String decrypted = service.decrypt(encrypted, shift);
            assertEquals(original, decrypted,
                    "Round-trip failed for shift " + shift);
        }
    }

    // ---- Wraparound ----

    @Test
    @DisplayName("Wraparound: 'Y' with shift 3 → 'B'")
    void testWraparound() {
        assertEquals('B', service.shiftChar('Y', 3));
    }

    @Test
    @DisplayName("Wraparound: 'Z' with shift 1 → 'A'")
    void testWraparoundZtoA() {
        assertEquals('A', service.shiftChar('Z', 1));
    }

    @Test
    @DisplayName("Wraparound: 'z' with shift 1 → 'a'")
    void testWraparoundLowercase() {
        assertEquals('a', service.shiftChar('z', 1));
    }

    @Test
    @DisplayName("Encrypt 'XYZ' with shift 3 → 'ABC'")
    void testWraparoundString() {
        assertEquals("ABC", service.encrypt("XYZ", 3));
    }

    // ---- Case preservation ----

    @Test
    @DisplayName("Case is preserved during encryption")
    void testCasePreservation() {
        String result = service.encrypt("Hello World", 5);
        assertEquals("Mjqqt Btwqi", result);
        assertTrue(Character.isUpperCase(result.charAt(0)));
        assertTrue(Character.isLowerCase(result.charAt(1)));
    }

    // ---- Non-letter characters ----

    @Test
    @DisplayName("Digits, spaces, and punctuation are unchanged")
    void testNonLetterCharacters() {
        String input = "Test 123 !@#";
        String result = service.encrypt(input, 5);
        assertEquals("Yjxy 123 !@#", result);
    }

    @Test
    @DisplayName("shiftChar returns non-letters unchanged")
    void testShiftCharNonLetter() {
        assertEquals('5', service.shiftChar('5', 3));
        assertEquals(' ', service.shiftChar(' ', 10));
        assertEquals('!', service.shiftChar('!', 7));
    }

    // ---- Negative shift (used internally by decrypt) ----

    @Test
    @DisplayName("Negative shift works correctly")
    void testNegativeShift() {
        assertEquals('A', service.shiftChar('D', -3));
    }

    // ---- Brute force ----

    @Test
    @DisplayName("Brute force returns exactly 25 results (shifts 1-25)")
    void testBruteForceReturns25Results() {
        List<BruteForceResult> results = service.bruteForce("KHOOR");
        assertEquals(25, results.size());
    }

    @Test
    @DisplayName("Brute force contains the correct decryption")
    void testBruteForceContainsCorrectAnswer() {
        List<BruteForceResult> results = service.bruteForce("KHOOR");
        BruteForceResult shiftThree = results.stream()
                .filter(r -> r.getShift() == 3)
                .findFirst()
                .orElseThrow();
        assertEquals("HELLO", shiftThree.getResult());
    }

    @Test
    @DisplayName("Brute force shifts are numbered 1 through 25")
    void testBruteForceShiftNumbers() {
        List<BruteForceResult> results = service.bruteForce("ABC");
        for (int i = 0; i < results.size(); i++) {
            assertEquals(i + 1, results.get(i).getShift());
        }
    }
}
