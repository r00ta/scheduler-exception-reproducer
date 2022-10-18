package org.acme;

import java.util.Date;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Path("/date")
public class GreetingResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreetingResource.class);

    @Inject
    ScheduledExecutorService scheduledExecutorService;

    @Inject
    ShouldFailOrNot shouldFailOrNot;

    Date date;

    @PostConstruct
    void init(){
        this.scheduledExecutorService.scheduleWithFixedDelay(this::updateDate, 0, 5, TimeUnit.SECONDS);
    }

    private void updateDate(){
        if (shouldFailOrNot.shouldFail()){
            LOGGER.info("Raising exception!");
            throw new RuntimeException("HAHAHAHAHA");
        }
        LOGGER.info("updating new date");
        this.date = new Date();
    }

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response getLastUpdate() {
        return Response.ok(date).build();
    }


    @GET
    @Path("/swapShouldFail")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.TEXT_PLAIN)
    public Response hello() {
        shouldFailOrNot.swapShouldFail();
        return Response.ok().build();
    }
}