package cz.ladicek.valuetypes.complex;

import org.ObjectLayout.CtorAndArgs;
import org.ObjectLayout.CtorAndArgsProvider;
import org.ObjectLayout.StructuredArray;
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
import org.openjdk.jmh.infra.Blackhole;
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
public class StructuredArrayExample {
    @Param({"10", "100", "1000", "10000", "100000", "1000000", "10000000"})
    int size;

    StructuredArray<Complex> complexNumbers;

    @Setup
    public void setUp() {
        Random random = new Random();
        CtorAndArgsProvider<Complex> ctorAndArgsProvider = context -> new CtorAndArgs<>(Complex.class,
                new Class[]{double.class, double.class}, random.nextInt(1000), random.nextInt(1000));
        complexNumbers = StructuredArray.newInstance(Complex.class, ctorAndArgsProvider, size);
    }

    @Benchmark
    public void sum(Blackhole blackhole) {
        double re = 0;
        double im = 0;
        for (int i = 0; i < size; i++) {
            Complex complexNumber = complexNumbers.get(i);
            re += complexNumber.re;
            im += complexNumber.im;
        }

        blackhole.consume(re);
        blackhole.consume(im);
    }

    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(StructuredArrayExample.class.getSimpleName())
                .build()
        ).run();
    }
}
