package projlab.rail.logic;

public enum Color {

    RED(java.awt.Color.RED), BLUE(java.awt.Color.BLUE), GREEN(java.awt.Color.GREEN), YELLOW(java.awt.Color.YELLOW);

    public final java.awt.Color value;

    Color(java.awt.Color value) {
        this.value = value;
    }
}