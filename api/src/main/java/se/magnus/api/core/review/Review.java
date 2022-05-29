package se.magnus.api.core.review;

public class Review {
    private int productId;
    private int reviewId;
    private String author;
    private String subject;
    private String content;
    private String serviceAddress;

    public Review() {
    }

    public Review(final int productId, final int reviewId, final String author, final String subject,
                  final String content, final String serviceAddress) {
        this.productId = productId;
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
        this.content = content;
        this.serviceAddress = serviceAddress;
    }

    public static Review createDefault() {
        return new Review(0, 0, null, null, null, null);
    }

    public int getProductId() {
        return productId;
    }

    public int getReviewId() {
        return reviewId;
    }

    public String getAuthor() {
        return author;
    }

    public String getSubject() {
        return subject;
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

    public void setReviewId(final int reviewId) {
        this.reviewId = reviewId;
    }

    public void setAuthor(final String author) {
        this.author = author;
    }

    public void setSubject(final String subject) {
        this.subject = subject;
    }

    public void setContent(final String content) {
        this.content = content;
    }

    public void setServiceAddress(final String serviceAddress) {
        this.serviceAddress = serviceAddress;
    }
}
