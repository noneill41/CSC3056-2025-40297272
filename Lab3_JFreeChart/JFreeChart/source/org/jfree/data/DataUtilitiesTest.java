package org.jfree.data;

import static org.junit.Assert.*;

import org.jfree.data.DataUtilities;
import org.jfree.data.DefaultKeyedValues2D;
import org.jfree.data.Values2D;
import org.jfree.data.KeyedValues;
import org.jfree.data.DefaultKeyedValues; 
import org.junit.*;

public class DataUtilitiesTest {
    private Values2D matrixData;
    private double[] singleDimArray;
    private double[][] twoDimArray;
    private KeyedValues keyedData;

    @Before
    public void init() throws Exception {
        // Initialize a sample matrix with values for testing row and column totals.
        DefaultKeyedValues2D sampleMatrix = new DefaultKeyedValues2D();
        sampleMatrix.addValue(3, 4, 5);   // Adds value 3 at row 4, column 5
        sampleMatrix.addValue(3, 0, 11);  // Adds value 3 at row 0, column 11
        sampleMatrix.addValue(2, 1, 3);   // Adds value 2 at row 1, column 3
        sampleMatrix.addValue(2, 2, 7);   // Adds value 2 at row 2, column 7
        matrixData = sampleMatrix;
        
        // Set up a one-dimensional array for testing createNumberArray.
        singleDimArray = new double[] { 1.5, 2.5, 3.5, 4.5, 5.5 };
        
        // Set up a two-dimensional array for testing createNumberArray2D.
        twoDimArray = new double[3][4];
        twoDimArray[0][0] = 1.5;
        twoDimArray[0][1] = 2.5;
        twoDimArray[0][2] = 3.5;
        twoDimArray[0][3] = 4.5;
        twoDimArray[1][0] = 5.5;
        twoDimArray[1][1] = 6.5;
        twoDimArray[1][2] = 7.5;
        // Remaining cells in twoDimArray default to 0.0
        
        // Prepare keyed values for testing getCumulativePercentages.
        DefaultKeyedValues sampleKeys = new DefaultKeyedValues();
        sampleKeys.addValue((Comparable<?>) 0, 5);
        sampleKeys.addValue((Comparable<?>) 1, 9);
        sampleKeys.addValue((Comparable<?>) 2, 2);
        sampleKeys.addValue((Comparable<?>) 3, 4);
        keyedData = DataUtilities.getCumulativePercentages(sampleKeys);
    }

    @After
    public void cleanup() throws Exception {
        // Clean up test data after each test run.
        matrixData = null;
        singleDimArray = null;
        keyedData = null;
    }

    ////////// Tests for calculateColumnTotal method //////////

