import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class check4MultiTest {

    private int[] inArr;

    public check4MultiTest(int[] inArr) {
        this.inArr = inArr;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new int[] {1, 3, 4, 1, 1, 1}},
                {new int[] {1, 1, 1, 1, 1, 3}}});

    }

    @Test (expected = RuntimeException.class)
    public void check4test() {
        ArrClass.check4(inArr);
    }
}
