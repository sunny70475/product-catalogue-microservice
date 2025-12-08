package com.example.productcatalogservice.services;

import com.example.productcatalogservice.models.Product;
import com.example.productcatalogservice.providerStrategy.ProductProvider;
import com.example.productcatalogservice.providerStrategy.ProductProviderRegistry;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import java.util.ArrayList;
import java.util.List;

//orchestrator service
@Service
public class ProductService implements IProductService {

    private final ProductProviderRegistry providerRegistry;

    // Default provider from config
    // Used when no source specified
    @Value("${product.provider.default:local}")
    private String defaultProvider;

    // Merge providers if needed
    // load the list of provider to call when merging multiple sources
    @Value("${product.provider.merge:local}")
    private List<String> mergeProviders;

    public ProductService(ProductProviderRegistry providerRegistry) {
        this.providerRegistry = providerRegistry;
    }

    private ProductProvider getProvider(String source) {
        if (source == null || source.isEmpty()) {
            return providerRegistry.getProvider(defaultProvider);
        }
        return providerRegistry.getProvider(source);
    }

    // ---------------------------------------------------------
    // GET ALL PRODUCTS
    // ---------------------------------------------------------
    @Override
    public List<Product> getAllProducts() {

        // Single source of truth = Local DB
        // Merge logic only if configured

        List<Product> merged = new ArrayList<>();

        for (String providerKey : mergeProviders) {
            ProductProvider provider = providerRegistry.getProvider(providerKey);
            if (provider != null) {
                merged.addAll(provider.getAllProducts());
            }
        }

        return merged;
    }

    // ---------------------------------------------------------
    // GET BY ID
    // ---------------------------------------------------------
    @Override
    public Product getProductById(Long id) {

        // Local DB always first
        Product local = getProvider("local").getProductById(id);
        if (local != null) return local;

        // Optional fallback to other providers
        for (String providerKey : mergeProviders) {
            if (!providerKey.equals("local")) {
                Product p = providerRegistry.getProvider(providerKey).getProductById(id);
                if (p != null) return p;
            }
        }

        return null;
    }

    // ---------------------------------------------------------
    // CREATE PRODUCT (Always to local DB)
    // ---------------------------------------------------------
    @Override
    public Product createProduct(Product product) {
        return getProvider("local").createProduct(product);
    }

    // ---------------------------------------------------------
    // UPDATE PRODUCT (Always to local DB)
    // ---------------------------------------------------------
    @Override
    public Product replaceProduct(Product input, Long id) {
        return getProvider("local").replaceProduct(input, id);
    }

    // ---------------------------------------------------------
    // BULK CREATE PRODUCT (Always to local DB)
    // ---------------------------------------------------------
    @Override
    public List<Product> createMultipleProducts(List<Product> products) {
        return getProvider("local").createMultipleProducts(products);
    }
}







//    @Autowired
//    private FakeStoreProductService fakeStoreProductService;
//
//    @Autowired
//    private LocalProductService localProductService;
//
//    @Override
//    public List<Product> getAllProducts() {
//        List<Product> output = new ArrayList<>();
//
//        output.addAll(fakeStoreProductService.getAllFakeProducts());
//        output.addAll(localProductService.getAllLocalProducts());
//
//        return output;
//    }
//
//    @Override
//    public Product getProductById(Long id) {
//        // Here you can choose priority (FakeStore or DB)
//        if(id < 20) return fakeStoreProductService.getProductById(id);
//        return localProductService.getProductById(id);
//    }
//
//    @Override
//    public Product createProduct(Product product) {
//        return fakeStoreProductService.createProduct(product);
//    }
//
//    @Override
//    public Product replaceProduct(Product input, Long id) {
//        return fakeStoreProductService.replaceProduct(input, id);
//    }
//
//    @Override
//    public List<Product> createMultipleProducts(List<Product> products) {
//        return localProductService.saveMultipleProducts(products);
//    }


