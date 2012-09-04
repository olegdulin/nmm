package com.olegdulin.nmm;

public class GCTest {

	/**
	 * @param args
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws InterruptedException {
		NativeMemoryMap nmm=new NativeMemoryMap<Integer, String>();
		int counter=0;
		while(true)
		{
			for (int i=0;i<10000;i++)
			{
				nmm.put(i,counter+"");
				counter++;
			}
			Thread.sleep(50);
//			System.gc();
		}

	}

}
