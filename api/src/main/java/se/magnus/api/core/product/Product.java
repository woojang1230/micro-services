package se.magnus.api.core.product;

public class Product {
    private int productId;
    private String name;
    private int weight;
    private String serviceAddress;

    protected Product() {
    }

    public Product(final int productId, final String name, final int weight, final String serviceAddress) {
        this.productId = productId;
        this.name = name;
        this.weight = weight;
        this.serviceAddress = serviceAddress;
    }

    public static Product createDefault() {
        return new Product(0, null, 0, null);
    }

    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public int getWeight() {
        return weight;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }
}
