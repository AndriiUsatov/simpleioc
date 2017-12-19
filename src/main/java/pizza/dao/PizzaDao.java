package pizza.dao;

import pizza.repository.Pizza;
import pizza.repository.PizzaType;

import java.util.List;

public interface PizzaDao {
    List<Pizza> getPizzaByType(PizzaType pizzaType);
    List<Pizza> getAllPizza();
    Pizza getPizzaByName(String pizzaName);
}
