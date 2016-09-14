/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author marcin
 */
@Path("/service")
@Stateless
public class ServiceResource {
    
    
    private final static Logger logger = Logger.getLogger(ServiceResource.class.getName());
    
    @Inject
    ServiceBoundary serviceBoundary;
    
   
    
    @GET
    @Produces("application/json")
    public Response findAll(){
        List<Service> allServices = serviceBoundary.findAll();
        return Response.ok(allServices).build();
    }
    
    @GET
    @Path("/{id}")
    @Produces("application/json")
    public Response getSingle(@PathParam("id") Long id){
        
        Optional<Service> service = serviceBoundary.findById(id);
        
        if(service.isPresent()){
            return Response.ok(service.get()).build();
        }else{
            return Response.status(Status.NOT_FOUND).build();
        }
    }
    
    
    @GET
    @Path("/{id}/dates")
    @Produces("application/json")
    public Response getServiceDaysInTheFuture(@PathParam("id") Long id){
        
        Set<ServiceDate> serviceDates = serviceBoundary.getServiceDates(id);
        logger.log(Level.INFO, "#### serviceDates: {0}", serviceDates);
        
        return Response.ok(serviceDates).build();
    }
    
    
    @GET
    @Path("/{id}/{date}")
    @Produces("application/json")
    public Response getPossibleAppointmentsForService(@PathParam("id") Long id, @PathParam("date") LocalDate date){
        
        logger.info("date: " + date);
        List<Appointment> appointmentsForDate = serviceBoundary.getAppointmentsForDate(id, date);
        //List<Appointment> collect = appointmentsForDate.stream().sorted().collect(Collectors.toList());
        return Response.ok(appointmentsForDate).build();
    }
    
    
    
   
}
