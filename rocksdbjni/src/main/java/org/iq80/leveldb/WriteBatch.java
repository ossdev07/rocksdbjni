package org.iq80.leveldb;

import java.io.Closeable;

/**
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public interface WriteBatch
        extends Closeable
{
    WriteBatch put(byte[] key, byte[] value);

    WriteBatch delete(byte[] key);
    
    WriteBatch put(ColumnFamilyHandle handle,byte[] key, byte[] value);

    WriteBatch delete(ColumnFamilyHandle handle,byte[] key);
    
    WriteBatch single_delete(ColumnFamilyHandle handle,byte[] key);
    
    WriteBatch single_delete(byte[] key);
	
    WriteBatch deleteRange(byte[] key, byte[] value);

    WriteBatch deleteRange(ColumnFamilyHandle columnFamilyHandles, byte[] bytes, byte[] bytes2);

  
}

