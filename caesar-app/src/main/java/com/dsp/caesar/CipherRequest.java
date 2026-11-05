package com.dsp.caesar;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

/**
 * Request body for the encrypt and decrypt endpoints.
 * text = the message, shift = how many positions to shift (1-25).
 */
public class CipherRequest {

    @NotBlank(message = "Text must not be blank")
    private String text;

    @Min(value = 1, message = "Shift must be at least 1")
    @Max(value = 25, message = "Shift must be at most 25")
    private int shift;

    // --- Constructors ---

    public CipherRequest() {
    }

    public CipherRequest(String text, int shift) {
        this.text = text;
        this.shift = shift;
    }

    // --- Getters & Setters ---

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getShift() {
        return shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }
}

