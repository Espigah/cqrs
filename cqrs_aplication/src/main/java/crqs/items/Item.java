package cqrs.items;

import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;

public class Item {

    public Integer id;
    public Integer price;

    public Item() {
    }

    public Item(Integer id, Integer price) {
        this.id = id;
        this.price = price;
    }

    private static Item from(Row row) {
        return new Item(row.getInteger("id"), row.getInteger("price"));
    }

    public static Multi<Item> findAll(PgPool client) {
        return client.query("SELECT id, price FROM item").execute()
                // Create a Multi from the set of rows:
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                // For each row create a fruit instance
                .onItem().apply(Item::from);
    }

}
