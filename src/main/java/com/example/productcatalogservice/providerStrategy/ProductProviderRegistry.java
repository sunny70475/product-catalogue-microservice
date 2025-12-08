package com.example.productcatalogservice.providerStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ProductProviderRegistry {

    private final Map<String, ProductProvider> providers = new HashMap<>();

    @Autowired
    public ProductProviderRegistry(
            @Qualifier("fakeStoreProvider") ProductProvider fakeStore,
            @Qualifier("localDbProvider") ProductProvider localDb) {

        providers.put("fake", fakeStore);
        providers.put("local", localDb);
    }

    public ProductProvider getProvider(String key) {
        return providers.get(key);
    }
}