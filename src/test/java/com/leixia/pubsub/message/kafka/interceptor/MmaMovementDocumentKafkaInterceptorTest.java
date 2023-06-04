package com.leixia.pubsub.message.kafka.interceptor;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.header.internals.RecordHeaders;
import org.apache.kafka.common.record.TimestampType;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MmaMovementDocumentKafkaInterceptorTest {


    @Test
    void givenConsumerRecordWithCorrelationHeader_Intercept_shouldInsertRandomUUID_asCorrelationIdHeader() {
        // given
        var consumerRecords = new ConsumerRecord<>
                ("test-topic", 1, 1L,
                        1L,
                        TimestampType.NO_TIMESTAMP_TYPE,
                        1,
                        1,
                        "key",
                        "value",
                        new RecordHeaders(),
                        Optional.empty());
        var cut = new MmaMovementDocumentKafkaInterceptor();

        // act
        cut.intercept(consumerRecords);

        //assert
        assertThat(consumerRecords.headers()).hasSize(1);
        assertThat(consumerRecords.headers().headers("x-correlation-id")).isNotEmpty();
    }

}