package activite32;

import java.io.Serializable;

// Même objet de demande que dans le TP2.
public class Operation implements Serializable {
    private static final long serialVersionUID = 1L;
    private final int x;
    private final char operateur;
    private final int y;

    public Operation(int x, char operateur, int y) {
        this.x = x;
        this.operateur = operateur;
        this.y = y;
    }

    public int getX() { return x; }
    public char getOperateur() { return operateur; }
    public int getY() { return y; }
}
