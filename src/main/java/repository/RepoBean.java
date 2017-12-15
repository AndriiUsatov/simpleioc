package repository;

import ioc.Benchmark;

public class RepoBean implements RepoBeanInterface {

    public RepoBean() {
        System.out.println("Created");
    }

    public void init() {
        System.out.println("Init is called");
    }

    @Override
    @Benchmark
    public double calculate() {
        double sum = 0;
        for (int i = 0; i < 100; i++) {
            double x = sum;
            sum += Math.sin(x) * Math.sin(x) + Math.cos(x) * Math.cos(x);
        }
        return sum;
    }
}
