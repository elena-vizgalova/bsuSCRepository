package by.bsu.patterncommand.entity;

import java.util.Objects;

/**
 *
 * @author Elena Vizgalova
 */
public class Order {
    private String curtomerInfo;
    private Cart cart;
    private boolean isPayed;

    public String getCurtomerInfo() {
        return curtomerInfo;
    }

    public void setCurtomerInfo(String curtomerInfo) {
        this.curtomerInfo = curtomerInfo;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public boolean isIsPayed() {
        return isPayed;
    }

    public void setIsPayed(boolean isPayed) {
        this.isPayed = isPayed;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.curtomerInfo);
        hash = 89 * hash + Objects.hashCode(this.cart);
        hash = 89 * hash + (this.isPayed ? 1 : 0);
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
        if (!Objects.equals(this.curtomerInfo, other.curtomerInfo)) {
            return false;
        }
        if (!Objects.equals(this.cart, other.cart)) {
            return false;
        }
        if (this.isPayed != other.isPayed) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order@" + "curtomerInfo=" + curtomerInfo + ", cart=" + cart 
                + ", isPayed=" + isPayed + '}';
    }
    
    
    
}
