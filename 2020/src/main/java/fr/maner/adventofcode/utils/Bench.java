package fr.maner.adventofcode.utils;

import fr.maner.adventofcode.day16.Day16;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.concurrent.TimeUnit;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Fork(value = 2,
        jvmArgsAppend = {"-server", "-disablesystemassertions"})
@Warmup(iterations = 1, time = 5)
@Measurement(iterations = 3, time = 3)
public class Bench {

    private Day day;

    @Setup(Level.Trial)
    public void setup() throws Exception {
        this.day = new Day16();
    }

    @Benchmark
    public void partOne() {
        try {
            this.day.partOne();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Benchmark
    public void partTwo() {
        try {
            this.day.partTwo();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder().include(Bench.class.getSimpleName()).build();

        new Runner(opt).run();
    }
}