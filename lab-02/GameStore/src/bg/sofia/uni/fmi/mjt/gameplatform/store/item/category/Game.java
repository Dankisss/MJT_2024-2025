package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Game extends Item{
    private final String genre;

    public Game() {
        super();
        this.genre = "";
    }

    public Game(String title, BigDecimal price, LocalDateTime releaseDate, String genre){
        super(title, price, releaseDate);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }
}
