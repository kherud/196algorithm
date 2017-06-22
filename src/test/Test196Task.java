package test;

import main.Application;
import main.Task;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.assertEquals;

public class Test196Task {

    private Task task;
    private Application app;

    @Before
    public void setup() {
        BigInteger border = BigInteger.ONE;
        app = new Application(border, border); // only used to set up task
        task = new Task(1, app, border, border);
    }

    @Test
    public void testSingleDigitNumbers() {
        assertEquals(BigInteger.valueOf(2), task.getNumber(BigInteger.ONE));
        assertEquals(BigInteger.valueOf(4), task.getNumber(BigInteger.valueOf(2)));
        assertEquals(BigInteger.valueOf(6), task.getNumber(BigInteger.valueOf(3)));
        assertEquals(BigInteger.valueOf(11), task.getNumber(BigInteger.valueOf(5)));
        assertEquals(BigInteger.valueOf(77), task.getNumber(BigInteger.valueOf(8)));
        assertEquals(BigInteger.valueOf(44), task.getNumber(BigInteger.valueOf(13)));
        assertEquals(BigInteger.valueOf(33), task.getNumber(BigInteger.valueOf(21)));
        assertEquals(BigInteger.valueOf(77), task.getNumber(BigInteger.valueOf(34)));
        assertEquals(BigInteger.valueOf(121), task.getNumber(BigInteger.valueOf(55)));
        assertEquals(new BigInteger("8813200023188"), task.getNumber(BigInteger.valueOf(89)));
    }

    @Test
    public void MultipleDigitNumbers() {
        assertEquals(BigInteger.valueOf(11), task.getNumber(BigInteger.valueOf(10)));
        assertEquals(BigInteger.valueOf(22), task.getNumber(BigInteger.valueOf(20)));
        assertEquals(BigInteger.valueOf(33), task.getNumber(BigInteger.valueOf(30)));
        assertEquals(BigInteger.valueOf(55), task.getNumber(BigInteger.valueOf(50)));
        assertEquals(BigInteger.valueOf(88), task.getNumber(BigInteger.valueOf(80)));
        assertEquals(BigInteger.valueOf(161), task.getNumber(BigInteger.valueOf(130)));
        assertEquals(BigInteger.valueOf(9339), task.getNumber(BigInteger.valueOf(195)));
        assertEquals(BigInteger.valueOf(1001), task.getNumber(BigInteger.valueOf(1000)));
        assertEquals(BigInteger.valueOf(8836388), task.getNumber(BigInteger.valueOf(9998)));
        assertEquals(new BigInteger("49748722784794"), task.getNumber(BigInteger.valueOf(9999998)));
    }

    @Test
    public void testIsPalindromeExpectTrue() {
        Assert.assertTrue(task.isPalindrome(BigInteger.valueOf(1)));
        Assert.assertTrue(task.isPalindrome(BigInteger.valueOf(121)));
        Assert.assertTrue(task.isPalindrome(BigInteger.valueOf(12321)));
        Assert.assertTrue(task.isPalindrome(BigInteger.valueOf(1234321)));
        Assert.assertTrue(task.isPalindrome(BigInteger.valueOf(123454321)));
        Assert.assertTrue(task.isPalindrome(new BigInteger("12345654321")));
        Assert.assertTrue(task.isPalindrome(new BigInteger("1234567654321")));
        Assert.assertTrue(task.isPalindrome(new BigInteger("98765432123456789")));
        Assert.assertTrue(task.isPalindrome(new BigInteger("789876543212345678987")));
        Assert.assertTrue(task.isPalindrome(new BigInteger("12398765432123456789321")));
    }

    @Test
    public void testIsPalindromeExpectFalse() {
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(12)));
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(123)));
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(1234)));
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(12345)));
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(123456)));
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(1234567)));
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(12345678)));
        Assert.assertFalse(task.isPalindrome(BigInteger.valueOf(123456789)));
        Assert.assertFalse(task.isPalindrome(new BigInteger("12345678901234567890")));
        Assert.assertFalse(task.isPalindrome(new BigInteger("123456789012345678901234567890")));
    }

    @Test
    public void testInvert(){
        assertEquals(BigInteger.valueOf(1), task.invertNumber(BigInteger.valueOf(1)));
        assertEquals(BigInteger.valueOf(1), task.invertNumber(BigInteger.valueOf(10)));
        assertEquals(BigInteger.valueOf(12), task.invertNumber(BigInteger.valueOf(21)));
        assertEquals(BigInteger.valueOf(73), task.invertNumber(BigInteger.valueOf(37)));
        assertEquals(BigInteger.valueOf(24), task.invertNumber(BigInteger.valueOf(42)));
        assertEquals(BigInteger.valueOf(321), task.invertNumber(BigInteger.valueOf(123)));
        assertEquals(BigInteger.valueOf(4321), task.invertNumber(BigInteger.valueOf(1234)));
        assertEquals(BigInteger.valueOf(54321), task.invertNumber(BigInteger.valueOf(12345)));
        assertEquals(BigInteger.valueOf(654321), task.invertNumber(BigInteger.valueOf(123456)));
        assertEquals(BigInteger.valueOf(7654321), task.invertNumber(BigInteger.valueOf(1234567)));
    }
}