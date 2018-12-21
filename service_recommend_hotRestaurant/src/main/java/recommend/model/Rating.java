package recommend.model;

import java.io.Serializable;

/**
 * @Auther: wangming
 * @Date: 2018/12/20 18:19
 * @Description:
 */
public class Rating implements Serializable {

    private int userId;
    private int restaurantId;
    private float rating;
    private long timestamp;

    public Rating(Integer integer, Integer integer1, double v) {
    }

    public Rating(int userId, int restaurantId, float rating, long timestamp) {
        this.userId = userId;
        this.restaurantId = restaurantId;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public static Rating parseRating(String str) {
        String[] fields = str.split("\\t");
        if (fields.length != 4) {
            throw new IllegalArgumentException("Each line must contain 4 fields");
        }
        int userId = Integer.parseInt(fields[0]);
        int movieId = Integer.parseInt(fields[1]);
        float rating = Float.parseFloat(fields[2]);
        long timestamp = Long.parseLong(fields[3]);
        return new Rating(userId, movieId, rating, timestamp);
    }


}
