package cqrs.customers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.StreamSupport;

import io.smallrye.mutiny.Multi;
import io.vertx.mutiny.pgclient.PgPool;
import io.vertx.mutiny.sqlclient.Row;
import io.vertx.mutiny.sqlclient.Tuple;

public class CustomerExpenditure {

    public Integer id;
    public String name;
    public Integer amount;

    private CustomerExpenditure() {
    }

    private CustomerExpenditure(Integer id, String name, Integer amount) {
        this.id = id;
        this.name = name;
        this.amount = amount;
    }

    public static CustomerExpenditure of() {
        return new CustomerExpenditure();
    }

    private static CustomerExpenditure from(Row row) {
        return new CustomerExpenditure(row.getInteger("id"), row.getString("name"), row.getInteger("amount"));
    }
    
    //LocalDateTime.of(2020,06,14,0,0,0), LocalDateTime.of(2020,06,14,0,0,0)
    public  Multi<CustomerExpenditure> findByPeriod(PgPool client, LocalDateTime begin, LocalDateTime end) {
        return client
                .preparedQuery(
                "select * from custome_expenditure" 
                + " where"
                + " updated >= $1::timestamp "
                + " AND updated <  ($2::timestamp + INTERVAL '1 DAY')"                
                )
                .execute(Tuple.of(begin, end))
                // Create a Multi from the set of rows:
                .onItem()
                .produceMulti(set -> Multi.createFrom().items(() -> StreamSupport.stream(set.spliterator(), false)))
                // For each row create a fruit instance
                .onItem().apply(CustomerExpenditure::from);
    }

}
