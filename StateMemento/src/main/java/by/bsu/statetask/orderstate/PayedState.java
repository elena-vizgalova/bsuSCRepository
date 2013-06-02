package by.bsu.statetask.orderstate;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;

/**
 *
 * @author Elena Vizgalova
 */
public class PayedState extends OrderState {

    public PayedState( Order order ) {
        setOrder( order );
    }

    @Override
    public void addProduct(Item item, int quantity) {
        System.out.println("You can't add product into payed order!");
    }

    @Override
    public void confirmOrder() {
        System.out.println("The order has already been confirmed!");
    }

    @Override
    public void closeOrder() {
        System.out.println("You can't close payed order!");
    }

    @Override
    public void sendOrder() {
        System.out.println("The order is sent!");
        getOrder().setState( new SentState( getOrder() ) );
    }

    @Override
    public void payOrder() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getStateDescription() {
        return "PAYED";
    }
    
}
