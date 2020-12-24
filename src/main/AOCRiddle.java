package main;

public abstract class AOCRiddle {

    public void setInput(String input) {
        this.input = input;
    }

    private String input;
    private final int part;

    public AOCRiddle(String in, int part){
        this.input = in.replaceAll("\r","");
        this.part = part;
    }


    public String solve(){
        return part==1?solve1():solve2();
    }

    protected abstract String solve1();

    protected abstract String solve2();

    public String getInput() {
        return input;
    }
}
