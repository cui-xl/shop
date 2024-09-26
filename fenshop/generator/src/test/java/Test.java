public class Test {
    public static void main(String[] args) {
        char[] arrs = new char[]{'a','b','c','d','e','f','g','h','i','j','k','1','4','1','6','2'};
        for (int i=0;i<4;i++){
            System.out.print(fun(arrs));
        }



    }
    public static char fun(char[] arr){
        return arr[(int)(Math.random()*arr.length)];


    }

}
