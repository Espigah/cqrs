package cqrs.orders.amqp;

import java.time.LocalDateTime;

import cqrs.customers.Customer;
import cqrs.items.Item;
import cqrs.orders.Order;

public class OrderData {

    Customer customer;
    Order order;
    Item item;

    private OrderData(final Customer customer, final Order order, final Item item) {
        this.customer = customer;
        this.order = order;
        this.item = item;
    }

//    public static OrderData of() {
//        return new OrderData(new Customer(4, "sajdhasjdhk"), new Order(1,LocalDateTime.now(),LocalDateTime.now()), new Item(1, 1));
//    }

    public static OrderData of(final Customer customer, final Order order, final Item item) {
        return new OrderData(customer, order, item);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Order getOrder() {
        return order;
    }

    public Item getItem() {
        return item;
    }
}