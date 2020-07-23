package cqrs.orders.amqp;

import javax.enterprise.context.ApplicationScoped;

import com.mongodb.client.MongoClient;

import org.eclipse.microprofile.reactive.messaging.Incoming;

import javax.inject.Inject;
import cqrs.orders.nosql.OrderNosqlService;

/**
 * A bean consuming data from the "prices" AMQP queue and applying some
 * conversion. The result is pushed to the "my-data-stream" stream which is an
 * in-memory stream.
 */
@ApplicationScoped
public class OrderConsumer {

  @Inject
  OrderNosqlService OrderNosqlService;

  @Inject
  MongoClient mongoClient;


  @Incoming("orders")
  public OrderData process(OrderData orderData) {
    try {
      OrderNosqlService.save(orderData);
    } catch (Exception e) {
      System.out.println(e);
    }
    return orderData;
  }

}