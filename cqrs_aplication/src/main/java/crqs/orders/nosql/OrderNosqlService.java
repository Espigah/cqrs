package cqrs.orders.nosql;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import cqrs.orders.Order;
import cqrs.orders.amqp.OrderData;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import org.bson.Document;

@ApplicationScoped
public class OrderNosqlService {
  @Inject
  MongoClient mongoClient;

  public List<OrderData> list() {
    final List<OrderData> list = new ArrayList<>();
    final MongoCursor<Document> cursor = getCollection().find().iterator();

    try {
      while (cursor.hasNext()) {
        final Document document = cursor.next();
        Document documentOrder = document.get("order", Document.class);
      }
    } finally {
      cursor.close();
    }
    return list;
  }

  public void save(final OrderData orderData) {
    final Document document = new Document()
      .append("order", orderData.getOrder())
      .append("customer", orderData.getCustomer())
      .append("item", orderData.getItem());
    getCollection().insertOne(document);
  }

  public void add(final Integer value) {
    System.out.println("add");

    final Document document = new Document().append("value", value);
    // .append("description", orderData.getDescription());
    MongoCollection mongoCollection = getCollection();
    System.out.println("insertOne");
    mongoCollection.insertOne(document);
  }

  public List<OrderData> expenditureByPeriod(
    LocalDateTime begin,
    LocalDateTime end
  ) {
    return list();
  }

  private MongoCollection getCollection() {
    return mongoClient.getDatabase("orderData").getCollection("orderData");
  }
}
