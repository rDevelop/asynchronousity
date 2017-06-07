import org.junit.Test;

/**
 * Created by rob on 6/7/17.
 */
public class RandomTest {

    @Test
    public void randomNum() {
        int n  = 400;
        int min = n;
        int max = 0;
        for(int i=0; i<1000; i++) {
            int r = (int)(Math.random() * n);
            if(r > max ) {max = r;}
            if(r < min ) {min = r;}
        }
        System.out.println("Min: " + min + " - " + " Max: " + max );
    }
}
