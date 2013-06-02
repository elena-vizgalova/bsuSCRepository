package by.bsu.statetask.orderstate;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;

/**
 *
 * @author Elena Vizgalova
 */
public abstract class OrderState {
    private Order order;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
    
    public abstract void addProduct( Item item, int quantity );
    public abstract void confirmOrder();
    public abstract void closeOrder();
    public abstract void sendOrder();
    public abstract void payOrder();
    
    public abstract String getStateDescription();
}
