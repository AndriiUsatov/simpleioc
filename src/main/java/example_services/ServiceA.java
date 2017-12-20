package example_services;

public class ServiceA {

    private ServiceB b;

    public ServiceA() {
    }

    public ServiceA(ServiceB b) {
        this.b = b;
    }

    public void setB(ServiceB b) {
        this.b = b;
    }
}
