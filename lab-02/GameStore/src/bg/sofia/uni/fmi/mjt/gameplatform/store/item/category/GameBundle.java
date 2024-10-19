package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class GameBundle extends Item{
    private final Game[] games;

    public GameBundle(String title, BigDecimal price, LocalDateTime releaseDate, Game[] games) {
        super(title, price, releaseDate);
        this.games = games;
    }

    public Game[] getGames() {
        return games;
    }

    public static void main(String[] args) {
        GameBundle gameBundle = new GameBundle("asdasd", new BigDecimal("10"), LocalDateTime.now(), new Game[]{new Game(), new Game()});
        System.out.println(gameBundle.getPrice());
    }
}
