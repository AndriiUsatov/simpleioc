package pizza.repo;


import pizza.repository.Pizza;
import pizza.repository.PizzaType;

import java.util.ArrayList;
import java.util.List;

public class PizzaRepo {
    private List<Pizza> pizzas = new ArrayList<Pizza>(){{
        add(new Pizza(1, "Hawaii", PizzaType.PIZZA_ONE, 100));
        add(new Pizza(2, "Cheese", PizzaType.PIZZA_TWO, 150));
        add(new Pizza(3, "Chicken", PizzaType.PIZZA_ONE, 120));
        add(new Pizza(4, "Bacon", PizzaType.PIZZA_THREE, 130));
        add(new Pizza(5, "Margarita", PizzaType.PIZZA_THREE, 100));
    }};

    public List<Pizza> getPizzas() {
        return pizzas;
    }

    public void addPizza(Pizza pizza) {
        pizzas.add(pizza);
    }

    public int getLastId() {
        return (pizzas.size() == 0) ? 0 : pizzas.get(pizzas.size()-1).getPizzaId();
    }


}
