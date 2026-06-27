/**
 * QuizWiz Timer Module
 *
 * Encapsulated as a single QuizTimer object (#12) to avoid polluting the
 * global scope. All state and behaviour live here.
 *
 * Features:
 *  #9  Configurable duration via data-quiz-duration on <body>
 *  #10 Visual progress bar that shrinks and changes colour over time
 *  #11 Last-10-seconds urgency: faster flash, red glow, short beep
 *  #12 QuizTimer utility object — no loose globals
 *  #13 started_at hidden input stamped on load for server-side validation
 *
 * Previously implemented (preserved):
 *  #1  Negative-timer guard
 *  #2  1-minute warning banner
 *  #3  onbeforeunload disabled on submission
 *  #5  Pause/resume via Page Visibility API + suspicious-activity logging
 *  #6  localStorage persistence across page refreshes
 *  #7  localStorage cleared on submission
 *  #8  DOMContentLoaded initialisation
 */

const QuizTimer = {

    // ------------------------------------------------------------------ config
    STORAGE_KEY: 'quizwiz_remaining_time',

    /** Read duration from <body data-quiz-duration="600">, fallback to 600 s */
    get DURATION() {
        const attr = parseInt(document.body.dataset.quizDuration, 10);
        return (!isNaN(attr) && attr > 0) ? attr : 600;
    },

    // ------------------------------------------------------------------ state
    timeRemaining: 0,
    interval:      null,
    tabHideCount:  0,
    beepContext:   null,   // Web Audio API context, created lazily

    // --------------------------------------------------------- time formatting
    /**
     * Formats seconds into MM:SS.
     * @param {number} seconds
     * @returns {string}
     */
    formatTime(seconds) {
        const m = Math.floor(seconds / 60);
        const s = seconds % 60;
        return String(m).padStart(2, '0') + ':' + String(s).padStart(2, '0');
    },

    // --------------------------------------------------------- display updates
    /** Updates the MM:SS text, warning classes, progress bar, and critical state. */
    updateDisplay() {
        const timerEl    = document.getElementById('timer');
        const progressEl = document.getElementById('timerProgress');
        const trackEl    = document.getElementById('timerProgressBar');
        const pct        = Math.max(0, (this.timeRemaining / this.DURATION) * 100);

        // --- MM:SS text
        if (timerEl) {
            timerEl.textContent = this.formatTime(this.timeRemaining);

            // Warning colour at ≤ 60 s
            if (this.timeRemaining <= 60) {
                timerEl.classList.add('timer-warning');
            }

            // Critical flash at ≤ 10 s (#11)
            if (this.timeRemaining <= 10) {
                timerEl.classList.add('timer-critical');
            }
        }

        // --- Progress bar (#10)
        if (progressEl) {
            progressEl.style.width = pct + '%';

            progressEl.classList.remove('progress-warning', 'progress-critical');
            if (this.timeRemaining <= 10) {
                progressEl.classList.add('progress-critical');
            } else if (this.timeRemaining <= 60) {
                progressEl.classList.add('progress-warning');
            }
        }

        // --- ARIA live value on the track element
        if (trackEl) {
            trackEl.setAttribute('aria-valuenow', Math.round(pct));
        }

        // --- 1-minute banner (fires exactly once at 60 s)
        if (this.timeRemaining === 60) {
            this.showWarningBanner();
        }

        // --- 10-second beep (#11)
        if (this.timeRemaining === 10) {
            this.playBeep();
        }
    },

    // ------------------------------------------------------------ core ticking
    /** One tick of the countdown. Called every second by setInterval. */
    tick() {
        if (this.timeRemaining <= 0) {
            this.timeRemaining = 0;
            this.saveTime();
            this.updateDisplay();
            clearInterval(this.interval);
            this.submit('auto');
            return;
        }

        this.timeRemaining--;
        this.saveTime();
        this.updateDisplay();

        if (this.timeRemaining <= 0) {
            this.timeRemaining = 0;
            this.saveTime();
            this.updateDisplay();
            clearInterval(this.interval);
            this.submit('auto');
        }
    },

    // --------------------------------------------------------- localStorage
    saveTime() {
        localStorage.setItem(this.STORAGE_KEY, this.timeRemaining);
    },

    /**
     * Loads persisted time. Returns the saved value when valid (> 0 and
     * ≤ configured duration); otherwise returns the full duration.
     */
    loadTime() {
        const saved = parseInt(localStorage.getItem(this.STORAGE_KEY), 10);
        if (!isNaN(saved) && saved > 0 && saved <= this.DURATION) {
            return saved;
        }
        return this.DURATION;
    },

    clearTime() {
        localStorage.removeItem(this.STORAGE_KEY);
    },

    // --------------------------------------------------- submission / cleanup
    /**
     * Submits the quiz form.
     * @param {'auto'|'manual'} reason
     */
    submit(reason) {
        const form = document.getElementById('quizForm');
        if (!form) return;

        this.clearTime();
        window.onbeforeunload = null;

        if (reason === 'auto') {
            console.log('Time expired — auto-submitting quiz.');
        }
        form.submit();
    },

    // ------------------------------------------------------- warning banner
    createWarningBanner() {
        let banner = document.getElementById('timerWarningBanner');
        if (!banner) {
            banner = document.createElement('div');
            banner.id = 'timerWarningBanner';
            banner.className = 'timer-banner';
            banner.setAttribute('role', 'alert');
            banner.setAttribute('aria-live', 'assertive');
            banner.innerHTML =
                '⚠️ Only 1 minute remaining! Please review and submit your answers.' +
                '<button class="banner-close" aria-label="Dismiss warning" ' +
                'onclick="QuizTimer.dismissWarningBanner()">✕</button>';
            document.body.insertBefore(banner, document.body.firstChild);
        }
        return banner;
    },

    showWarningBanner() {
        const banner = this.createWarningBanner();
        requestAnimationFrame(() => banner.classList.add('visible'));
    },

    dismissWarningBanner() {
        const banner = document.getElementById('timerWarningBanner');
        if (banner) banner.classList.remove('visible');
    },

    // ----------------------------------------------------- last-10s beep (#11)
    /**
     * Plays a short 440 Hz beep using the Web Audio API.
     * Fails silently if the browser does not support it.
     */
    playBeep() {
        try {
            if (!this.beepContext) {
                this.beepContext = new (window.AudioContext || window.webkitAudioContext)();
            }
            const ctx        = this.beepContext;
            const oscillator = ctx.createOscillator();
            const gain       = ctx.createGain();

            oscillator.connect(gain);
            gain.connect(ctx.destination);

            oscillator.type      = 'sine';
            oscillator.frequency.value = 880; // Hz — a short high-pitched ping
            gain.gain.setValueAtTime(0.3, ctx.currentTime);
            gain.gain.exponentialRampToValueAtTime(0.0001, ctx.currentTime + 0.4);

            oscillator.start(ctx.currentTime);
            oscillator.stop(ctx.currentTime + 0.4);
        } catch (e) {
            // Web Audio not supported — degrade gracefully
            console.warn('QuizTimer: beep not supported in this browser.');
        }
    },

    // -------------------------------------------- Page Visibility API (#5)
    handleVisibilityChange() {
        if (document.hidden) {
            this.tabHideCount++;
            clearInterval(this.interval);
            this.saveTime();
            console.warn(
                'Suspicious activity: quiz tab hidden (count: ' + this.tabHideCount + ')'
            );
        } else {
            this.interval = setInterval(() => this.tick(), 1000);
            console.log('Quiz tab restored. Resuming timer:', this.formatTime(this.timeRemaining));
        }
    },

    // ------------------------------------------------------- initialisation
    /**
     * Starts the timer. Called on DOMContentLoaded (#8).
     *
     * #9  Reads duration from data-quiz-duration on <body>.
     * #6  Restores time from localStorage (survives refresh).
     * #13 Stamps started_at hidden input for server-side validation.
     */
    start() {
        // #6 Restore persisted time
        this.timeRemaining = this.loadTime();

        // #13 Stamp started_at for server validation (epoch ms)
        const startedAtEl = document.getElementById('startedAt');
        if (startedAtEl && !startedAtEl.value) {
            startedAtEl.value = Date.now();
        }

        // Initial display render
        this.updateDisplay();

        // Start countdown
        this.interval = setInterval(() => this.tick(), 1000);

        // #3 Clear storage + disable unload warning on any form submission
        const form = document.getElementById('quizForm');
        if (form) {
            form.addEventListener('submit', () => {
                this.clearTime();
                window.onbeforeunload = null;
            });
        }

        // #5 Pause/resume on tab visibility change
        document.addEventListener('visibilitychange', () => this.handleVisibilityChange());

        console.log(
            'QuizTimer started — duration:', this.formatTime(this.DURATION),
            '| remaining:', this.formatTime(this.timeRemaining)
        );
    }
};

// #8 DOMContentLoaded — preferred over window.onload
document.addEventListener('DOMContentLoaded', () => QuizTimer.start());

/**
 * #3 Warn before leaving mid-quiz.
 * Cleared automatically before any submission.
 */
window.onbeforeunload = function (e) {
    if (QuizTimer.timeRemaining > 0 && QuizTimer.timeRemaining < QuizTimer.DURATION) {
        const message = 'Your quiz is in progress. Are you sure you want to leave?';
        e.returnValue = message;
        return message;
    }
};
