/**
 * Caesar Cipher Tool — Frontend
 * Calls the Spring Boot REST API and updates the page.
 */
(function () {
    "use strict";

    // ---- DOM References ----
    const inputText      = document.getElementById("inputText");
    const outputText     = document.getElementById("outputText");
    const shiftSlider    = document.getElementById("shiftSlider");
    const shiftNumber    = document.getElementById("shiftNumber");
    const shiftDisplay   = document.getElementById("shiftDisplay");
    const shiftBadge     = document.getElementById("shiftBadge");
    const encryptBtn     = document.getElementById("encryptBtn");
    const decryptBtn     = document.getElementById("decryptBtn");
    const bruteForceBtn  = document.getElementById("bruteForceBtn");
    const copyBtn        = document.getElementById("copyBtn");
    const darkModeToggle = document.getElementById("darkModeToggle");
    const errorBox       = document.getElementById("errorBox");
    const bruteSection   = document.getElementById("bruteForceSection");
    const bruteTableBody = document.querySelector("#bruteForceTable tbody");
    const plainAlpha     = document.getElementById("plainAlpha");
    const cipherAlpha    = document.getElementById("cipherAlpha");

    // ---- Alphabet Visualization ----

    /** Build the plain & cipher alphabet rows, reflecting the current shift. */
    function renderAlphabet() {
        const shift = getShift();
        plainAlpha.innerHTML = "";
        cipherAlpha.innerHTML = "";

        for (let i = 0; i < 26; i++) {
            const plainChar  = String.fromCharCode(65 + i);                       // A-Z
            const cipherChar = String.fromCharCode(65 + ((i + shift) % 26));      // shifted

            const ps = document.createElement("span");
            ps.textContent = plainChar;
            plainAlpha.appendChild(ps);

            const cs = document.createElement("span");
            cs.textContent = cipherChar;
            cipherAlpha.appendChild(cs);
        }

        shiftBadge.textContent = "Shift: " + shift;
    }

    // ---- Shift Sync ----

    function getShift() {
        return parseInt(shiftSlider.value, 10);
    }

    function syncShift(source) {
        const val = source === "slider" ? shiftSlider.value : shiftNumber.value;
        shiftSlider.value  = val;
        shiftNumber.value  = val;
        shiftDisplay.textContent = val;
        renderAlphabet();
    }

    shiftSlider.addEventListener("input", () => syncShift("slider"));
    shiftNumber.addEventListener("input", () => syncShift("number"));

    // ---- Error Handling ----

    function showError(msg) {
        errorBox.textContent = msg;
        errorBox.hidden = false;
    }

    function clearError() {
        errorBox.hidden = true;
        errorBox.textContent = "";
    }

    // ---- API Calls ----

    async function callApi(endpoint, body) {
        clearError();
        try {
            const res = await fetch(endpoint, {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify(body),
            });

            const data = await res.json();

            if (!res.ok) {
                showError(data.error || "An unexpected error occurred.");
                return null;
            }
            return data;
        } catch (err) {
            showError("Network error: " + err.message);
            return null;
        }
    }

    // ---- Button Handlers ----

    encryptBtn.addEventListener("click", async () => {
        bruteSection.hidden = true;
        const data = await callApi("/api/encrypt", {
            text: inputText.value,
            shift: getShift(),
        });
        if (data) outputText.value = data.result;
    });

    decryptBtn.addEventListener("click", async () => {
        bruteSection.hidden = true;
        const data = await callApi("/api/decrypt", {
            text: inputText.value,
            shift: getShift(),
        });
        if (data) outputText.value = data.result;
    });

    bruteForceBtn.addEventListener("click", async () => {
        const data = await callApi("/api/bruteforce", {
            text: inputText.value,
        });
        if (data) {
            outputText.value = "";
            bruteTableBody.innerHTML = "";
            data.forEach((row) => {
                const tr = document.createElement("tr");
                const tdShift  = document.createElement("td");
                const tdResult = document.createElement("td");
                tdShift.textContent  = row.shift;
                tdResult.textContent = row.result;
                tr.appendChild(tdShift);
                tr.appendChild(tdResult);
                bruteTableBody.appendChild(tr);
            });
            bruteSection.hidden = false;
        }
    });

    // ---- Copy to Clipboard ----

    copyBtn.addEventListener("click", () => {
        if (!outputText.value) return;
        navigator.clipboard.writeText(outputText.value).then(() => {
            const original = copyBtn.textContent;
            copyBtn.textContent = "✅ Copied!";
            setTimeout(() => (copyBtn.textContent = original), 1500);
        });
    });

    // ---- Dark Mode ----

    function applyTheme(dark) {
        document.documentElement.setAttribute("data-theme", dark ? "dark" : "light");
        darkModeToggle.textContent = dark ? "☀️" : "🌙";
        localStorage.setItem("caesar-dark", dark ? "1" : "0");
    }

    darkModeToggle.addEventListener("click", () => {
        const isDark = document.documentElement.getAttribute("data-theme") === "dark";
        applyTheme(!isDark);
    });

    // Restore saved preference
    if (localStorage.getItem("caesar-dark") === "1") {
        applyTheme(true);
    }

    // ---- Init ----
    renderAlphabet();
})();

