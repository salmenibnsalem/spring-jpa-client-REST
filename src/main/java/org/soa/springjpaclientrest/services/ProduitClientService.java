package org.soa.springjpaclientrest.services;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import soa.entities.Produit;
import java.util.Arrays;
import java.util.List;
@Service
public class ProduitClientService {
    private final RestTemplate restTemplate;
    private static final String BASE_URL =
            "http://localhost:8080/produits/";
    public ProduitClientService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }
    // Méthode pour ajouter l'en-tête Accept: application/json
    private HttpEntity<Void> createHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json"); // Ajout de l'en-têteAccept
        return new HttpEntity<>(headers);
    }
    public List<Produit> getAllProduits() {
// Ajout de l'entête Accept: application/json dans la requête
        HttpEntity<Void> entity = createHeaders();
        ResponseEntity<Produit[]> response =
                restTemplate.exchange(BASE_URL,
                        org.springframework.http.HttpMethod.GET, entity,
                        Produit[].class);
        return Arrays.asList(response.getBody());
    }
    public Produit getProduitById(Long id) {
// Ajout de l'entête Accept: application/json dans la requête
        HttpEntity<Void> entity = createHeaders();
        return restTemplate.exchange(BASE_URL + id,
                org.springframework.http.HttpMethod.GET, entity,
                Produit.class).getBody();
    }
    public Produit createProduit(Produit produit) {
// Ajout de l'entête Accept: application/json dans la requête
        HttpEntity<Produit> entity = new HttpEntity<>(produit,
                createHeaders().getHeaders());
// return restTemplate.postForObject(BASE_URL, entity,Produit.class);
        try {
            return restTemplate.postForObject(BASE_URL, entity,
                    Produit.class);
        } catch (HttpClientErrorException e) {
            System.out.println(e);
            throw e;
        }
    }
    public void updateProduit(Produit produit) {
// Ajout de l'entête Accept: application/json dans la requête
        HttpEntity<Produit> entity = new HttpEntity<>(produit,
                createHeaders().getHeaders());
        restTemplate.exchange(BASE_URL ,
                org.springframework.http.HttpMethod.PUT, entity,
                Void.class);
    }
    public void deleteProduit(Long id) {
// Ajout de l'entête Accept: application/json dans la requête
        Produit entityp = getProduitById(id);
        HttpEntity<Produit> entity = new HttpEntity<>(entityp,
                createHeaders().getHeaders());
        restTemplate.exchange(BASE_URL ,
                org.springframework.http.HttpMethod.DELETE, entity,
                Void.class);
    }
}