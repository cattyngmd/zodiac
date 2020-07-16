package cat.yoink.zodiac.module.manager.module;

public enum Category {
    COMBAT("Combat", 20),
    MISC("Misc", 160),
    RENDER("Render", 280);

    private String name;
    private int X;
    private int Y;
    private int width;
    private int height;
    private boolean expanded;

    Category(final String name, int X) {
        setName(name);
        setX(X);
        setY(20);
        setWidth(100);
        setHeight(15);
        setExpanded(true);
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getX() {
        return X;
    }
    public void setX(int x) {
        X = x;
    }
    public int getY() {
        return Y;
    }
    public void setY(int y) {
        Y = y;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public boolean isExpanded() {
        return expanded;
    }
    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
