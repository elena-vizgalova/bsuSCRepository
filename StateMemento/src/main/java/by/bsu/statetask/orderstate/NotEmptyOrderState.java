package by.bsu.statetask.orderstate;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;

/**
 *
 * @author Elena Vizgalova
 */
public class NotEmptyOrderState extends OrderState {

    public NotEmptyOrderState( Order order ) {
        setOrder( order );
    }

    @Override
    public void addProduct(Item item, int quantity) {
        getOrder().addItemIntoMap( item, quantity );
        System.out.println("You've successfully add item into order!");
    }

    @Override
    public void confirmOrder() {
        System.out.println("You've successfully confirmed order!");
        getOrder().setState( new ConfirmedState( getOrder() ) );
    }

    @Override
    public void closeOrder() {
        System.out.println("You've successfully closed the order!");
        getOrder().setState( new ClosedState( getOrder() ) );
    }

    @Override
    public void sendOrder() {
        System.out.println("You can't send order before confirming and paying it!");
    }

    @Override
    public void payOrder() {
        System.out.println("You can't pay order before confirming it!");
    }

    @Override
    public String getStateDescription() {
        return "NOT_EMPTY";
    }
    
}
