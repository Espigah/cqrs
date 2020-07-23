package cqrs.orders;

import java.time.LocalDateTime;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.Document;

import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.RowSet;

public class Order {

    public Integer id;
    public LocalDateTime created;
    public LocalDateTime updated;
    public String label;
    public String tag;
    public String description;
    public Integer magicNumber;

    private Order() {
    }

    public Order(Integer id, LocalDateTime created, LocalDateTime updated, String label, String tag, String description, Integer magicNumber) {
        this.id = id;
        this.created = created;
        this.updated = updated;
        this.label = label;
        this.tag = tag;
        this.description = description;
        this.magicNumber = magicNumber;
    }

    public static Order of() {
        return new Order();
    }

    private static Order from(Row row) {
        return new Order(
                row.getInteger("id")
                , row.getLocalDateTime("created")
                , row.getLocalDateTime("updated")
                , row.getString("label")
                , row.getString("tag")
                , row.getString("description")
                , row.getInteger("magic_number")
        );
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
                .query("INSERT INTO  \"order\" (created, updated) VALUES (NOW(),NOW()) RETURNING id, created, updated, label, tag, description, magic_number")
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

    public static Order of(Document documentOrder) {
        String id = documentOrder.getString("_id");
        if (id == null) {
            id = documentOrder.getString("id");
        }

        LocalDateTime updated = LocalDateTime.parse(documentOrder.getString("updated"));
        LocalDateTime created = LocalDateTime.parse(documentOrder.getString("created"));

        return new Order(Integer.parseInt(id), created, updated,
                documentOrder.getString("label"),
                documentOrder.getString("tag"),
                documentOrder.getString("description"),
                Integer.parseInt(documentOrder.getString("magicNumber"))
                );
    }
}
