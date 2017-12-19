package pizza.service;

import pizza.repository.Pizza;
import pizza.repository.PizzaType;

import java.util.List;

public interface PizzaService {
    List<Pizza> getPizzaByType(PizzaType pizzaType);
    List<Pizza> getAllPizza();
    Pizza getPizzaByName(String pizzaName);
}
