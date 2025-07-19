package com.example.productcatalogservice.clients;

import com.example.productcatalogservice.dtos.FakeStoreProductDto;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

//making call to FakeStore Endpoint
@Component
public class FakeStoreApiClient {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public FakeStoreProductDto getFakeStoreProductById(Long id) {
        // Create a RestTemplate instance using the builder
        RestTemplate restTemplate = restTemplateBuilder.build();

        // Make a GET request to the FakeStore API to retrieve a product by ID
        // The response is expected to be of type FakeStoreProductDto
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity(HttpMethod.GET,"https://fakestoreapi.com/products/{id}",null,
                        FakeStoreProductDto.class,id);

        FakeStoreProductDto fakeStoreProductDtoOutput = fakeStoreProductDtoResponseEntity.getBody();

        if(validateResponse(fakeStoreProductDtoResponseEntity)) {
            return fakeStoreProductDtoOutput;
        }

        return null;
    }

    /**
     * This method retrieves a product by its ID from the FakeStore API.
     *
     * @param id the ID of the product to retrieve
     * @return a FakeStoreProductDto object representing the product, or null if not found
     */
    public FakeStoreProductDto createFakeStoreProduct(FakeStoreProductDto fakeStoreProductDtoInput) {

        RestTemplate restTemplate = restTemplateBuilder.build();

        // Make a POST request to the FakeStore API to create a new product
        // The response is expected to be of type FakeStoreProductDto
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity(HttpMethod.POST,"https://fakestoreapi.com/products",fakeStoreProductDtoInput,
                        FakeStoreProductDto.class);

        FakeStoreProductDto fakeStoreProductDtoOutput = fakeStoreProductDtoResponseEntity.getBody();

        if(validateResponse(fakeStoreProductDtoResponseEntity)) {
            return fakeStoreProductDtoOutput;
        }

        return null;
    }

    public FakeStoreProductDto replaceFakeStoreProduct(FakeStoreProductDto fakeStoreProductDtoInput, Long id) {
        ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity =
                requestForEntity(HttpMethod.PUT,"https://fakestoreapi.com/products/{id}",fakeStoreProductDtoInput,
                        FakeStoreProductDto.class,id);

        FakeStoreProductDto fakeStoreProductDtoOutput = fakeStoreProductDtoResponseEntity.getBody();

        if(validateResponse(fakeStoreProductDtoResponseEntity)) {
            return fakeStoreProductDtoOutput;
        }

        return null;
    }

    private Boolean validateResponse(ResponseEntity<FakeStoreProductDto> fakeStoreProductDtoResponseEntity) {
        if(fakeStoreProductDtoResponseEntity.getBody() != null &&
                fakeStoreProductDtoResponseEntity.getStatusCode() ==
                        HttpStatus.valueOf(200)) {

            return true;
        }

        return false;
    }

    private <T> ResponseEntity<T> requestForEntity(HttpMethod httpMethod, String url, @Nullable Object request, Class<T> responseType, Object... uriVariables) throws RestClientException {
        RestTemplate restTemplate = restTemplateBuilder.build();
        RequestCallback requestCallback = restTemplate.httpEntityCallback(request, responseType);
        ResponseExtractor<ResponseEntity<T>> responseExtractor = restTemplate.responseEntityExtractor(responseType);
        return restTemplate.execute(url, httpMethod, requestCallback, responseExtractor, uriVariables);
    }
}

