public class Co_ord {
    int x;
    int y;

    public Co_ord(int x, int y) {
        if (x < 1 || x > 9 || y < 1 || y > 9)
            throw new NumberFormatException();
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Co_ord c = (Co_ord) obj;
        return (c.x == this.x && c.y == this.y);
    }

    public int x_to_list() {
        return x - 1;
    }

    public int y_to_list() {
        return 9 - y;
    }

}
