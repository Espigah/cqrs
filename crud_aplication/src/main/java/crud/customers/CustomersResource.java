package cqrs.customers;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.vertx.mutiny.pgclient.PgPool;

@Path("customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomersResource {

    @Inject
    PgPool client;

    @GET
    @Path("expenditureByPeriod")
    public long get() {
        Long t1 = System.nanoTime();

        for (int i = 0; i < 500; i++) {
            CustomerExpenditure.of().findByPeriod(client, LocalDateTime.of(2020, 06, 14, 0, 0, 0), LocalDateTime.now()).collectItems().asList().await().indefinitely();;
        }

        long convert = TimeUnit.SECONDS.convert(System.nanoTime() - t1, TimeUnit.NANOSECONDS);
        System.out.println("read: "+ convert + " seconds");
        return convert;
    }

}