package cat.dto;

import java.util.Objects;

public class ProductDTO {

    private Integer product_id;
    private String name;
    private Integer price;
    private Integer stock;
    private String description;

    public ProductDTO() {}

    public ProductDTO(String name, Integer price, Integer stock, String description) {
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer quantity) {
        this.stock = quantity;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "product_id=" + product_id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", stock=" + stock +
                ", description='" + description + '\'' +
                '}';
    }
}
