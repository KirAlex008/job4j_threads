package ru.job4j.concurrent.completablefuture;

import org.junit.Test;
import java.util.concurrent.ExecutionException;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

public class RolColSumTest {

    @Test
    public void fullMatrixAsync() throws ExecutionException, InterruptedException {
        int[][] myArray = {{9, 7, 2}, {13, 10, 3}, {8, 4, 12}};
        RolColSum.Sums forTestFirstRowAndCol = new RolColSum.Sums(30, 18);
        RolColSum.Sums forTestSecondRowAndCol = new RolColSum.Sums(21, 26);
        RolColSum.Sums forTestThirdRowAndCol = new RolColSum.Sums(17, 24);
        var rsl = RolColSum.asyncSum(myArray);
        assertThat(rsl[0].toString(), is(forTestFirstRowAndCol.toString()));
        assertThat(rsl[1].toString(), is(forTestSecondRowAndCol.toString()));
        assertThat(rsl[2].toString(), is(forTestThirdRowAndCol.toString()));
    }

    @Test
    public void fullMatrixSimple() {
        int[][] myArray = {{9, 7, 2}, {13, 10, 3}, {8, 4, 12}};
        RolColSum.Sums forTestFirstRowAndCol = new RolColSum.Sums(30, 18);
        RolColSum.Sums forTestSecondRowAndCol = new RolColSum.Sums(21, 26);
        RolColSum.Sums forTestThirdRowAndCol = new RolColSum.Sums(17, 24);
        var rsl = RolColSum.sum(myArray);
        assertThat(rsl[0].toString(), is(forTestFirstRowAndCol.toString()));
        assertThat(rsl[1].toString(), is(forTestSecondRowAndCol.toString()));
        assertThat(rsl[2].toString(), is(forTestThirdRowAndCol.toString()));
    }

}
