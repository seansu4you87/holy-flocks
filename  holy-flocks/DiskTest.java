import junit.framework.Assert;
import junit.framework.TestCase;


public class DiskTest extends TestCase {
	private Disk testDisk1;
	private Disk testDisk2;
	
	public void setUp(){
		testDisk1 = new Disk(1);
		testDisk2 = new Disk(2);
	}

	public void testFreeSpace(){
		Assert.assertTrue(testDisk1.freeSpace()==1000000);
		Assert.assertTrue(testDisk2.freeSpace()==1000000);
	}
	
	public void testAdd(){
		testDisk1.add(600000);
		Assert.assertTrue(testDisk1.freeSpace()==400000);
		testDisk1.add(100000);
		Assert.assertTrue(testDisk1.freeSpace()==300000);
	}
	
	public void testToString(){
		Assert.assertEquals(testDisk1.toString(), "1\t1000000:\t");
		testDisk1.add(600000);
		Assert.assertEquals(testDisk1.toString(), "1\t400000:\t 600000");
	}
	
	public void testEquals(){
		Assert.assertTrue(testDisk1.equals(testDisk2)==false);
		Assert.assertTrue(testDisk1.equals(null)==false);
		Assert.assertTrue(testDisk1.equals(testDisk1)==true);
	}
	
	public void testCompareTo(){
		Assert.assertTrue(testDisk1.compareTo(testDisk2)==-1);
		Assert.assertTrue(testDisk1.compareTo(null)==-1);
		testDisk1.add(400000);
		Assert.assertTrue(testDisk1.compareTo(testDisk2)==400000);
	}
}
