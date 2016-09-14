/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.payments;

import app.baskets.Basket;
import javax.ejb.Stateless;

/**
 *
 * @author marcin
 */
@Stateless
public class PaymentsBoundary {
    
    
    public boolean pay(Basket basket){
        return true;
    }
}
