package by.bsu.statetask.entity;

import by.bsu.statetask.memento.Memento;

/**
 *
 * @author Elena Vizgalova
 */
public final class OrderHandler {
    private Memento memento;

    public OrderHandler( Memento memento ) {
        this.memento = new Memento( 
                memento.getState(), memento.getItemMap() );
    }

    public Memento getMemento() {
        Memento newMemento = new Memento( memento.getState(), memento.getItemMap() );
        return newMemento;
    }

    public void saveState( Order order ) {
        Memento memento = order.getMemento();
        this.memento = new Memento( 
                memento.getState(), memento.getItemMap() );
    }
    
    public Order loadOrder() {
        Order order = new Order();
        
        order.setMemento( memento );
        System.out.println( order.getState().getStateDescription() );
        System.out.println( order.getItemMap() );
        
        return order;
    }
    
}
