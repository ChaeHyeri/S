package cat.dto;

public class CartDTO {

    private Integer cart_id;
    private String user_id;
    private Integer product_id;
    private Integer quantity;
    private ProductDTO productDTO;

    public CartDTO() {}


    public Integer getCart_id() {
        return cart_id;
    }

    public void setCart_id(Integer cart_id) {
        this.cart_id = cart_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Integer product_id) {
        this.product_id = product_id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public ProductDTO getProductDTO() {
        return productDTO;
    }

    public void setProductDTO(ProductDTO productDTO) {
        this.productDTO = productDTO;
    }

    @Override
    public String toString() {
        return "CartDTO{" +
                "cart_id=" + cart_id +
                ", user_id='" + user_id + '\'' +
                ", product_id=" + product_id +
                ", quantity=" + quantity +
                ", productDTO=" + productDTO +
                '}';
    }
}

