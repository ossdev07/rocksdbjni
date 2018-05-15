package org.iq80.leveldb;

import org.fusesource.rocksdbjni.internal.NativeComparator.ComparatorJNI;
import org.fusesource.rocksdbjni.internal.NativeDescriptor;

public interface ColumnFamilyHandle {
		
	public long  getId() ;
	
	
	public ComparatorJNI  getComparator() ;
	
	public long  getDescriptor(NativeDescriptor descriptor);
	
	public String get1Name();
	
}