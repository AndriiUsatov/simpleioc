package pizza.dao;

import pizza.repo.PizzaRepo;
import pizza.repository.Pizza;
import pizza.repository.PizzaType;

import java.util.List;
import java.util.stream.Collectors;

public class PizzaDaoImpl implements PizzaDao {
    private PizzaRepo pizzaRepo;

    public PizzaDaoImpl(PizzaRepo pizzaRepo) {
        this.pizzaRepo = pizzaRepo;
    }

    @Override
    public List<Pizza> getPizzaByType(PizzaType pizzaType) {
        List<Pizza> pizzas = pizzaRepo.getPizzas();
        return pizzas.stream().
                filter(pizza -> {return pizza.getPizzaType() == pizzaType;  }).
                collect(Collectors.toList());
    }

    @Override
    public List<Pizza> getAllPizza() {
        return pizzaRepo.getPizzas();
    }

    @Override
    public Pizza getPizzaByName(String pizzaName) {
        List<Pizza> pizzas = pizzaRepo.getPizzas();
        return pizzas.stream().filter(pizza -> { return pizza.getTitle().equals(pizzaName); }).findFirst().get();
    }
}
