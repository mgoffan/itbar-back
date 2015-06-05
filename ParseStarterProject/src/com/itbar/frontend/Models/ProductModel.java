package com.itbar.frontend.Models;

/**
 * Created by kcortesrodrigue on 04/06/15.
 */
public class ProductModel {
    String prodName;
    String prodPrice;
    String prodCant;
    String prodDesc;

    public ProductModel(String prodName, String prodPrice, String prodCant, String prodDesc){
        this.prodCant=prodCant;
        this.prodPrice=prodPrice;
        this.prodCant=prodCant;
        this.prodDesc=prodDesc;
    }

    public String getProdName() {
        return prodName;
    }

    public void setProdName(String prodName) {
        this.prodName = prodName;
    }

    public String getProdPrice() {
        return prodPrice;
    }

    public void setProdPrice(String prodPrice) {
        this.prodPrice = prodPrice;
    }

    public String getProdCant() {
        return prodCant;
    }

    public void setProdCant(String prodCant) {
        this.prodCant = prodCant;
    }

    public String getProdDesc() {
        return prodDesc;
    }

    public void setProdDesc(String prodDesc) {
        this.prodDesc = prodDesc;
    }
}
