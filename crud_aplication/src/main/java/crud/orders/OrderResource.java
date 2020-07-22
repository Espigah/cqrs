package cqrs.orders;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;


import org.eclipse.microprofile.config.inject.ConfigProperty;

import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.MultiConcat;
import cqrs.customer_order.CustomerOrder;
import cqrs.customers.Customer;
import cqrs.items.Item;
import cqrs.order_item.OrderItem;
import io.reactivex.Single;
import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Tuple;


@Path("order")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrderResource {

    @Inject
    PgPool client;

    
    @POST
    public long post() {
        Long t1 = System.nanoTime();
        List<Customer> customerList =
        Customer.findAll(client).collectItems().asList().await().indefinitely();

        List<Item> itemList =
        Item.findAll(client).collectItems().asList().await().indefinitely();

        for (Item item : itemList) {
            for (Customer customer : customerList) {
                save(client, item, customer);
            }
        }
        
        // TimeUnit
        long convert = TimeUnit.SECONDS.convert(System.nanoTime() - t1, TimeUnit.NANOSECONDS);

        System.out.println("write: "+ convert + " seconds");
        return convert; 
    }

    private void save(PgPool client, Item item, Customer customer) {

        Order order = Order.of().save(client).await().indefinitely();

        try {
            OrderItem.of().save(client, order.id, item.id).await().indefinitely();
        } catch (Exception e) {
            System.out.println("OrderItem=" + order.id + " - " + item.id);
        }
        try {
            CustomerOrder.of().save(client, order.id, customer.id).await().indefinitely();
        } catch (Exception e) {
            System.out.println("CustomerOrder=" + order.id + " - " + customer.id);
        }
    }

}