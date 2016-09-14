/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.baskets;

/**
 *
 * @author marcin
 */
public class Message<T> {
    
    private final T body;
    private final String message;

    public Message(T body, String message) {
        this.body = body;
        this.message = message;
    }

    public T getBody() {
        return body;
    }

    public String getMessage() {
        return message;
    }
    
    
    
    
}
