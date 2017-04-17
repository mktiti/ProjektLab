package projlab.rail.logic;

public enum Color {

    RED(java.awt.Color.RED), BLUE(java.awt.Color.BLUE), GREEN(java.awt.Color.GREEN), YELLOW(java.awt.Color.YELLOW);

    public final java.awt.Color value;

    Color(java.awt.Color value) {
        this.value = value;
    }

    public static Color lookup(String in) {
        if (in == null || (in = in.trim()).length() == 0) {
            return null;
        }

        for (Color c : Color.values()) {
            if (c.toString().equals(in.toUpperCase())) {
                return c;
            }
        }

        return null;
    }
}