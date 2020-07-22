package cqrs.orders;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

public class Order {

    public Integer id;
    public LocalDateTime created;
    public LocalDateTime updated;

    private Order() {
    }

    public Order(Integer id, LocalDateTime created, LocalDateTime updated) {
        this.id = id;
        this.created = created;
        this.updated = updated;
    }

    public static Order of() {
        return new Order();
    }

    private static Order from(Row row) {
        return new Order(row.getInteger("id"), row.getLocalDateTime("created"), row.getLocalDateTime("updated"));
    }

    public static Multi<Order> findAll(PgPool client) {
        return client.query("SELECT * FROM \"order\"").execute()
                // Create a Multi from the set of rows:
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                // For each row create a fruit instance
                .onItem().apply(Order::from);
    }

    public Uni<Order> save(PgPool client) {
        return client
                .query("INSERT INTO  \"order\" (created, updated) VALUES (NOW(),NOW()) RETURNING id, created, updated")
                .execute().onItem().apply(RowSet::iterator).onItem()
                .apply(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

    public Integer getId() {
        return id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }
}
