package pizza.repository;

public class Pizza {
    private int pizzaId;
    private double price;
    private String title;
    private PizzaType pizzaType;

    public Pizza(int pizzaId, String title, PizzaType pizzaType, double price) {
        this(title, pizzaType, price);
        this.pizzaId = pizzaId;
    }

    public Pizza(String title, PizzaType pizzaType, double price) {
        this.title = title;
        this.pizzaType = pizzaType;
        this.price = price;
    }

    public int getPizzaId() {
        return pizzaId;
    }

    public void setPizzaId(int pizzaId) {
        this.pizzaId = pizzaId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public PizzaType getPizzaType() {
        return pizzaType;
    }

    public void setPizzaType(PizzaType pizzaType) {
        this.pizzaType = pizzaType;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "pizzaId=" + pizzaId +
                ", price=" + price +
                ", title='" + title + '\'' +
                ", pizzaType=" + pizzaType +
                '}';
    }
}
