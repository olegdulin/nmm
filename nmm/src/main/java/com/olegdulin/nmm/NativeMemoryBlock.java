package com.olegdulin.nmm;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class NativeMemoryBlock<V extends Serializable> {
	private static final Unsafe unsafe;

	static {
		try {
			Field field = sun.misc.Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (sun.misc.Unsafe) field.get(null);
		} catch (Exception e) {
			throw new AssertionError(e);
		}
	}

	@SuppressWarnings("restriction")
	@Override
	protected void finalize() throws Throwable {
		System.out.println("f");
		unsafe.freeMemory(pointer);
	}

	protected long pointer;
	private int length;

	public NativeMemoryBlock(V object) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream out = new ObjectOutputStream(bos);
		out.writeObject(object);
		out.close();
		byte[] buf = bos.toByteArray();
		this.pointer = unsafe.allocateMemory(buf.length);
		for (int i=0;i<buf.length;i++)
		{
			unsafe.putByte(this.pointer+i, buf[i]);
		}
		this.length=buf.length;
	}
	
	public V get() throws IOException, ClassNotFoundException
	{
		byte[] buffer=new byte[this.length];
		for (int i=0;i<length;i++)
		{
			buffer[i]=unsafe.getByte(pointer+i);
		}
		ByteArrayInputStream bis=new ByteArrayInputStream(buffer);
		ObjectInputStream in=new ObjectInputStream(bis);
		return (V) in.readObject();
	}
	
	

}
