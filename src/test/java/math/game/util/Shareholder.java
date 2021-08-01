package math.game.util;

import java.util.Objects;

public class Shareholder {

    private final int ID;
    private int actionsCount;

    public Shareholder(int id) {
        ID = id;
    }

    public int getID() {
        return ID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shareholder that = (Shareholder) o;
        return ID == that.ID;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(ID);
    }

    public int getActionsCount() {
        return actionsCount;
    }

    public void setActionsCount(int actionsCount) {
        this.actionsCount = actionsCount;
    }
}
