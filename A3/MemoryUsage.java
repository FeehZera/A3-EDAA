package A3;

public class MemoryUsage {

    private long usedBefore;
    private long usedAfter;
    private boolean started;

    public void start() {
        Runtime runtime = Runtime.getRuntime();
        runtime.gc(); // tenta limpar um pouco antes de medir
        usedBefore = runtime.totalMemory() - runtime.freeMemory();
        started = true;
    }

    public void stop() {
        if (!started) return;
        Runtime runtime = Runtime.getRuntime();
        runtime.gc();
        usedAfter = runtime.totalMemory() - runtime.freeMemory();
        started = false;
    }

    public long getUsedBytes() {
        return usedAfter - usedBefore;
    }

    public double getUsedKilobytes() {
        return getUsedBytes() / 1024.0;
    }

    public double getUsedMegabytes() {
        return getUsedBytes() / (1024.0 * 1024.0);
    }
}
