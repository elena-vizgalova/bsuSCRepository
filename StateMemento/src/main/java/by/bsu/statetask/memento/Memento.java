package by.bsu.statetask.memento;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.orderstate.OrderState;
import java.util.Map;

/**
 *
 * @author Elena Vizgalova
 */
public class Memento {
    private OrderState state;
    private Map<Item, Integer> itemMap;
    
    public Memento(OrderState state, Map<Item, Integer> itemMap) {
        this.state = state;
        this.itemMap = itemMap;
    }

    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    public Map<Item, Integer> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Item, Integer> itemMap) {
        this.itemMap = itemMap;
    }
    
}
