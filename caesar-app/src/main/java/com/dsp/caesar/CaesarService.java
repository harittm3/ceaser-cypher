package com.dsp.caesar;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service that implements the Caesar Cipher algorithm.
 *
 * Algorithm:
 *   Encryption:  E(x) = (x + k) mod 26
 *   Decryption:  D(x) = (x - k) mod 26   (same as encrypting with -k)
 *
 * x = zero-based index of the letter (A=0, B=1, ... Z=25), k = shift key.
 * Non-letter characters pass through unchanged. Case is preserved.
 */
@Service
public class CaesarService {

    /**
     * Shifts a single character by the given amount.
     * Uppercase stays uppercase, lowercase stays lowercase,
     * everything else (digits, spaces, punctuation) is returned as-is.
     * Negative shifts are fine (used by decrypt).
     *
     * Formula: E(x) = (x + k) mod 26
     */
    public char shiftChar(char c, int shift) {
        if (Character.isUpperCase(c)) {
            // Map to 0-25, shift, wrap with floorMod (handles negatives), map back
            return (char) ('A' + Math.floorMod((c - 'A') + shift, 26));
        } else if (Character.isLowerCase(c)) {
            // Same thing for lowercase
            return (char) ('a' + Math.floorMod((c - 'a') + shift, 26));
        }
        // Not a letter — return as-is
        return c;
    }

    /**
     * Encrypts the plaintext by shifting every letter forward.
     * E(x) = (x + k) mod 26
     */
    public String encrypt(String text, int shift) {
        StringBuilder sb = new StringBuilder(text.length());
        for (char c : text.toCharArray()) {
            sb.append(shiftChar(c, shift));
        }
        return sb.toString();
    }

    /**
     * Decrypts the ciphertext by shifting backward (just encrypt with -shift).
     * D(x) = (x - k) mod 26
     */
    public String decrypt(String text, int shift) {
        // D(x) = E(x, -shift)
        return encrypt(text, -shift);
    }

    /**
     * Tries all 25 possible shifts on the ciphertext (brute force).
     * Works because Caesar only has 26 possible keys.
     */
    public List<BruteForceResult> bruteForce(String text) {
        List<BruteForceResult> results = new ArrayList<>();
        for (int shift = 1; shift <= 25; shift++) {
            // Try decrypting with each possible shift
            results.add(new BruteForceResult(shift, decrypt(text, shift)));
        }
        return results;
    }
}
