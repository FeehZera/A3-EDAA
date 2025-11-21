package A2;

public class BenchMark {
    private long startTime;
    private long endTime;
    private boolean running;

    public void start() {
        startTime = System.nanoTime();
        running = true;
    }

    public void stop() {
        endTime = System.nanoTime();
        running = false;
    }

    private long getNanoseconds() {
        if (running) {
            return System.nanoTime() - startTime;
        } else {
            return endTime - startTime;
        }
    }

    public double getTime() {
        return getNanoseconds() / 1_000_000.0;
    }

    public void clear() {
        this.startTime = 0;
        this.endTime = 0;
    }
}
