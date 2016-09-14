/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.customers;

import app.baskets.Basket;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author marcin
 */
@Named
@SessionScoped
public class CustomerBean implements Serializable{
    
    private Basket basket = new Basket();

    public Basket getBasket() {
        return basket;
    }

    public void setBasket(Basket basket) {
        this.basket = basket;
    }
    
    
    
}
