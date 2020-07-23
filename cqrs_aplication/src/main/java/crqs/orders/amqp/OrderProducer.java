package cqrs.orders.amqp;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * A bean producing random prices every 5 seconds. The prices are written to an
 * AMQP queue (prices). The AMQP configuration is specified in the application
 * configuration.
 */
@ApplicationScoped
public class OrderProducer {

    @Channel("orders")
    Emitter<OrderData> queryEmitter;


    public void emit (OrderData orderData){
        queryEmitter.send(orderData);
    }

}