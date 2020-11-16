
abstract class Tile {
    private double chanceOfAppearanceInBag;
    private final String type;

    public Tile (String type, double chanceOfAppearanceInBag) {
        this.type = type;
        this.chanceOfAppearanceInBag = chanceOfAppearanceInBag;
    }

    public String getTileType() {
        return type;
    }

    public double getChangeOfAppearing() {
        return chanceOfAppearanceInBag;
    }

    public abstract void executeTile();
}