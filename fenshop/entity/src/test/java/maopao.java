import java.util.Arrays;

public class maopao {
    public static void main(String[] args) {
        int[] a = {1, 8, 4, 3, 24, 23};  //从大到小排
        int len=a.length;
        for (int i=0;i<len-1;i++){
            for (int j=0;j<len-1-i;j++){ //刚开始比较len-1次，随着i变，i表示比较成功的数，减一个i就是说就不用比较i个了
                if (a[j]<a[j+1]){
                    int temp=0;
                    temp=a[j];
                    a[j]=a[j+1];
                    a[j+1]=temp;
                }
            }
        }
        System.out.println(Arrays.toString(a));
    }
}
