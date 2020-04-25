import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import pl.pjm77.misc.ValidateParameter;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class ValidateParameterTests {

    private final ByteArrayOutputStream mockOutput = new ByteArrayOutputStream();
    private final PrintStream originalOutput = System.out;
    public static final String EXPECTED_FAIL_MESSAGE = "Error!\r\n";
    public static final String EXPECTED_SUCCESS_MESSAGE = "";


    @Before
    public void setup() throws Exception {
        System.setOut(new PrintStream(mockOutput));
    }

    @After
    public void tearDown() throws Exception {
        System.setOut(originalOutput);
    }

    @Test
    public void testLongForNull() throws Exception {
        Assert.assertEquals(ValidateParameter.checkLong(null, "Error!"), -1);
        Assert.assertEquals(EXPECTED_FAIL_MESSAGE, mockOutput.toString());
    }

    @Test
    public void testIntForNull() throws Exception {
        Assert.assertEquals(ValidateParameter.checkInt(null, "Error!"), -1);
        Assert.assertEquals(EXPECTED_FAIL_MESSAGE, mockOutput.toString());
    }

    @Test
    public void testLongForEmptyValue() throws Exception {
        Assert.assertEquals(ValidateParameter.checkLong("", "Error!"), -1);
        Assert.assertEquals(EXPECTED_FAIL_MESSAGE, mockOutput.toString());
    }

    @Test
    public void testIntForEmptyValue() throws Exception {
        Assert.assertEquals(ValidateParameter.checkInt("", "Error!"), -1);
        Assert.assertEquals(EXPECTED_FAIL_MESSAGE, mockOutput.toString());
    }
    
    @Test
    public void testLongForNotNumber() throws Exception {
        Assert.assertEquals(ValidateParameter.checkLong("test", "Error!"), -2);
        Assert.assertEquals(EXPECTED_FAIL_MESSAGE, mockOutput.toString());
    }

    @Test
    public void testIntForNotNumber() throws Exception {
        Assert.assertEquals(ValidateParameter.checkInt("test", "Error!"), -2);
        Assert.assertEquals(EXPECTED_FAIL_MESSAGE, mockOutput.toString());
    }

    @Test
    public void testLongForValidValue() throws Exception {
        Assert.assertEquals(ValidateParameter.checkLong("123", "Error!"), 123);
        Assert.assertEquals(EXPECTED_SUCCESS_MESSAGE, mockOutput.toString());
    }

    @Test
    public void testIntForValidValue() throws Exception {
        Assert.assertEquals(ValidateParameter.checkInt("123", "Error!"), 123);
        Assert.assertEquals(EXPECTED_SUCCESS_MESSAGE, mockOutput.toString());
    }
}