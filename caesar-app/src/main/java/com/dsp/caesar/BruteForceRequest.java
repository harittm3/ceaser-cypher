package com.dsp.caesar;

import jakarta.validation.constraints.NotBlank;

/**
 * Request body for the brute-force endpoint. Only needs the ciphertext.
 */
public class BruteForceRequest {

    @NotBlank(message = "Text must not be blank")
    private String text;

    // --- Constructors ---

    public BruteForceRequest() {
    }

    public BruteForceRequest(String text) {
        this.text = text;
    }

    // --- Getters & Setters ---

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

