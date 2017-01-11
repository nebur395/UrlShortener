package urlshortener.common.domain;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayObject implements Delayed {
    private long startTime;

    public DelayObject(long delay) {
        this.startTime = System.currentTimeMillis() + delay;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        long diff = startTime - System.currentTimeMillis();
        return unit.convert(diff, TimeUnit.SECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (this.startTime < ((DelayObject) o).startTime) {
            return -1;
        }
        if (this.startTime > ((DelayObject) o).startTime) {
            return 1;
        }
        return 0;
    }

    @Override
    public String toString() {
        return "{ startTime=" + startTime + '}';
    }
}
