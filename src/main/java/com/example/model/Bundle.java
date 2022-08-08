package com.example.model;

/**
 * упаковка из нескольких штук одного продукта
 */
public class Bundle implements Packable {
    private Product product;
    private int amount;

    public Bundle(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return """
               Bundle {
               product = %s
               amount = %d
               }
               """
                .formatted(product, amount);
    }
}
