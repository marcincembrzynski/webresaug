/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.baskets;

import app.customers.CustomerBean;
import app.services.Appointment;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author marcin
 */
@Path("/basket")
@Stateless
public class BasketResource {
    
    private final static Logger logger = Logger.getLogger(BasketResource.class.getName());
    
    @Inject
    BasketBoundary basketBoundary;
    
    @Inject 
    CustomerBean customerBean;
    
    
    @GET
    @Produces("application/json")
    public Response get(){
        
        if(null == customerBean.getBasket().getId()){
             //return Response.status(Status.NOT_FOUND).build();
        }
        
        Optional<Basket> basketById = basketBoundary.getBasketById(customerBean.getBasket().getId());
        if(basketById.isPresent()){
            return Response.ok(basketById.get()).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }
   
    
    @POST
    @Produces("application/json")
    @Consumes("application/json")
    public Response add(@NotNull @Valid Appointment appointment){
        logger.log(Level.INFO, "##### appointment: {0}", appointment);
        //ogger.info("bid: " + basketId);
        
        Basket basket = basketBoundary.addAppointment(appointment);
       
        ///securityContext.
        //NewCookie cookie = new NewCookie("bid", basket.getId().toString(), "/", "localhost", "", 99999, true, true);
            
        return Response.ok(basket).build();
           
        
    }
    
    
    @DELETE
    @Path("/{basketAppointmentId}")
    @Produces("application/json")
    public Response delete(@PathParam("basketAppointmentId") Long basketAppointmentId){
        
        
        
        // remove only if appointment belond to the particular basket
        
        logger.info("### basketAppointmentId: " + basketAppointmentId);
        
        Optional<Basket> basket = basketBoundary.removeAppointment(basketAppointmentId);
        
        //Optional<Basket> basketById = basketBoundary.getBasketById(basketId);
        if(basket.isPresent()){
            
            
            return Response.ok(basket.get()).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }
}
