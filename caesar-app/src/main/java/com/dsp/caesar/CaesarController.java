package com.dsp.caesar;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Caesar Cipher operations.
 *
 * Endpoints:
 *   POST /api/encrypt    – encrypt plaintext with a given shift
 *   POST /api/decrypt    – decrypt ciphertext with a given shift
 *   POST /api/bruteforce – try all 25 shifts on ciphertext
 */
@RestController
@RequestMapping("/api")
public class CaesarController {

    private final CaesarService caesarService;

    public CaesarController(CaesarService caesarService) {
        this.caesarService = caesarService;
    }

    // POST /api/encrypt  →  {"result": "..."}

    @PostMapping("/encrypt")
    public Map<String, String> encrypt(@Valid @RequestBody CipherRequest request) {
        String ciphertext = caesarService.encrypt(request.getText(), request.getShift());
        return Map.of("result", ciphertext);
    }

    // POST /api/decrypt  →  {"result": "..."}

    @PostMapping("/decrypt")
    public Map<String, String> decrypt(@Valid @RequestBody CipherRequest request) {
        String plaintext = caesarService.decrypt(request.getText(), request.getShift());
        return Map.of("result", plaintext);
    }

    // POST /api/bruteforce  →  [{shift, result}, ...]

    @PostMapping("/bruteforce")
    public List<BruteForceResult> bruteForce(@Valid @RequestBody BruteForceRequest request) {
        return caesarService.bruteForce(request.getText());
    }
}



