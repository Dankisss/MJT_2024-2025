package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

import java.math.BigDecimal;

public class PriceItemFilter implements ItemFilter{
    private final BigDecimal lowerBound;
    private final BigDecimal upperBound;

    public PriceItemFilter(BigDecimal lowerBound, BigDecimal upperBound) {
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }

    @Override
    public boolean matches(StoreItem item) {
        boolean low = lowerBound.compareTo(item.getPrice()) <= 0;
        boolean high = upperBound.compareTo(item.getPrice()) >= 0;

        return low && high;
    }
}
