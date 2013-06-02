package by.bsu.statetask.orderstate;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;

/**
 *
 * @author Elena Vizgalova
 */
public class ConfirmedState extends OrderState {

    public ConfirmedState( Order order ) {
        setOrder( order );
    }
    
    @Override
    public void confirmOrder() {
        System.out.println( "The order is confirmed!" );
    }

    @Override
    public void closeOrder() {
        System.out.println("You've successfully closed the order!");
        getOrder().setState( new ClosedState( getOrder() ) );
    }

    @Override
    public void sendOrder() {
        System.out.println("You can't send order before paying!");
    }

    @Override
    public void payOrder() {
        System.out.println( "You've successfully payed the order!" );
        getOrder().setState( new PayedState( getOrder() ) );
    }

    @Override
    public String getStateDescription() {
        return "CONFIRMED";
    }

    @Override
    public void addProduct(Item item, int quantity) {
        System.out.println("You can't add product into confirmed order!");
    }
    
}
