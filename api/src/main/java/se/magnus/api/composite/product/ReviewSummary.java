package se.magnus.api.composite.product;

public class ReviewSummary {

    private final int reviewId;
    private final String author;
    private final String subject;

    public ReviewSummary(final int reviewId, final String author, final String subject) {
        this.reviewId = reviewId;
        this.author = author;
        this.subject = subject;
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
}
