package se.magnus.api.composite.product;

public class RecommendationSummary {
    private final int recommendationId;
    private final String author;
    private final int rate;
    private final String content;

    public RecommendationSummary(final int recommendationId, final String author, final int rate, final String content) {
        this.recommendationId = recommendationId;
        this.author = author;
        this.rate = rate;
        this.content = content;
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
}
