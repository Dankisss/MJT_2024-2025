package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Objects;

abstract class Item implements StoreItem {
    protected String title;
    protected BigDecimal price;
    protected LocalDateTime releaseDate;
    protected double rating;
    private int ratingsCount = 0;
    public Item() {
        title = "";
        price = new BigDecimal(0);
        releaseDate = LocalDateTime.now();
    }

    public Item(String title, BigDecimal price, LocalDateTime releaseDate) {
        this.title = title;
        this.price = price;
        this.releaseDate = releaseDate;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public BigDecimal getPrice() {
        return price;
    }

    @Override
    public LocalDateTime getReleaseDate() {
        return releaseDate;
    }

    @Override
    public double getRating() {
        return rating / ratingsCount;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public void setPrice(BigDecimal price) {
        this.price = price.setScale(2, RoundingMode.HALF_UP);
    }

    @Override
    public void setReleaseDate(LocalDateTime releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public void rate(double rating) {
        if(rating >= 1 && rating <= 5) {

            ratingsCount++;
            this.rating += rating;

        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(title, item.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }

    @Override
    public String toString() {
        return "Item{" +
                "title='" + title + '\'' +
                ", price=" + price +
                ", releaseDate=" + releaseDate +
                ", rating=" + rating +
                ", ratingsCount=" + ratingsCount +
                '}';
    }
}