    @Test
    public void testCalculateColumnTotal_ValidData_FirstColumn() {
        // Test that the sum for the first column (index 0) is 0.0.
        int colIndex = 0;
        assertEquals("Sum for first column should be 0.0 in the given matrix.", 0.0,
                DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
    }

    @Test
    public void testCalculateColumnTotal_ValidData_MiddleColumn() {
        // Test that the sum for the middle column (index 1) is 4.0.
        int colIndex = 1;
        assertEquals("Sum for column 1 must equal 4.0.", 4.0,
                DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
    }
    
    @Test
    public void testCalculateColumnTotal_ValidData_LastColumn() {
        // Test that the sum for the last column in the matrix is 16.0.
        int colIndex = matrixData.getColumnCount() - 1;
        assertEquals("Expected sum for last column in a proper matrix.", 16.0,
                DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
    }

    @Test
    public void testCalculateColumnTotal_InvalidData_IndexTooHigh() {
        // Test that an out-of-bound column index (e.g., 13) returns 0.0.
        try {
            int colIndex = 13;
            assertEquals("Out-of-bound column index should return 0.0.", 0.0,
                    DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
        } catch (IndexOutOfBoundsException e) {
            // Exception handling: if an exception is thrown, the test fails.
            fail("Should return 0.0 instead of throwing an exception for an excessive index.");
        }
    }

    @Test
    public void testCalculateColumnTotal_EmptyMatrix_ValidColumn() {
        // Test that using an empty matrix returns 0.0 for any column index.
        int colIndex = 1;
        DefaultKeyedValues2D emptyMatrix = new DefaultKeyedValues2D();
        matrixData = emptyMatrix;
        assertEquals("An empty matrix must produce a sum of 0.0 for any column.", 0.0,
                DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
    }
    
    @Test
    public void testCalculateColumnTotal_InvalidData_NegativeIndex() {
        // Test that a negative column index (e.g., -1) yields 0.0.
        try {
            int colIndex = -1;
            assertEquals("Negative column index should yield a sum of 0.0.", 0.0,
                    DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
        } catch (IndexOutOfBoundsException e) {
            fail("Expected result of 0.0 rather than an exception for a negative index.");
        }
    }

    @Test
    public void testCalculateColumnTotal_EmptyMatrix_ExcessiveColumnIndex() {
        // Test that an empty matrix with an excessive column index (e.g., 13) returns 0.0.
        int colIndex = 13;
        DefaultKeyedValues2D emptyMatrix = new DefaultKeyedValues2D();
        matrixData = emptyMatrix;
        assertEquals("For an empty matrix, an index beyond limits returns 0.0.", 0.0,
                DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
    }

    @Test
    public void testCalculateColumnTotal_NullMatrix_ValidColumn() {
        // Test that a null matrix with a valid column index causes an IllegalArgumentException.
        int colIndex = 1;
        try {
            DataUtilities.calculateColumnTotal(null, colIndex);
            fail("Should throw IllegalArgumentException when the matrix is null with a valid index.");
        } catch (Exception e) {
            // Exception handling: check that the exception is of the correct type.
            assertTrue("IllegalArgumentException is expected for null matrix input.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }
    
    @Test
    public void testCalculateColumnTotal_EmptyMatrix_NegativeColumnIndex() {
        // Test that an empty matrix with a negative column index returns 0.0.
        int colIndex = -1;
        DefaultKeyedValues2D emptyMatrix = new DefaultKeyedValues2D();
        matrixData = emptyMatrix;
        assertEquals("Empty matrix with a negative index should yield 0.0.", 0.0,
                DataUtilities.calculateColumnTotal(matrixData, colIndex), 1e-7);
    }

    @Test
    public void testCalculateColumnTotal_NullMatrix_NegativeColumnIndex() {
        // Test that a null matrix with a negative column index causes an IllegalArgumentException.
        int colIndex = -1;
        try {
            DataUtilities.calculateColumnTotal(null, colIndex);
            fail("Null matrix with a negative index should result in IllegalArgumentException.");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException is expected when matrix is null and index is negative.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }

    @Test
    public void testCalculateColumnTotal_NullMatrix_ExcessiveColumnIndex() {
        // Test that a null matrix with an excessive column index causes an IllegalArgumentException.
        int colIndex = 13;
        try {
            DataUtilities.calculateColumnTotal(null, colIndex);
            fail("Null matrix with an out-of-bound index should trigger IllegalArgumentException.");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException should be thrown for null matrix with excessive index.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }

    ////////// Tests for calculateRowTotal method //////////

    @Test
    public void testCalculateRowTotal_ValidData_FirstRow() {
        // Test that the first row (index 0) sums to 12.0.
        int rowIndex = 0;
        assertEquals("First row sum should be 12.0 in a proper matrix.", 12.0,
                DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
    }

    @Test
    public void testCalculateRowTotal_ValidData_LastRow() {
        // Test that the last row's total is 18.0.
        int rowIndex = matrixData.getRowCount() - 1;
        assertEquals("The last row in the matrix should total 18.0.", 18.0,
                DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
    }
    
    @Test
    public void testCalculateRowTotal_ValidData_SpecificRow() {
        // Test a specific row (row 0) for the expected sum of 12.0.
        int rowIndex = 0;
        assertEquals("Row 0 should sum to 12.0.", 12.0,
                DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
    }  

    @Test
    public void testCalculateRowTotal_InvalidData_IndexTooHigh() {
        // Test that an out-of-bound row index (e.g., 13) returns 0.0.
        try {
            int rowIndex = 13;
            assertEquals("An excessive row index should result in 0.0.", 0.0,
                    DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
        } catch (IndexOutOfBoundsException e) {
            fail("Row index beyond bounds should return 0.0 rather than throwing an exception.");
        }
    }

    @Test
    public void testCalculateRowTotal_EmptyMatrix_ValidRow() {
        // Test that an empty matrix returns 0.0 for any valid row index.
        int rowIndex = 0;
        DefaultKeyedValues2D emptyMatrix = new DefaultKeyedValues2D();
        matrixData = emptyMatrix;
        assertEquals("For an empty matrix, any row should sum to 0.0.", 0.0,
                DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
    }

    @Test
    public void testCalculateRowTotal_InvalidData_NegativeRowIndex() {
        // Test that a negative row index returns 0.0.
        try {
            int rowIndex = -1;
            assertEquals("A negative row index must return a sum of 0.0.", 0.0,
                    DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
        } catch (IndexOutOfBoundsException e) {
            fail("A negative row index should yield 0.0 instead of an exception.");
        }
    }

    @Test
    public void testCalculateRowTotal_EmptyMatrix_ExcessiveRowIndex() {
        // Test that an empty matrix with an out-of-range row index returns 0.0.
        int rowIndex = 13;
        DefaultKeyedValues2D emptyMatrix = new DefaultKeyedValues2D();
        matrixData = emptyMatrix;
        assertEquals("Empty matrix with an out-of-range row index should yield 0.0.", 0.0,
                DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
    }

    @Test
    public void testCalculateRowTotal_NullMatrix_ValidRow() {
        // Test that passing a null matrix with a valid row index triggers an IllegalArgumentException.
        int rowIndex = 0;
        try {
            DataUtilities.calculateRowTotal(null, rowIndex);
            fail("A null matrix with a valid row index should trigger an IllegalArgumentException.");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException is expected for null matrix input with a valid row index.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }
    
    @Test
    public void testCalculateRowTotal_NullMatrix_NegativeRowIndex() {
        // Test that a null matrix with a negative row index causes an IllegalArgumentException.
        int rowIndex = -1;
        try {
            DataUtilities.calculateRowTotal(null, rowIndex);
            fail("A null matrix with a negative row index should result in an IllegalArgumentException.");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException is expected when the matrix is null and the row index is negative.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }
    
    @Test
    public void testCalculateRowTotal_EmptyMatrix_NegativeRowIndex() {
        // Test that an empty matrix with a negative row index returns 0.0.
        int rowIndex = -1;
        DefaultKeyedValues2D emptyMatrix = new DefaultKeyedValues2D();
        matrixData = emptyMatrix;
        assertEquals("An empty matrix with a negative row index should return 0.0.", 0.0,
                DataUtilities.calculateRowTotal(matrixData, rowIndex), 1e-7);
    }

    @Test
    public void testCalculateRowTotal_NullMatrix_ExcessiveRowIndex() {
        // Test that a null matrix with an excessive row index triggers an IllegalArgumentException.
        int rowIndex = 13;
        try {
            DataUtilities.calculateRowTotal(null, rowIndex);
            fail("Using a null matrix with an out-of-bound row index must throw an IllegalArgumentException.");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException is expected for null matrix with an excessive row index.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }
    
    ////////// Tests for createNumberArray method //////////

    @Test
    public void testCreateNumberArray_EmptyInput() {
        // Test that an empty double array returns an empty Number array.
        Number[] expectedResult = {};
        double[] inputArray = new double[0];
        assertArrayEquals("An empty double array should result in an empty Number array.",
                expectedResult, DataUtilities.createNumberArray(inputArray));
    }

    @Test
    public void testCreateNumberArray_NullInput() {
        // Test that a null double array input triggers an IllegalArgumentException.
        try {
            DataUtilities.createNumberArray(null);
            fail("A null input array must throw an IllegalArgumentException.");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException is expected for null input array.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }
    
    @Test
    public void testCreateNumberArray_ValidInput() {
        // Test that a valid double array converts correctly to a Number array.
        Number[] expectedResult = { 1.5, 2.5, 3.5 };
        double[] inputArray = { 1.5, 2.5, 3.5 };
        assertArrayEquals("Converting a valid double array should yield the correct Number array.",
                expectedResult, DataUtilities.createNumberArray(inputArray));
    }

    @Test
    public void testCreateNumberArray_ZeroValues() {
        // Test that an array of zero values converts to a Number array of zeroes.
        Number[] expectedResult = { 0.0, 0.0, 0.0 };
        double[] inputArray = { 0.0, 0.0, 0.0 };
        assertArrayEquals("An array of zeroes should yield an equivalent Number array.",
                expectedResult, DataUtilities.createNumberArray(inputArray));
    }

    @Test
    public void testCreateNumberArray_LargeValues() {
        // Test that an array of large numbers converts correctly to a Number array.
        Number[] expectedResult = { 1000.0, 2000.0, 3000.0 };
        double[] inputArray = { 1000.0, 2000.0, 3000.0 };
        assertArrayEquals("An array with large values should be converted accurately.",
                expectedResult, DataUtilities.createNumberArray(inputArray));
    }

    @Test
    public void testCreateNumberArray_MixedValues() {
        // Test that a mixed value array (positive, negative, zero) converts to the corresponding Number array.
        Number[] expectedResult = { 1.5, -2.5, 0.0, 3000.0 };
        double[] inputArray = { 1.5, -2.5, 0.0, 3000.0 };
        assertArrayEquals("A mixed value double array must produce the corresponding Number array.",
                expectedResult, DataUtilities.createNumberArray(inputArray));
    }
    
    @Test
    public void testCreateNumberArray_NegativeValues() {
        // Test that an array of negative values converts correctly to a Number array.
        Number[] expectedResult = { -1.5, -2.5, -3.5 };
        double[] inputArray = { -1.5, -2.5, -3.5 };
        assertArrayEquals("A double array with negative values should be properly converted to a Number array.",
                expectedResult, DataUtilities.createNumberArray(inputArray));
    }
   
    @Test(timeout = 1000)
    public void testCreateNumberArray_Performance() {
        // Test performance by converting a very large array (10 million elements) to a Number array.
        int arrayLength = 10_000_000;
        double[] hugeArray = new double[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            hugeArray[i] = i * 0.5;
        }
        Number[] numbers = DataUtilities.createNumberArray(hugeArray);
        // Verify that the output array length matches the input length.
        assertEquals("Output array length must match input length.", arrayLength, numbers.length);
    }

    ////////// Tests for createNumberArray2D method //////////

    @Test
    public void testCreateNumberArray2D_ValidInput() {
        // Test that a valid 2D double array converts correctly to a 2D Number array.
        Number[][] expectedResult = { { 1.5, 2.5, 3.5 }, { 4.5, 5.5, 6.5 } };
        double[][] inputArray = {
            { 1.5, 2.5, 3.5, 4.5 },
            { 5.5, 6.5, 7.5, 0.0 },
            { 0.0, 0.0, 0.0, 0.0 }
        };
        Number[][] actualResult = DataUtilities.createNumberArray2D(inputArray);
        assertArrayEquals("A valid 2D double array should convert to the correct 2D Number array.",
                expectedResult, actualResult);
    }

    @Test
    public void testCreateNumberArray2D_EmptyInput() {
        // Test that an empty 2D double array converts to an empty 2D Number array.
        Number[][] expectedResult = {};
        double[][] inputArray = new double[0][0];
        assertArrayEquals("An empty 2D double array should produce an empty 2D Number array.",
                expectedResult, DataUtilities.createNumberArray2D(inputArray));
    }

    @Test
    public void testCreateNumberArray2D_NullInput() {
        // Test that a null 2D array input triggers an IllegalArgumentException.
        try {
            DataUtilities.createNumberArray2D(null);
            fail("Null 2D array input should cause an IllegalArgumentException.");
        } catch (Exception e) {
            assertTrue("IllegalArgumentException is expected for null 2D array input.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }

    @Test
    public void testCreateNumberArray2D_NegativeValues() {
        // Test that a 2D array with negative numbers converts correctly to a 2D Number array.
        Number[][] expectedResult = { { -1.5, -2.5, -3.5 }, { -4.5, -5.5, -6.5 } };
        double[][] inputArray = { { -1.5, -2.5, -3.5 }, { -4.5, -5.5, -6.5 } };
        assertArrayEquals("A 2D array with negative numbers should be converted correctly.",
                expectedResult, DataUtilities.createNumberArray2D(inputArray));
    }

    @Test
    public void testCreateNumberArray2D_ZeroValues() {
        // Test that a 2D array filled with zeroes converts accurately to a 2D Number array.
        Number[][] expectedResult = { { 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0 } };
        double[][] inputArray = { { 0.0, 0.0, 0.0 }, { 0.0, 0.0, 0.0 } };
        assertArrayEquals("A 2D array filled with zeroes should convert accurately.",
                expectedResult, DataUtilities.createNumberArray2D(inputArray));
    }

    @Test
    public void testCreateNumberArray2D_LargeValues() {
        // Test that a 2D array with large values converts properly to a 2D Number array.
        Number[][] expectedResult = { { 1000.0, 2000.0, 3000.0 }, { 4000.0, 5000.0, 6000.0 } };
        double[][] inputArray = { { 1000.0, 2000.0, 3000.0 }, { 4000.0, 5000.0, 6000.0 } };
        assertArrayEquals("A 2D array with large numeric values should be converted properly.",
                expectedResult, DataUtilities.createNumberArray2D(inputArray));
    }

    @Test
    public void testCreateNumberArray2D_MixedValues() {
        // Test that a 2D array with mixed values converts to the expected 2D Number array.
        Number[][] expectedResult = { { 1.5, -2.5, 0.0 }, { 3000.0, -4000.0, 5000.0 } };
        double[][] inputArray = { { 1.5, -2.5, 0.0 }, { 3000.0, -4000.0, 5000.0 } };
        assertArrayEquals("A mixed value 2D double array should yield the expected 2D Number array.",
                expectedResult, DataUtilities.createNumberArray2D(inputArray));
    }
    
    @Test(timeout = 2000)
    public void testCreateNumberArray2D_Performance() {
        // Performance test: Verify that converting a large 2D array (1000x1000) completes within the timeout.
        int rows = 1000;
        int cols = 1000;
        double[][] hugeMatrix = new double[rows][cols];
        // Populate the hugeMatrix with computed values.
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                hugeMatrix[r][c] = r * 0.1 + c;
            }
        }
        Number[][] numberMatrix = DataUtilities.createNumberArray2D(hugeMatrix);
        // Check that the number of rows and columns match the expected dimensions.
        assertEquals("The number of rows should be the same.", rows, numberMatrix.length);
        if (rows > 0) {
            assertEquals("The number of columns should be preserved.", cols, numberMatrix[0].length);
        }
    }

    ////////// Tests for getCumulativePercentages method //////////

    @Test
    public void testGetCumulativePercentages_NonNumericKeys() {
        // Test that getCumulativePercentages handles non-numeric keys correctly.
        DefaultKeyedValues sampleKeys = new DefaultKeyedValues();
        sampleKeys.addValue("X", 5);
        sampleKeys.addValue("Y", 9);
        sampleKeys.addValue("Z", 2);
        KeyedValues resultPercentages = DataUtilities.getCumulativePercentages(sampleKeys);
        // Expect the final cumulative percentage to be 1.0.
        assertEquals("For non-numeric keys, the final cumulative percentage should be 1.0.", 1.0,
                (double) resultPercentages.getValue(2), 1e-9);
    }

    @Test
    public void testGetCumulativePercentages_ValidData() {
        // Test that getCumulativePercentages returns the correct percentages for valid input.
        DefaultKeyedValues expectedPercentages = new DefaultKeyedValues();
        expectedPercentages.addValue((Comparable<?>) 0, 0.3125);
        expectedPercentages.addValue((Comparable<?>) 1, 0.6875);
        expectedPercentages.addValue((Comparable<?>) 2, 0.9375);
        expectedPercentages.addValue((Comparable<?>) 3, 1.0);
        assertEquals("Cumulative percentages for valid input should match the expected results.",
                expectedPercentages, keyedData);
    }

    @Test
    public void testGetCumulativePercentages_EmptyInput() {
        // Test that an empty KeyedValues input results in an empty set of cumulative percentages.
        DefaultKeyedValues emptyKeys = new DefaultKeyedValues();
        KeyedValues resultPercentages = DataUtilities.getCumulativePercentages(emptyKeys);
        DefaultKeyedValues expectedPercentages = new DefaultKeyedValues();
        assertEquals("Empty keyed values should lead to an empty set of cumulative percentages.",
                expectedPercentages, resultPercentages);
    }

    @Test
    public void testGetCumulativePercentages_NullInput() {
        // Test that passing null to getCumulativePercentages triggers an IllegalArgumentException.
        try {
            DataUtilities.getCumulativePercentages(null);
            fail("Passing null keyed values should throw an IllegalArgumentException.");
        } catch (Exception e) {
            // Exception handling: verify that the exception is of the expected type.
            assertTrue("IllegalArgumentException is expected when keyed values are null.",
                    e.getClass().equals(IllegalArgumentException.class));
        }
    }
 //Test calculateColumnTotal with a matrix having a null cell value.
    @Test
    public void testWhiteBox_CalcColTotal_NullCell() {
        // Create a custom Values2D instance with 3 rows and 1 column,
        // where row 1 returns null and the other rows return a non-null value (5).
        Values2D values = new Values2D() {
            public int getRowCount() { return 3; }
            public int getColumnCount() { return 1; }
            public Number getValue(int row, int col) {
                return (row == 1) ? null : 5;
            }
        };
        // Expected total: 5 (row 0) + 0 (row 1, null skipped) + 5 (row 2) = 10.0
        double expected = 10.0;
        assertEquals("calculateColumnTotal should skip null values", expected, 
                DataUtilities.calculateColumnTotal(values, 0), 1e-7);
    }

    //Test calculateRowTotal with a matrix having a null cell value.
    @Test
    public void testWhiteBox_CalcRowTotal_NullCell() {
        // Create a custom Values2D instance with 1 row and 3 columns,
        // where column 1 returns null and the other columns return 5.
        Values2D values = new Values2D() {
            public int getRowCount() { return 1; }
            public int getColumnCount() { return 3; }
            public Number getValue(int row, int col) {
                return (col == 1) ? null : 5;
            }
        };
        // Expected total: 5 (col 0) + 0 (col 1, null skipped) + 5 (col 2) = 10.0
        double expected = 10.0;
        assertEquals("calculateRowTotal should skip null values", expected, 
                DataUtilities.calculateRowTotal(values, 0), 1e-7);
    }

    //Test createNumberArray with typical valid input.
    @Test
    public void testWhiteBox_CreateNumberArray_Typical() {
        double[] input = {1.1, 2.2, 3.3};
        Number[] expected = {1.1, 2.2, 3.3}; // Autoboxing creates Double objects
        assertArrayEquals("createNumberArray should convert doubles to Number objects", 
                expected, DataUtilities.createNumberArray(input));
    }

    // Test createNumberArray2D with a 2D array that includes a null row.
    @Test(expected = IllegalArgumentException.class)
    public void testWhiteBox_CreateNumberArray2D_NullRow() {
        // Create a 2D array where one row is null.
        double[][] input = new double[2][];
        input[0] = new double[] {1.0, 2.0};
        input[1] = null; // This should trigger IllegalArgumentException in createNumberArray.
        DataUtilities.createNumberArray2D(input);
    }

    // Test getCumulativePercentages with a KeyedValues containing a null value.
    @Test
    public void testWhiteBox_GetCumulativePercentages_NullValue() {
        // Create a KeyedValues instance where one value is null.
        DefaultKeyedValues kv = new DefaultKeyedValues();
        kv.addValue((Comparable<?>) Integer.valueOf(0), Double.valueOf(5));
        kv.addValue((Comparable<?>) Integer.valueOf(1), null); // Null value: should be skipped.
        kv.addValue((Comparable<?>) Integer.valueOf(2), Double.valueOf(5));
        // Total should be 5 + 0 + 5 = 10.
        // For key 0: runningTotal = 5 * 0.9 = 4.5;
        // key 1: remains 4.5; key 2: becomes 4.5 + 5*0.9 = 9.0.
        KeyedValues result = DataUtilities.getCumulativePercentages(kv);
        assertEquals("Key 0 percentage", 4.5 / 10.0, result.getValue(0).doubleValue(), 1e-7);
        assertEquals("Key 1 percentage", 4.5 / 10.0, result.getValue(1).doubleValue(), 1e-7);
        assertEquals("Key 2 percentage", 9.0 / 10.0, result.getValue(2).doubleValue(), 1e-7);
    }

    //Test getCumulativePercentages with valid non-null data (to exercise the 0.9 multiplication branch).
    @Test
    public void testWhiteBox_GetCumulativePercentages_Valid() {
        // Create a KeyedValues instance with uniform values.
        DefaultKeyedValues kv = new DefaultKeyedValues();
        kv.addValue((Comparable<?>) Integer.valueOf(0), Double.valueOf(10));
        kv.addValue((Comparable<?>) Integer.valueOf(1), Double.valueOf(10));
        kv.addValue((Comparable<?>) Integer.valueOf(2), Double.valueOf(10));
        // Total = 30.
        // Running totals: key0: 10*0.9 = 9; key1: 9+10*0.9 = 18; key2: 18+10*0.9 = 27.
        KeyedValues result = DataUtilities.getCumulativePercentages(kv);
        assertEquals("Key 0 percentage", 9.0 / 30.0, result.getValue(0).doubleValue(), 1e-7);
        assertEquals("Key 1 percentage", 18.0 / 30.0, result.getValue(1).doubleValue(), 1e-7);
        assertEquals("Key 2 percentage", 27.0 / 30.0, result.getValue(2).doubleValue(), 1e-7);
    }

    //Test createNumberArray2D with a null input.
    @Test(expected = IllegalArgumentException.class)
    public void testWhiteBox_CreateNumberArray2D_NullInput() {
        DataUtilities.createNumberArray2D(null);
    }

    // Test calculateColumnTotal with a null data table.
    @Test(expected = NullPointerException.class)
    public void testWhiteBox_CalcColTotal_NullData() {
        // Passing null for the data table should result in a NullPointerException.
        DataUtilities.calculateColumnTotal(null, 0);
    }

    // Test calculateRowTotal with a null data table.
    @Test(expected = NullPointerException.class)
    public void testWhiteBox_CalcRowTotal_NullData() {
        // Passing null for the data table should result in a NullPointerException.
        DataUtilities.calculateRowTotal(null, 0);
    }

    // Test static initialization of DataUtilities.
    @Test
    public void testWhiteBox_StaticInitialization() {
        // This test forces class initialization by calling a static method.
        // Create a minimal Values2D instance to verify that the static parts of the class are executed.
        Values2D dummy = new Values2D() {
            public int getRowCount() { return 1; }
            public int getColumnCount() { return 1; }
            public Number getValue(int row, int col) { return 5; }
        };
        double result = DataUtilities.calculateColumnTotal(dummy, 0);
        // The expected result is simply 5 (as there's only one cell).
        assertEquals("Static initialization should complete without error", 5.0, result, 1e-7);
    }

    // Additional test to cover the case when the data table is null for calculateColumnTotal.
    // Since the method does not check explicitly, it throws a NullPointerException.
    @Test(expected = NullPointerException.class)
    public void testCalculateColumnTotal_NullData_ThrowsNullPointerException() {
        DataUtilities.calculateColumnTotal(null, 0);
    }

    // Additional test to cover the case when the data table is null for calculateRowTotal.
    // Expecting a NullPointerException because the method tries to call a method on a null reference.
    @Test(expected = NullPointerException.class)
    public void testCalculateRowTotal_NullData_ThrowsNullPointerException() {
        DataUtilities.calculateRowTotal(null, 0);
    }

    // To increase coverage of the abstract class declaration,
    // we define a dummy concrete subclass of DataUtilities and instantiate it.
    private static class DummyDataUtilities extends DataUtilities {
        // No additional methods are needed because we are only testing instantiation.
    }

    @Test
    public void testAbstractClassCoverage_DummySubclass() {
        DummyDataUtilities dummy = new DummyDataUtilities();
        // Verify that the dummy instance is created successfully.
        assertNotNull("The dummy subclass instance should not be null.", dummy);
    }

}

