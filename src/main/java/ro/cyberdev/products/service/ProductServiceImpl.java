package ro.cyberdev.products.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import ro.cyberdev.products.rest.CreateProductRestModel;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class ProductServiceImpl implements ProductService {
    private final Logger log = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate;

    public ProductServiceImpl(KafkaTemplate<String, ProductCreatedEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public String createProduct(CreateProductRestModel productRestModel) throws Exception {
        String productId = UUID.randomUUID().toString();

        //TODO: Persist ProductDetails into a database table before publishing an Event

        ProductCreatedEvent productCreatedEvent =
                new ProductCreatedEvent(
                        productId,
                        productRestModel.getTitle(),
                        productRestModel.getPrice(),
                        productRestModel.getQuantity());

        // send message async and not wait for a result
//        CompletableFuture<SendResult<String, ProductCreatedEvent>> future =
//                kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent);
//        future.whenComplete((result, ex) -> {
//            if (ex != null) {
//                log.error(" ***** Error sending message: " + ex.getMessage());
//            } else {
//                log.info(" ***** Message sent successfully" + result.getRecordMetadata());
//            }
//        });

        // block the execution (current thread) until the message is sent to Kafka (execution is completed)
        // synchronous execution
//        future.join();

        // Synchronous execution with try-catch
        SendResult<String, ProductCreatedEvent> result =
                kafkaTemplate.send("product-created-events-topic", productId, productCreatedEvent).get();

        log.info(" ***** Returning product id: " + productId);

        return productId;
    }
}
