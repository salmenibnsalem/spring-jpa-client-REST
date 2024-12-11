package org.soa.springjpaclientrest.controllers;
import org.soa.springjpaclientrest.services.ProduitClientService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import soa.entities.Produit;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import java.text.SimpleDateFormat;
import java.util.Date;
@Controller
@RequestMapping("/produits")
public class ProduitWebController {
    private final ProduitClientService produitClientService;
    public ProduitWebController(ProduitClientService produitClientService)
    {
        this.produitClientService = produitClientService;
    }
//conversion date
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new
                org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat,
                true));
    }
    // Afficher la liste des produits
    @GetMapping
    public String listArticles(Model model) {
        model.addAttribute("produits",
                produitClientService.getAllProduits());
        return "produits/list"; // La vue list.html
    }
    // Afficher le formulaire pour créer un nouveau produit
    @GetMapping("/add")
    public String createArticleForm(Model model) {
        model.addAttribute("produit", new Produit());
        return "produits/form"; // La vue form.html pour la création
    }
    // Enregistrer un nouveau produit
    @PostMapping("/add")
    public String saveArticle(@ModelAttribute Produit produit) {
        produitClientService.createProduit(produit);
        return "redirect:/produits"; // Rediriger vers la liste desproduits après création
    }
    // Afficher le formulaire pour modifier un produit existant
    @GetMapping("/{id}")
    public String editArticleForm(@PathVariable Long id, Model model) {
        model.addAttribute("produit",
                produitClientService.getProduitById(id));
        return "produits/form"; // La vue form.html pour l'édition
    }
    // Mettre à jour un produit existant
    @PostMapping("/{id}")
    public String updateArticle(@ModelAttribute Produit produit) {
        produitClientService.updateProduit(produit);
        return "redirect:/produits"; // Rediriger vers la liste desproduits après mise à jour
    }
    // Supprimer un produit
    @GetMapping("/delete/{id}")
    public String deleteArticle(@PathVariable Long id) {
        produitClientService.deleteProduit(id);
        return "redirect:/produits"; // Rediriger vers la liste desproduits après suppression
    }
}