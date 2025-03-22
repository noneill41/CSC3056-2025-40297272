package org.jfree.data;
import org.junit.Test;
import static org.junit.Assert.*;

public class RangeTest {
	// --- Constructor and Accessor Tests ---

    // Test the constructor with valid bounds.
    @Test
    public void testConstructor_Valid() {
        Range r = new Range(0, 10);
        assertEquals("Lower bound should be 0", 0.0, r.getLowerBound(), 1e-7);
        assertEquals("Upper bound should be 10", 10.0, r.getUpperBound(), 1e-7);
    }

    // Test that the constructor throws an exception when lower > upper.
    @Test(expected = IllegalArgumentException.class)
    public void testConstructor_Invalid() {
        new Range(10, 0);
    }

    // Test getLength method.
    @Test
    public void testGetLength() {
        Range r = new Range(5, 15);
        assertEquals("Length should be 10", 10.0, r.getLength(), 1e-7);
    }

    // Test getCentralValue method.
    @Test
    public void testGetCentralValue() {
        Range r = new Range(2, 10);
        // Central value = (2/2 + 10/2) = 6.0
        assertEquals("Central value should be 6.0", 6.0, r.getCentralValue(), 1e-7);
    }


    // Test contains method.
    @Test
    public void testContains() {
        Range r = new Range(0, 10);
        assertTrue("Should contain 5", r.contains(5));
        assertTrue("Should contain lower bound", r.contains(0));
        assertTrue("Should contain upper bound", r.contains(10));
        assertFalse("Should not contain value below lower bound", r.contains(-1));
        assertFalse("Should not contain value above upper bound", r.contains(11));
    }
    
 // Test that a zero-length range (lower == upper) only contains its single value.
    @Test
    public void testContains_ZeroLengthRange() {
        Range r = new Range(5, 5);
        assertTrue("Zero-length range should contain its single value", r.contains(5.0));
        assertFalse("Zero-length range should not contain a value slightly less than its value", r.contains(4.999));
        assertFalse("Zero-length range should not contain a value slightly greater than its value", r.contains(5.001));
    }

    // Test that a range with negative bounds correctly determines membership.
    @Test
    public void testContains_NegativeRange() {
        Range r = new Range(-10, -5);
        assertTrue("Negative range should contain a value within bounds", r.contains(-7.5));
        assertTrue("Negative range should contain its lower bound", r.contains(-10.0));
        assertTrue("Negative range should contain its upper bound", r.contains(-5.0));
        assertFalse("Negative range should not contain a value below the lower bound", r.contains(-11));
        assertFalse("Negative range should not contain a value above the upper bound", r.contains(-4));
    }

    // Test that a range containing zero correctly identifies membership.
    @Test
    public void testContains_RangeContainingZero() {
        Range r = new Range(-5, 5);
        assertTrue("Range containing zero should contain 0", r.contains(0));
        assertTrue("Range containing zero should contain its lower bound", r.contains(-5));
        assertTrue("Range containing zero should contain its upper bound", r.contains(5));
        assertFalse("Range containing zero should not contain a value just above the upper bound", r.contains(5.1));
        assertFalse("Range containing zero should not contain a value just below the lower bound", r.contains(-5.1));
    }

    // Test that a value infinitesimally below the lower bound is not contained.
    @Test
    public void testContains_JustBelowLowerBound() {
        Range r = new Range(0, 10);
        double justBelowLower = -1e-9;
        assertFalse("Value just below lower bound should not be contained", r.contains(justBelowLower));
    }

    // Test that a value infinitesimally above the upper bound is not contained.
    @Test
    public void testContains_JustAboveUpperBound() {
        Range r = new Range(0, 10);
        double justAboveUpper = 10.0 + 1e-9;
        assertFalse("Value just above upper bound should not be contained", r.contains(justAboveUpper));
    }

    // Test intersects method when the input range's lower bound is <= this.lower.
    @Test
    public void testIntersects_Branch1_True() {
        Range r = new Range(0, 10);
        // Test range with lower <= 0 and upper > 0.
        assertTrue("Intersects should be true for range [-5, 5]", r.intersects(-5, 5));
    }
    
