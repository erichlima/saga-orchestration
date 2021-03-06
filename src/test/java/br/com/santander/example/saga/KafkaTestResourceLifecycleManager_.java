package br.com.santander.example.saga;

import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;
import io.smallrye.reactive.messaging.connectors.InMemoryConnector;

import java.util.HashMap;
import java.util.Map;

public class KafkaTestResourceLifecycleManager_ implements QuarkusTestResourceLifecycleManager {
    @Override
    public Map<String, String> start() {
        Map<String, String> env = new HashMap<>();
        Map<String, String> props1 = InMemoryConnector.switchIncomingChannelsToInMemory("preview-authorization");
        Map<String, String> props2 = InMemoryConnector.switchOutgoingChannelsToInMemory("confirmation-authorization");
        env.putAll(props1);
        env.putAll(props2);
        return env;
    }
    @Override
    public void stop() {
        InMemoryConnector.clear();
    }
}
