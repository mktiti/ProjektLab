package projlab.rail.logic;

public enum Color {

    RED(new java.awt.Color(237, 28, 36)), BLUE(new java.awt.Color(0, 162, 232)), GREEN(new java.awt.Color(34, 177, 76)), YELLOW(new java.awt.Color(255, 242, 0));

    public final java.awt.Color value;

    Color(java.awt.Color value) {
        this.value = value;
    }

    /**
        Retrieves Color with the given name. Case insensitive and null-safe.
        @param in the name of the color
        @return The color or null if the color is not found
     */
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