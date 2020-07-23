package cqrs.order_item;

import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.mutiny.sqlclient.RowSet;
import io.smallrye.mutiny.Uni;

public class OrderItem {

    public Integer id;

    private OrderItem() {
    }

    private OrderItem(Integer id) {
        this.id = id;
    }

    public static OrderItem of() {
        return new OrderItem();
    }

    private OrderItem from(Row row) {
        return new OrderItem(row.getInteger("id"));
    }

    public Uni<OrderItem> save(PgPool client, Integer idOrder, Integer idItem) {
        return client.preparedQuery("INSERT INTO order_item (id_order, id_item) VALUES ($1, $2)")
                .execute(Tuple.of(idOrder, idItem)).onItem().apply(RowSet::iterator).onItem()
                .apply(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }

}
