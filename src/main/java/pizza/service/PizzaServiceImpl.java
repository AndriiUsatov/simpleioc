package pizza.service;

import pizza.dao.PizzaDao;
import pizza.repository.Pizza;
import pizza.repository.PizzaType;

import java.util.List;

public class PizzaServiceImpl implements PizzaService {
    private PizzaDao pizzaDao;

    public PizzaServiceImpl(PizzaDao pizzaDao) {
        this.pizzaDao = pizzaDao;
    }

    @Override
    public List<Pizza> getPizzaByType(PizzaType pizzaType) {
        return pizzaDao.getPizzaByType(pizzaType);
    }

    @Override
    public List<Pizza> getAllPizza() {
        return pizzaDao.getAllPizza();
    }

    @Override
    public Pizza getPizzaByName(String pizzaName) {
        return pizzaDao.getPizzaByName(pizzaName);
    }
}
