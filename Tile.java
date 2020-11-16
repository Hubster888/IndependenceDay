
abstract class Tile {
    private double chanceOfAppearanceInBag;
    private final String type;

    public String getTileType() {
        return type;
    }

    public double getChangeOfAppearing() {
        return chanceOfAppearanceInBag;
    }

    public abstract void executeTile();
}