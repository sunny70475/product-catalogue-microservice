package com.example.productcatalogservice.clients;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * Client class responsible for calling the FakeStore external API.
 * All network communication & HTTP handling is isolated here.
 */
@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    private final String BASE_URL = "https://fakestoreapi.com/products";

    /**
     * Fetch a product by ID from FakeStore.
     */
    public FakeStoreProductDto getFakeStoreProductById(Long id) {

        // Make GET request → FakeStore returns FakeStoreProductDto JSON
        ResponseEntity<FakeStoreProductDto> response =
                requestForEntity(
                        HttpMethod.GET,
                        BASE_URL + "/{id}",
                        null,                             // GET → no request body
                        FakeStoreProductDto.class,
                        id                               // path variable {id}
                );

        if (validateResponse(response)) {
            return response.getBody();
        }

        return null;
    }

    /**
     * Create a product in FakeStore using POST.
     */
    public FakeStoreProductDto createFakeStoreProduct(FakeStoreProductDto input) {

        // POST request → send product payload
        ResponseEntity<FakeStoreProductDto> response =
                requestForEntity(
                        HttpMethod.POST,
                        BASE_URL,
                        input,
                        FakeStoreProductDto.class
                );

        if (validateResponse(response)) {
            return response.getBody();
        }

        return null;
    }

    /**
     * Replace an existing product using PUT.
     */
    public FakeStoreProductDto replaceFakeStoreProduct(FakeStoreProductDto input, Long id) {

        ResponseEntity<FakeStoreProductDto> response =
                requestForEntity(
                        HttpMethod.PUT,
                        BASE_URL + "/{id}",
                        input,
                        FakeStoreProductDto.class,
                        id
                );

        if (validateResponse(response)) {
            return response.getBody();
        }

        return null;
    }

    /**
     * Fetch all products from FakeStore.
     */
    public List<FakeStoreProductDto> getAllProducts() {

        RestTemplate restTemplate = restTemplateBuilder.build();

        // GET returns array → convert to list for easier handling
        FakeStoreProductDto[] products =
                restTemplate.getForObject(BASE_URL, FakeStoreProductDto[].class);

        return Arrays.asList(products);
    }

    /**
     * Checks:
     * 1. Response body must not be null
     * 2. HTTP status should be 2xx (success range)
     */
    private boolean validateResponse(ResponseEntity<?> response) {
        return response != null && response.getStatusCode().is2xxSuccessful();
    }

    /**
     * Core reusable method for performing ANY HTTP call.
     * Replaces deprecated `execute()` + callbacks with `exchange()`.
     */
    private <T> ResponseEntity<T> requestForEntity(
            HttpMethod method,
            String url,
            @Nullable Object requestBody,  // optional (can be null for GET)
            Class<T> responseType,
            Object... uriVariables          // path variables
    ) {
        RestTemplate restTemplate = restTemplateBuilder.build();

        // Wrap request body (if any) in an HttpEntity
        HttpEntity<?> entity = new HttpEntity<>(requestBody);

        // exchange() = standard way to call REST endpoints (GET/POST/PUT/DELETE)
        return restTemplate.exchange(
                url,
                method,
                entity,
                responseType,
                uriVariables
        );
    }
}
