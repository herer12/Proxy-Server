package data.repos;

import model.Database.Product;
import model.Database.Review;

import java.util.LinkedList;

public interface ReviewRepository {
    public void addReview(Review review);
    public void updateReview(Review review);
    public void deleteReview(int id);
    public LinkedList<Review> getAllReviews();
    public LinkedList<Review> getAllReviewsByProduct(Product product);
}
