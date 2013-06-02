package by.bsu.statetask.orderstate;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;

/**
 *
 * @author Elena Vizgalova
 */
public class ClosedState extends OrderState {

    public ClosedState( Order order ) {
        order = null;
        setOrder( order );
    }

    @Override
    public void addProduct(Item item, int quantity) {
        System.out.println("You can't add product into closed order!");
    }

    @Override
    public void confirmOrder() {
        System.out.println("You can't confirm cloded order!");
    }

    @Override
    public void closeOrder() {
        System.out.println("The order is closed!");
    }

    @Override
    public void sendOrder() {
        System.out.println("You can't send closed order!");
    }

    @Override
    public void payOrder() {
        System.out.println("You can't pay closed order!");
    }

    @Override
    public String getStateDescription() {
        return "CLOSED";
    }
    
}
