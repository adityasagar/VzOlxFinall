package test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.ProductUtility;
import com.ProductVO;

public class ProductUtilityTest {

	@Test
public void testGetProduct(){
	long value= 62L;
	ProductVO p= new ProductVO();
	try {
		p = ProductUtility.getProduct(value);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	assertEquals("Vehicles",p.getCategory());
	
}@Test
	public void testGetCategoryImage(){
		String imageLink=ProductUtility.getCategoryImage("Entertainment");
		assertEquals("img/Electronics.png", imageLink);
}
}
