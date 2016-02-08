package cz.ladicek.valuetypes;

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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Fork(1)
@BenchmarkMode({Mode.AverageTime})
@OutputTimeUnit(TimeUnit.MILLISECONDS)
public class ArrayTraversal {
    @Param({
            "1",        //   4  B
            "2",        //   8  B
            "4",        //  16  B
            "8",        //  32  B
            "16",       //  64  B
            // ------------------ cache line size
            "32",       // 128  B
            "64",       // 256  B
            "128",      // 512  B
            "256",      //   1 kB
            "512",      //   2 kB
            "1024",     //   4 kB
            "2048",     //   8 kB
            "4096",     //  16 kB
            "8192",     //  32 kB
            // ------------------ L1 cache size
            "16384",    //  64 kB
            "32768",    // 128 kB
            "65536",    // 256 kB
            // ------------------ L2 cache size
            "131072",   // 512 kB
            "262144",   //   1 MB
            "524288",   //   2 MB
            "1048576",  //   4 MB
            // ------------------ L3 cache size
            "2097152",  //   8 MB
            "4194304",  //  16 MB
            "8388608",  //  32 MB
            "16777216", //  64 MB
            "33554432", // 128 MB
            "67108864"  // 256 MB
    })
    int size;

    int[] numbers;

    @Setup
    public void setUp() {
        Random random = new Random();
        numbers = new int[size];
        for (int i = 0; i < size; i++) {
            numbers[i] = random.nextInt(1000);
        }
    }

    @Benchmark
    public void predictableTraversal() {
        int steps = 64 * 1024 * 1024;
        int lengthMod = numbers.length - 1;
        for (int i = 0; i < steps; i++) {
            // if numbers.length is power of 2, x & lengthMod == x % numbers.length
            numbers[(i * 16) & lengthMod]++;
        }
    }

    // uses xorshift* random number generator
    @Benchmark
    public void randomTraversal() {
        int steps = 64 * 1024 * 1024;
        int lengthMod = numbers.length - 1;

        long rnd = System.nanoTime(); // seed
        int next = 0;

        for (int i = 0; i < steps; i++) {
            numbers[next]++;

            rnd ^= (rnd << 21);
            rnd ^= (rnd >>> 35);
            rnd ^= (rnd << 4);
            next = (int) (rnd & lengthMod);
        }
    }

    public static void main(String[] args) throws RunnerException {
        new Runner(new OptionsBuilder()
                .include(ArrayTraversal.class.getSimpleName())
                .build()
        ).run();
    }
}
