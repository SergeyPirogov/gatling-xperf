package io.xperf.model;

/**
 * Application Performance Index, measuring user satisfaction. The Apdex score converts many measurements into one
 * number on a uniform scale of 0-to-1 (0 = no users satisfied, 1 = all users satisfied). Visit http://www.apdex.org/
 * for more information.
 */
public class Apdex {
    protected static final float DEFAULT_THRESOLD = 1.5f;

    protected final float threshold;

    protected int satisfied, tolerating, frustrated;

    protected Apdex() {
        threshold = DEFAULT_THRESOLD;
    }

    protected Apdex(Float thresold) {
        if (thresold == null) {
            this.threshold = DEFAULT_THRESOLD;
        } else {
            this.threshold = thresold;
        }
    }

    public void addMs(long value) {
        add(value / 1000.0);
    }

    public void add(double value) {
        if (value <= threshold) {
            satisfied++;
        } else if (value <= 4 * threshold) {
            tolerating++;
        } else {
            frustrated++;
        }
    }

    public float getScore() {
        long total = satisfied + tolerating + frustrated;
        if (total == 0) {
            return 0;
        }
        return (float) ((satisfied + (tolerating / 2.0)) / total);
    }

    public Rating getRating() {
        float score = getScore();
        if (score < 0.5) {
            return Rating.Unacceptable;
        } else if (score < 0.7) {
            return Rating.Poor;
        } else if (score < 0.85) {
            return Rating.Fair;
        } else if (score < 0.94) {
            return Rating.Good;
        } else {
            return Rating.Excellent;
        }
    }

    protected enum Rating {
        Unacceptable, Poor, Fair, Good, Excellent
    }
}
