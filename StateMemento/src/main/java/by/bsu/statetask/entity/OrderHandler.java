package by.bsu.statetask.entity;

import by.bsu.statetask.memento.Memento;

/**
 *
 * @author Elena Vizgalova
 */
public class OrderHandler {
    private Memento memento;

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }
    
    public void saveState( Order order ) {
        memento = order.getMemento();
    }
    
    public  Order loadOrder() {
        Order order = new Order();
        
        order.setMemento( memento );
        System.out.println( order.getState().getStateDescription() );
        System.out.println( order.getItemMap() );
        
        return order;
    }
    
}
