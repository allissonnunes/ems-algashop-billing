package br.dev.allissonnunes.algashop.billing.infrastructure.resilience;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.boot.health.contributor.Health;
import org.springframework.boot.health.contributor.HealthIndicator;
import org.springframework.boot.health.contributor.Status;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component("circuitBreakers")
@RequiredArgsConstructor
class CircuitBreakerHealthIndicator implements HealthIndicator {

    private final CircuitBreakerRegistry circuitBreakerRegistry;

    @Override
    public @Nullable Health health() {
        final Map<String, Object> indicatorDetails = new HashMap<>();
        String indicatorStatus = Status.UP.getCode();
        Throwable lastException = null;

        for (final CircuitBreaker circuitBreaker : this.circuitBreakerRegistry.getAllCircuitBreakers()) {
            final CircuitBreaker.State state = circuitBreaker.getState();

            final Map<String, Object> detailsBuilder = new HashMap<>();
            detailsBuilder.put("state", state.name());

            if (state == CircuitBreaker.State.OPEN) {
                indicatorStatus = "DEGRADED";
//                final Throwable cause = NestedExceptionUtils.getRootCause(policy.getLastException());
//                if (cause != null) {
//                    lastException = cause;
//                    detailsBuilder.put("lastException", lastException.getMessage());
//                } else {
                detailsBuilder.put("lastException", null);
//                }
            }

            indicatorDetails.put(circuitBreaker.getName(), detailsBuilder);
        }

        final Health.Builder healthBuilder = Health
                .status(indicatorStatus)
                .withDetails(indicatorDetails);

//        if ("DEGRADED".equals(indicatorStatus) && lastException != null) {
//            healthBuilder.withException(lastException);
//        }

        return healthBuilder.build();
    }

}
