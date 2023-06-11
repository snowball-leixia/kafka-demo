package com.leixia.pubsub.message.kafka.interceptor;


import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.RecordInterceptor;

import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.UUID;


public class MmaMovementDocumentKafkaInterceptor implements RecordInterceptor<String, String> {
    private static final String X_CORRELATION_ID = "x-correlation-id";

    @Override
    public ConsumerRecord<String, String> intercept(ConsumerRecord<String, String> consumerRecord) {
        boolean found = Arrays.stream(consumerRecord.headers().toArray()).anyMatch(h -> X_CORRELATION_ID.equalsIgnoreCase(h.key()));
        if (!found) {
            var correlationId = UUID.randomUUID().toString();
            consumerRecord.headers().add(X_CORRELATION_ID, correlationId.getBytes(Charset.defaultCharset()));
        }
        return consumerRecord;
    }

    @Override
    public ConsumerRecord<String, String> intercept(ConsumerRecord<String, String> consumerRecord, Consumer<String, String> consumer) {
        return RecordInterceptor.super.intercept(consumerRecord, consumer);
    }

    @Override
    public void success(ConsumerRecord<String, String> consumerRecord, Consumer<String, String> consumer) {
        RecordInterceptor.super.success(consumerRecord, consumer);
    }

    @Override
    public void failure(ConsumerRecord<String, String> consumerRecord, Exception exception, Consumer<String, String> consumer) {
        RecordInterceptor.super.failure(consumerRecord, exception, consumer);
    }

    @Override
    public void afterRecord(ConsumerRecord<String, String> consumerRecord, Consumer<String, String> consumer) {
        RecordInterceptor.super.afterRecord(consumerRecord, consumer);
    }

    @Override
    public void setupThreadState(Consumer<?, ?> consumer) {
        RecordInterceptor.super.setupThreadState(consumer);
    }

    @Override
    public void clearThreadState(Consumer<?, ?> consumer) {
        RecordInterceptor.super.clearThreadState(consumer);
    }
}
