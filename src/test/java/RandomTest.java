import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

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
            int r = ThreadLocalRandom.current().nextInt(0, n+1);
            if(r > max ) {max = r;}
            if(r < min ) {min = r;}
        }
        System.out.println("Min: " + min + " - " + " Max: " + max );
    }
}
