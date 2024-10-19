package bg.sofia.uni.fmi.mjt.gameplatform.store.item.filter;

import bg.sofia.uni.fmi.mjt.gameplatform.store.item.StoreItem;

public class TitleItemFilter implements ItemFilter{
    private final String title;
    private boolean caseSensitive;
    public TitleItemFilter(String title, boolean caseSensitive) {
        this.title = title;
        setCaseSensitive(caseSensitive);
    }

    @Override
    public boolean matches(StoreItem item) {
        if(caseSensitive) {
            return item.getTitle().contains(title);
        }else {
            return item.getTitle().toLowerCase().contains(title.toLowerCase());
        }

    }

    public boolean isCaseSensitive() {
        return caseSensitive;
    }

    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }


}