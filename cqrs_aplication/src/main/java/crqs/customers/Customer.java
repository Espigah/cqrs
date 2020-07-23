package cqrs.customers;

import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;

public class Customer {

    public Integer id;
    public String name;

    public Customer() {
    }

    public Customer(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    private static Customer from(Row row) {
        return new Customer(row.getInteger("id"), row.getString("name"));
    }
    
    public static Multi<Customer> findAll(PgPool client) {
        return client.query("SELECT * FROM \"customer\"").execute()
                // Create a Multi from the set of rows:
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                // For each row create a fruit instance
                .onItem().apply(Customer::from);
    }
}
