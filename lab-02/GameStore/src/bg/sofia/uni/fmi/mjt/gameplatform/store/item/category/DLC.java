package bg.sofia.uni.fmi.mjt.gameplatform.store.item.category;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class DLC extends Item{
    private final Game game;

    public DLC() {
        super();
        game = new Game();
    }

    public DLC(String title, BigDecimal price, LocalDateTime releaseDate, Game game) {
        super(title, price, releaseDate);
        this.game = game;
    }

    public Game getGame() {
        return game;
    }

    public static void main(String[] args) {
        DLC game = new DLC();

        game.rate(3);
        game.rate(4);
        game.rate(3);

        System.out.println(game.getRating());
    }
}
