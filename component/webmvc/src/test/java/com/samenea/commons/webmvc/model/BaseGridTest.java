package com.samenea.commons.webmvc.model;

import static org.junit.Assert.*;

import org.aspectj.lang.annotation.Before;
import org.junit.Test;

import com.ibm.icu.impl.Assert;

public class BaseGridTest {
	BaseGrid baseGrid;
	

	
	@Test
	public void should_return_totalPages(){
		baseGrid=new BaseGrid();
		baseGrid.setTotalRecords(55, 10);
		
		junit.framework.Assert.assertEquals(6, baseGrid.getTotalPages());
		
	}

}
