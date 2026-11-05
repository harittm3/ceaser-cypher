package com.dsp.caesar;

import jakarta.validation.constraints.NotBlank;

public class BruteForceRequest {

    @NotBlank(message = "Text must not be blank")
    private String text;

    // --- Constructors ---

    public BruteForceRequest() {
    }

    public BruteForceRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}

