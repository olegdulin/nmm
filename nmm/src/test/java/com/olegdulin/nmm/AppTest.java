package com.olegdulin.nmm;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    private NativeMemoryMap<String, String> nmm;

	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    @Override
	protected void setUp() throws Exception {
		super.setUp();
		this.nmm=new NativeMemoryMap<String, String>();
	}

    public void testPutAndGet()
    {
    	String key="put";
    	String value="put1";
    	this.nmm.put(key,  value);
    	String stored=this.nmm.get(key);
    	assertEquals(value, stored);
    }
    
    public void testPutAndOverwrite()
    {
    	String key="put";
    	String value="put2";
    	this.nmm.put(key,  value);
    	String stored=this.nmm.get(key);
    	assertEquals(value, stored);    	
    }
    
    public void testPutAndRemove()
    {
    	String key="put";
    	String value="put2";
    	this.nmm.put(key,  value);
    	this.nmm.remove(key);
    	String stored=this.nmm.get(key);
    	assertNull(stored);
    }
}