    @Test
    public void testIntersects_Branch1_False() {
        Range r = new Range(0, 10);
        // Test range with lower <= 0 but upper <= 0.
        assertFalse("Intersects should be false for range [-5, 0]", r.intersects(-5, 0));
    }
    
    // Test intersects when the input range's lower bound is > this.lower.
    @Test
    public void testIntersects_Branch2_True() {
        Range r = new Range(0, 10);
        // For [1, 9]: since 9 < 10 and 9 >= 1, returns true.
        assertTrue("Intersects should be true for range [1, 9]", r.intersects(1, 9));
    }
    
    @Test
    public void testIntersects_Branch2_False() {
        Range r = new Range(0, 10);
        // For [1, 11]: upper bound 11 does not satisfy (11 < 10)
        assertFalse("Intersects should be false for range [1, 11]", r.intersects(1, 11));
    }
    

    // Test constrain: value within the range.
    @Test
    public void testConstrain_Within() {
        Range r = new Range(0, 10);
        assertEquals("Constrain returns same value when within range", 5.0, r.constrain(5.0), 1e-7);
    }

    // Test constrain: value below the range.
    @Test
    public void testConstrain_Below() {
        Range r = new Range(0, 10);
        assertEquals("Constrain returns lower bound if value below range", 0.0, r.constrain(-5.0), 1e-7);
    }
    
    // Test constrain: value above the range.
    @Test
    public void testConstrain_Above() {
        Range r = new Range(0, 10);
        assertEquals("Constrain returns upper bound if value above range", 10.0, r.constrain(15.0), 1e-7);
    }

    // Test combine with both ranges non-null.
    @Test
    public void testCombine_BothNonNull() {
        Range r1 = new Range(0, 10);
        Range r2 = new Range(5, 15);
        Range combined = Range.combine(r1, r2);
        assertEquals("Combined lower bound should be 0", 0.0, combined.getLowerBound(), 1e-7);
        assertEquals("Combined upper bound should be 15", 15.0, combined.getUpperBound(), 1e-7);
    }

    // Test combine when first range is null.
    @Test
    public void testCombine_FirstNull() {
        Range r2 = new Range(5, 15);
        Range combined = Range.combine(null, r2);
        assertEquals("When first range is null, combine returns second range", r2, combined);
    }
    
    // Test combine when second range is null.
    @Test
    public void testCombine_SecondNull() {
        Range r1 = new Range(0, 10);
        Range combined = Range.combine(r1, null);
        // Per implementation, if range2 is null, returns range2 (null).
        assertNull("When second range is null, combine returns null", combined);
    }
    
    // Test combine when both ranges are null.
    @Test
    public void testCombine_BothNull() {
        Range combined = Range.combine(null, null);
        assertNull("When both ranges are null, combine returns null", combined);
    }
    
    // Test expandToInclude: when range is null.
    @Test
    public void testExpandToInclude_NullRange() {
        Range expanded = Range.expandToInclude(null, 5);
        assertEquals("Expanded range lower bound should equal value", 5.0, expanded.getLowerBound(), 1e-7);
        assertEquals("Expanded range upper bound should equal value", 5.0, expanded.getUpperBound(), 1e-7);
    }
    
    // Test expandToInclude: value below current lower bound.
    @Test
    public void testExpandToInclude_ValueBelow() {
        Range r = new Range(5, 10);
        Range expanded = Range.expandToInclude(r, 2);
        assertEquals("Expanded lower bound should update", 2.0, expanded.getLowerBound(), 1e-7);
        assertEquals("Expanded upper bound should remain unchanged", 10.0, expanded.getUpperBound(), 1e-7);
    }
    
    // Test expandToInclude: value above current upper bound.
    @Test
    public void testExpandToInclude_ValueAbove() {
        Range r = new Range(5, 10);
        Range expanded = Range.expandToInclude(r, 15);
        assertEquals("Expanded lower bound should remain unchanged", 5.0, expanded.getLowerBound(), 1e-7);
        assertEquals("Expanded upper bound should update", 15.0, expanded.getUpperBound(), 1e-7);
    }
    
