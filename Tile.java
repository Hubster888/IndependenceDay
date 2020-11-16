
abstract class Tile {
    private double chanceOfAppearanceInBag;
    private final String type;

    public String getTileType() {
        return type;
    }

    public abstract void executeTile() {

    }

    public double getChangeOfAppearing() {

    }
}