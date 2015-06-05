package com.itbar.backend.services.views;

import com.itbar.backend.util.Form;

/**
 * Created by ioninielavitzky on 5/28/15.
 */
public class OrderProduct {

    private Order order;
    private MenuItem menuItem;
    private String comment = "";
    private int quantity = 0;

    public OrderProduct(){

    }

    public OrderProduct(MenuItem mI, String c, int q){
        menuItem = mI;
        comment = c;
        quantity = q;

    }

    public OrderProduct(MenuItem mI){
        menuItem = mI;

    }

    public Order getOrder() {
        return order;
    }

    public MenuItem getMenuItem() {
        return menuItem;
    }

    public void setMenuItem(MenuItem menuItem) {
        this.menuItem = menuItem;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment() {
        return comment;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;

        if (o.getClass() != this.getClass())
            return false;
        if (((OrderProduct)o).getMenuItem().equals(this.getMenuItem()))
            return true;
        else
            return false;
    }
}
