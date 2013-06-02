package by.bsu.statetask.entity;

import by.bsu.statetask.memento.Memento;
import by.bsu.statetask.orderstate.EmptyState;
import by.bsu.statetask.orderstate.OrderState;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author Elena Vizgalova
 */
public class Order {
    private Map<Item, Integer> itemMap;
    private String orderId;
    private String address;
    
    private OrderState state;

    public Order() {
        state = new EmptyState( this );
        itemMap = new HashMap<>();
        orderId = "1N";
    }
    
    public Map<Item, Integer> getItemMap() {
        return itemMap;
    }

    public void setItemMap(Map<Item, Integer> itemMap) {
        this.itemMap = itemMap;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    
    public OrderState getState() {
        return state;
    }

    public void setState(OrderState state) {
        this.state = state;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 19 * hash + Objects.hashCode(this.itemMap);
        hash = 19 * hash + Objects.hashCode(this.orderId);
        hash = 19 * hash + Objects.hashCode(this.address);
        hash = 19 * hash + Objects.hashCode(this.state);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (!Objects.equals(this.itemMap, other.itemMap)) {
            return false;
        }
        if (!Objects.equals(this.orderId, other.orderId)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order{" + "itemMap=" + itemMap + ", orderId=" + orderId 
                + ", address=" + address + ", state=" + state + '}';
    }
    
    public void addProduct( Item item, int quantity ) {
        state.addProduct( item, quantity );
    }
    
    public void confirmOrder() {
        state.confirmOrder();
    }
    
    public void closeOrder() {
        state.closeOrder();
    }
    
    public void sendOrder() {
        state.sendOrder();
    }
    
    public void payOrder() {
        state.payOrder();
    }
    
    public void addItemIntoMap( Item item, int quantity ) {
        itemMap.put( item, quantity );
    }
    
    public void setMemento( Memento memento ) {
        state = memento.getState();
        itemMap = memento.getItemMap();
        state.setOrder( this );
    }
    
    public Memento getMemento() {
        return new Memento( state, itemMap );
    }
    
}
