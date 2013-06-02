package by.bsu.statetask.orderstate;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;

/**
 *
 * @author Elena Vizgalova
 */
public class SentState extends OrderState {

    public SentState( Order order ) {
        setOrder( order );
    }

    @Override
    public void addProduct(Item item, int quantity) {
        System.out.println("You can't add product into sent order!");
    }

    @Override
    public void confirmOrder() {
        System.out.println("The order has already been confirmed!");
    }

    @Override
    public void closeOrder() {
        System.out.println("You've successfully closed order!");
        getOrder().setState( new ClosedState( getOrder() ) );
    }

    @Override
    public void sendOrder() {
        System.out.println("The order has already been sent!");
    }

    @Override
    public void payOrder() {
        System.out.println("The order has already been payed!");
    }

    @Override
    public String getStateDescription() {
        return "SENT";
    }
    
}
