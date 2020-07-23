package cqrs.customer_order;

import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;
import io.vertx.mutiny.sqlclient.RowSet;
import io.smallrye.mutiny.Uni;

public class CustomerOrder {

    public Integer idCustomer;
    public Integer idOrder;

    private CustomerOrder() {
    }

    private CustomerOrder(Integer idCustomer, Integer idOrder) {
        this.idCustomer = idCustomer;
        this.idOrder = idOrder;
    }

    public static CustomerOrder of() {
        return new CustomerOrder();
    }

    private  CustomerOrder from(Row row) {
        return new CustomerOrder(row.getInteger("id_customer"), row.getInteger("id_order"));
    }

    public  Uni<CustomerOrder> save(PgPool client, Integer idOrder, Integer idCustomer) {
        return client.preparedQuery("INSERT INTO customer_order (id_order, id_customer) VALUES ($1, $2)")
                .execute(Tuple.of(idOrder, idCustomer)).onItem().apply(RowSet::iterator).onItem()
                .apply(iterator -> iterator.hasNext() ? from(iterator.next()) : null);
    }
}