    // Test expandToInclude: value within the range.
    @Test
    public void testExpandToInclude_ValueWithin() {
        Range r = new Range(5, 10);
        Range expanded = Range.expandToInclude(r, 7);
        assertEquals("Range should remain unchanged if value is within", r, expanded);
    }
    
    // Test expand method.
    @Test
    public void testExpand() {
        Range r = new Range(2, 6);
        // Length = 4, lower margin = 4 * 0.25 = 1, upper margin = 4 * 0.5 = 2, so new range [1, 8]
        Range expanded = Range.expand(r, 0.25, 0.5);
        assertEquals("Expanded lower bound", 1.0, expanded.getLowerBound(), 1e-7);
        assertEquals("Expanded upper bound", 8.0, expanded.getUpperBound(), 1e-7);
    }
    
    @Test(expected = IllegalArgumentException.class)
    public void testExpand_NullRange() {
        Range.expand(null, 0.25, 0.5);
    }
    
    
    // Test shift with allowZeroCrossing false.
    @Test
    public void testShift_NoZeroCrossing() {
        Range r = new Range(5, 15);
        // Shift left by -10, so lower bound: shiftWithNoZeroCrossing(5, -10) = Math.max(5-10, 0) = 0
        // Upper bound: shiftWithNoZeroCrossing(15, -10) = Math.max(15-10, 0) = 5
        Range shifted = Range.shift(r, -10, false);
        assertEquals("Shifted lower bound should be 0", 0.0, shifted.getLowerBound(), 1e-7);
        assertEquals("Shifted upper bound should be 5", 5.0, shifted.getUpperBound(), 1e-7);
    }
    
    // Test shift with allowZeroCrossing true.
    @Test
    public void testShift_AllowZeroCrossing() {
        Range r = new Range(5, 15);
        // With allowZeroCrossing, shift is simply lower+delta and upper+delta.
        Range shifted = Range.shift(r, -10, true);
        assertEquals("Shifted lower bound should be -5", -5.0, shifted.getLowerBound(), 1e-7);
        assertEquals("Shifted upper bound should be 5", 5.0, shifted.getUpperBound(), 1e-7);
    }
    
    // Test default shift method (shift without allowZeroCrossing parameter defaults to false).
    @Test
    public void testShift_Default() {
        Range r = new Range(5, 15);
        Range shifted = Range.shift(r, 10);
        // For positive values with delta=10: shiftWithNoZeroCrossing returns Math.max(5+10, 0)=15 and Math.max(15+10, 0)=25.
        assertEquals("Shifted lower bound using default shift", 15.0, shifted.getLowerBound(), 1e-7);
        assertEquals("Shifted upper bound using default shift", 25.0, shifted.getUpperBound(), 1e-7);
    }
    
    
    // Test equals for two equal ranges.
    @Test
    public void testEquals_SameValues() {
        Range r1 = new Range(0, 10);
        Range r2 = new Range(0, 10);
        assertTrue("Ranges with identical bounds should be equal", r1.equals(r2));
    }
    
    // Test equals for two different ranges.
    @Test
    public void testEquals_DifferentValues() {
        Range r1 = new Range(0, 10);
        Range r2 = new Range(0, 15);
        assertFalse("Ranges with different bounds should not be equal", r1.equals(r2));
    }
    
    // Test equals with an object of a different type.
    @Test
    public void testEquals_NonRangeObject() {
        Range r = new Range(0, 10);
        assertFalse("Range should not equal a non-range object", r.equals("Not a Range"));
    }
    
    // Test that equal ranges have the same hash code.
    @Test
    public void testHashCode() {
        Range r1 = new Range(0, 10);
        Range r2 = new Range(0, 10);
        assertEquals("Equal ranges should have the same hash code", r1.hashCode(), r2.hashCode());
    }
    
    // Test toString method.
    @Test
    public void testToString() {
        Range r = new Range(2, 8);
        String expected = "Range[2.0,8.0]";
        assertEquals("toString should return the proper format", expected, r.toString());
    }
}
