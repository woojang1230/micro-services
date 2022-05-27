package se.magnus.api.core.recommendation;

public class Recommendation {
    private int productId;
    private int recommendationId;
    private String author;
    private int rate;
    private String content;
    private String serviceAddress;

    public Recommendation() {
    }

    public Recommendation(final int productId, final int recommendationId, final String author, final int rate,
                          final String content, final String serviceAddress) {
        this.productId = productId;
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public static Recommendation createDefault() {
        return new Recommendation(0, 0, null, 0, null, null);
    }

    public int getProductId() {
        return productId;
    }

    public int getRecommendationId() {
        return recommendationId;
    }

    public String getAuthor() {
        return author;
    }

    public int getRate() {
        return rate;
    }

    public String getContent() {
        return content;
    }

    public String getServiceAddress() {
        return serviceAddress;
    }

    public void setProductId(final int productId) {
        this.productId = productId;
    }

    public void setRecommendationId(final int recommendationId) {
        this.recommendationId = recommendationId;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setRate(final int rate) {
        this.rate = rate;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setServiceAddress(final String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
