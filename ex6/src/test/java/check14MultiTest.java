import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class check14MultiTest {

    private int[] inArr;
    private boolean has14;

    public check14MultiTest(int[] inArr, boolean has14) {
        this.inArr = inArr;
        this.has14 = has14;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {new int[] {1, 1, 4, 4, 1, 1}, true},
                {new int[] {1, 1, 1, 1, 1, 1}, false},
                {new int[] {1, 3, 4, 4, 1, 1}, false},
                {new int[] {4, 4, 4, 4, 4, 4}, false}});

    }

    @Test
    public void check14test() {
        Assert.assertEquals(has14, ArrClass.has14(inArr));
    }
}
