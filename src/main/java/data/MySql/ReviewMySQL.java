package data.MySql;

import data.repos.ReviewRepository;
import model.Database.Product;
import model.Database.Review;

import java.util.LinkedList;

public class ReviewMySQL implements ReviewRepository {
    @Override
    public void addReview(Review review) {

    }

    @Override
    public void updateReview(Review review) {

    }

    @Override
    public void deleteReview(int id) {

    }

    @Override
    public LinkedList<Review> getAllReviews() {
        return null;
    }

    @Override
    public LinkedList<Review> getAllReviewsByProduct(Product product) {
        return null;
    }
}
