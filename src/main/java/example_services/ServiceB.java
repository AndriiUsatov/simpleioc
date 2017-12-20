package example_services;

public class ServiceB {

    private ServiceA a;


    public ServiceB(ServiceA a) {
        this.a = a;
    }

    public void init(){
        a.setB(this);
    }
}
