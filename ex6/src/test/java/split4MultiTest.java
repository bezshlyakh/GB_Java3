import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class split4MultiTest {

    private int[] inArr;
    private int[] outArr;

    public split4MultiTest(int[] inArr, int[] outArr) {
        this.inArr = inArr;
        this.outArr = outArr;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new int[] {1, 1, 4, 4, 1, 1}, new int [] {1, 1}},
                {new int[] {1, 1, 4, 4, 1, 4}, new int [0] },
                {new int[] {1, 3, 4, 4, 1, 1}, new int [] {1, 1}},
                {new int[] {1, 1, 4, 1, 1, 3}, new int [] {1, 1, 3}}});

    }

    @Test
    public void check4test() {
        Assert.assertArrayEquals(outArr, ArrClass.check4(inArr));
    }
}
