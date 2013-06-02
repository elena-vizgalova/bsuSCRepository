package by.bsu.statetask.orderstate;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;

/**
 *
 * @author Elena Vizgalova
 */
public class EmptyState extends OrderState {

    public EmptyState( Order order ) {
        setOrder( order );
    }

    @Override
    public void closeOrder() {
        System.out.println("You've closed the empty order!");
        getOrder().setState( new ClosedState( getOrder() ) );
    }

    @Override
    public void sendOrder() {
        System.out.println("You can't send empty order!");
    }

    @Override
    public void payOrder() {
        System.out.println("You can't pay the empty order!");
    }

    @Override
    public void confirmOrder() {
        System.out.println("You can't confirm the empty order!");
    }

    @Override
    public String getStateDescription() {
        return "EMPTY";
    }

    @Override
    public void addProduct(Item item, int quantity) {
        getOrder().addItemIntoMap( item, quantity );
        getOrder().setState( new NotEmptyOrderState( getOrder() ) );
    }
    
}
