package com.example.productcatalogservice.scheduler;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.providerStrategy.ProductProvider;
import com.example.productcatalogservice.providerStrategy.ProductProviderRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSyncScheduler {

    private final ProductProviderRegistry registry;

    public ProductSyncScheduler(ProductProviderRegistry registry) {
        this.registry = registry;
    }

    // Runs daily at 3 AM
    @Scheduled(cron = "0 0 3 * * *")
    public void syncFakeStoreProducts() {

        ProductProvider fakeProvider = registry.getProvider("fake");
        ProductProvider localProvider = registry.getProvider("local");

        List<Product> externalProducts = fakeProvider.getAllProducts();

        // store or update into local DB
        localProvider.createMultipleProducts(externalProducts);

        System.out.println("Synced " + externalProducts.size() + " products from FakeStore → Local DB.");
    }
}
