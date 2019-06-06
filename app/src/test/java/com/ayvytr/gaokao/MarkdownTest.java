package com.ayvytr.gaokao;

import org.commonmark.internal.util.FormulaUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author admin
 */
public class MarkdownTest {
    @Test
    public void testDbc() {
        String input = "$\\frac{a}{b} > 1 \\Leftrightarrow a >\n" +
                "b;\\frac{a}{b} = 1 \\Leftrightarrow a = b;\\frac{a}{b} < 1 \\Leftrightarrow a < b;$";
        String toDbc = FormulaUtil.toDbc(input);
        Assert.assertEquals(input, toDbc);
    }
}
