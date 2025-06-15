public class Co_ord {
    int x;
    int y;

    public Co_ord(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int x_to_list(){
        return x - 1;
    }

    public int y_to_list(){
        return 9 - y;
    }

}
