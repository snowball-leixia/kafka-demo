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
        this.meterRegistry.config()
                .commonTags(List.of(
                        Tag.of("service", "demo-service"),
                        Tag.of("region", "eu-west-2")));
    }

    public void recordSuccess() {
        meterRegistry.counter("customer.request.status", "status", "request-success").increment();
    }

    public void recordFail() {
        meterRegistry.counter("customer.request.status", "status", "request-fail").increment();
    }
}

