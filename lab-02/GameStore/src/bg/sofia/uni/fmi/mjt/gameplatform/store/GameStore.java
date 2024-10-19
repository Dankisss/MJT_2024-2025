package bg.sofia.uni.fmi.mjt.gameplatform.store;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.DLC;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.Game;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.category.GameBundle;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.ItemFilter;
import bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter.PriceItemFilter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

public class GameStore implements StoreAPI {
    private static final String DISCOUNT_ONE = "VAN40";
    private static final String DISCOUNT_TWO = "100YO";

    private final StoreItem[] availableItems;
    private boolean isDiscounted = false;
    public GameStore(StoreItem[] availableItems) {
        this.availableItems = Arrays.copyOf(availableItems, availableItems.length);
    }

    @Override
    public StoreItem[] findItemByFilters(ItemFilter[] itemFilters) {
        StoreItem[] res = new StoreItem[availableItems.length];

        int curSize = 0;
        for (StoreItem a : availableItems) {
            boolean isMatching = true;

            for (ItemFilter f : itemFilters) {

                if (!f.matches(a)) {
                    isMatching = false;
                    break;
                }
            }

            if (isMatching) {
                res[curSize++] = a;
            }
        }

        return Arrays.copyOfRange(res, 0, curSize);
    }

    @Override
    public void applyDiscount(String promoCode) {
        boolean isDiscountOne = promoCode.equals(DISCOUNT_ONE);
        boolean isDiscountTwo = promoCode.equals(DISCOUNT_TWO);

        if (!isDiscounted && (isDiscountOne || isDiscountTwo)) {

            BigDecimal discount = new BigDecimal(isDiscountOne ? 0.6 : 0);

            for (int i = 0; i < availableItems.length; i++) {
                BigDecimal newPrice = availableItems[i].getPrice().multiply(discount);

                availableItems[i].setPrice(newPrice);
            }

            isDiscounted = true;
        }
    }

    @Override
    public boolean rateItem(StoreItem item, int rating) {
        boolean isRated = false;

        if (rating >= 1 && rating <= 5) {

            for (StoreItem i : availableItems) {
                if (i.getTitle().equals(item.getTitle())) {
                    i.rate(rating);
                    isRated = true;
                    break;
                }
            }
        }

        return isRated;
    }

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        StoreItem dlc = new DLC("CS:GO", new BigDecimal("10.99"), now,
                new Game("CS",
                        new BigDecimal("5.99"),
                        now,
                        "Shooter"));

        StoreItem game = new Game("Outlast", new BigDecimal("4.99"), now, "Horror");

        StoreItem gameBundle = new GameBundle("Fortnite starter pack", new BigDecimal("24.99"), now,
                new Game[]{new Game("Fortnite 1", new BigDecimal("1.99"), now, "Action"),
                        new Game("Fortnite 2", new BigDecimal("2.99"), now, "Action"),
                        new Game("Fortnite 3", new BigDecimal("3.99"), now, "Action")
                });

        StoreAPI storeItem = new GameStore(new StoreItem[]{dlc, game, gameBundle});

        ItemFilter[] filters = new ItemFilter[]{
                new PriceItemFilter(new BigDecimal("10.99"), new BigDecimal("20.99"))
        };

        storeItem.applyDiscount("VAN40");
        System.out.println(storeItem);
//        System.out.println(Arrays.toString(storeItem.findItemByFilters(filters)));
    }

    @Override
    public String toString() {
        return Arrays.toString(availableItems);
    }
}
