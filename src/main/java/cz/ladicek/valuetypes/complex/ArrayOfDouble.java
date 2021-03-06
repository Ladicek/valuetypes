package cz.ladicek.valuetypes.complex;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.profile.LinuxPerfNormProfiler;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 2, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ArrayOfDouble {
    @Param({"10", "100", "1000", "10000", "100000", "1000000", "10000000"})
    int size;

    double[] complexNumbers;

    @Setup
    public void setUp() {
        Random random = new Random();
        complexNumbers = new double[2 * size];
        for (int i = 0; i < size; i++) {
            complexNumbers[i] = random.nextInt(1000);
        }
    }

    @Benchmark
    public double sum() {
        double re = 0;
        double im = 0;
        for (int i = 0; i < size; i += 2) {
            re += complexNumbers[i];
            im += complexNumbers[i + 1];
        }

        return re + im;
    }

    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(ArrayOfDouble.class.getSimpleName())
                .addProfiler(LinuxPerfNormProfiler.class, "-events=L1-dcache-load-misses,cache-misses")
                .build()
        ).run();
    }
}
