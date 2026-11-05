package com.dsp.caesar;

/**
 * One row of a brute-force result: a shift value + the decrypted text.
 */
public class BruteForceResult {

    private int shift;
    private String result;

    // --- Constructors ---

    public BruteForceResult() {
    }

    public BruteForceResult(int shift, String result) {
        this.shift = shift;
        this.result = result;
    }

    // --- Getters & Setters ---

    // --- Getters & Setters ---

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
