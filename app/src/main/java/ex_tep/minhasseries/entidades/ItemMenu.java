package ex_tep.minhasseries.entidades;

/**
 * Created by Anailson on 08/07/2015.
 */
public class ItemMenu {

    private String title;
    private int icon;
    private String count;
    private boolean isCounterVisible;

    public ItemMenu(String title, int icon) {
        this.title = title;
        this.icon = icon;
        this.count = "";
        this.isCounterVisible = false;
    }

    public ItemMenu(String title, int icon, boolean isCounterVisible, String count) {
        this(title, icon);
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public String getCount() {
        return count;
    }

    public boolean getCounterVisible() {
        return isCounterVisible;
    }
}
