package com.leixia.pubsub.message.kpi;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KpiMetricsService {
    private final MeterRegistry meterRegistry;

    public KpiMetricsService(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
        this.meterRegistry.config().commonTags(List.of(Tag.of("service", "kafka-demo")));
    }

    public void recordSuccess() {
        meterRegistry.counter("http-request-status", "success", "request-success").increment();
    }

    public void recordFail() {
        meterRegistry.counter("http-request-status", "fail", "request-fail").increment();
    }
}

