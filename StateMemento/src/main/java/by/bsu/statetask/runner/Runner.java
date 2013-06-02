package by.bsu.statetask.runner;

import by.bsu.statetask.entity.Item;
import by.bsu.statetask.entity.Order;
import by.bsu.statetask.entity.OrderHandler;

public class Runner {
    public static void main( String[] args ) {
        Order order = new Order();
        System.out.println( order.getState().getStateDescription() );
        
        order.confirmOrder();
        order.payOrder();
        
        Item item = new Item();
        item.setName("newItem");
        item.setProductId("1N");
        order.addProduct( item, 2 );
        System.out.println( order.getState().getStateDescription() );

        order.payOrder();
        order.confirmOrder();
        System.out.println( order.getState().getStateDescription() );
        
        OrderHandler orderHandler = new OrderHandler();
        orderHandler.saveState( order );
        Order orderNew = orderHandler.loadOrder();
        
        System.out.println( "orderNew: " + orderNew );
        System.out.println( "order: " + order );
        order.closeOrder();
        System.out.println( order.getState().getStateDescription() );
    }
}
