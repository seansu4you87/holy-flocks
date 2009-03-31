import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

import junit.framework.Assert;
import junit.framework.TestCase;


public class BinsTest extends TestCase {
	private Bins exampleBin;
	public void setUp(){
		exampleBin = new Bins("data/example.txt"); 
	}
	
	public void testGetDataSize(){
		Assert.assertTrue(exampleBin.getDataSize()==1950000);
	}
	
	public void testworstFitMethod(){
		Assert.assertTrue(exampleBin.worstFitMethod("worst-fit method")==3);
	}
	
	public void testworstFitDecreasingMethod(){
		Assert.assertTrue(exampleBin.worstFitDecreasingMethod()==2);
	}
	
	public void testworstFitIncreasingMethod(){
		Assert.assertTrue(exampleBin.worstFitIncreasingMethod()==3);
	}
	
	public void testworstFitRandomMethod(){
		Assert.assertTrue(exampleBin.worstFitRandomMethod()==2);
	}
	
	public void testweipingMethod(){
		Assert.assertTrue(exampleBin.weipingMethod()==2);
	}
}