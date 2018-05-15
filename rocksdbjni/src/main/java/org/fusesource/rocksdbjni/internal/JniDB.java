/*
 * Copyright (C) 2011, FuseSource Corp.  All rights reserved.
 *
 *     http://fusesource.com
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 * 
 *    * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *    * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *    * Neither the name of FuseSource Corp. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.fusesource.rocksdbjni.internal;

import java.io.File;
import java.io.IOException;

import org.iq80.leveldb.ColumnFamilyHandle;
import org.fusesource.rocksdbjni.internal.JniColumnFamilyHandle;
import org.fusesource.rocksdbjni.internal.JniColumnFamilyOptions;
import org.iq80.leveldb.*;

/**
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public class JniDB implements DB  {

    private NativeDB db;
    private NativeComparator comparator;
    private NativeLogger logger;

    public JniDB(NativeDB db, NativeComparator comparator, NativeLogger logger) {
        this.db = db;
        this.comparator = comparator;
        this.logger = logger;
    }

    public void close() {
        if( db!=null ) {
            db.delete();
            db = null;
            if(comparator!=null){
                comparator.delete();
                comparator = null;
            }
            if(logger!=null) {
                logger.delete();
                logger = null;
            }
        }
    }
    
    public boolean keyMayExist(byte[] key, ReadOptions options) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            return db.keyMayExist(convert(options), key);
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }
    
   


    public byte[] get(byte[] key) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        return get(key, new ReadOptions());
    }

    public byte[] get(byte[] key, ReadOptions options) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            return db.get(convert(options), key);
        } catch (NativeDB.DBException e) {
            if(e.isNotFound()) {
                return null;
            }
            throw new DBException(e.getMessage(), e);
        }
    }
    
    private NativeOptions convert(Options options) {
    	if(options==null) {
    		return null;
    	}
    	NativeOptions option = new NativeOptions();
    	option.createIfMissing(options.createIfMissing());
    	
		return option; 
	}
    
    
	public DBIterator iterator() {
        return iterator(new ReadOptions());
    }

    public DBIterator iterator(ReadOptions options) {
        if( db==null ) {
            throw new DBException("Closed");
        }
        return new JniDBIterator(db.iterator(convert(options)));
    }

    public void put(byte[] key, byte[] value) throws DBException {
        put(key, value, new WriteOptions());
    }
    
    @Override
    public void DefaultColumnFamily() throws IOException, org.fusesource.rocksdbjni.internal.NativeDB.DBException {
        db.DefaultColumnFamily();
    }
   

    public void delete(byte[] key) throws DBException {
        delete(key, new WriteOptions());
    }

    public void write(WriteBatch updates) throws DBException {
        write(updates, new WriteOptions());
    }

    public WriteBatch createWriteBatch() {
        return new JniWriteBatch(new NativeWriteBatch());
    }

    public Snapshot put(byte[] key, byte[] value, WriteOptions options) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.put(convert(options), key, value);
            return null;
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }


    public Snapshot put(NativeColumnFamilyHandle handle, byte[] key, byte[] value, WriteOptions options) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.put(convert(options),handle, key, value);
            return null;
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    
    public Snapshot delete(byte[] key, WriteOptions options) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.delete(convert(options), key);
            return null;
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }
    
    public Snapshot delete(byte[] key, WriteOptions options, ColumnFamilyHandle handle) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.delete(convert(options),convert(handle), key);
            return null;
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    public Snapshot write(WriteBatch updates, WriteOptions options) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.write(convert(options), ((JniWriteBatch) updates).writeBatch());
            return null;
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
    }

    public Snapshot getSnapshot() {
        if( db==null ) {
            throw new DBException("Closed");
        }
        return new JniSnapshot(db, db.getSnapshot());
    }

    public long[] getApproximateSizes(Range... ranges) {
        if( db==null ) {
            throw new DBException("Closed");
        }
        NativeRange args[] = new NativeRange[ranges.length];
        for (int i = 0; i < args.length; i++) {
            args[i] = new NativeRange(ranges[i].start(), ranges[i].limit());
        }
        return db.getApproximateSizes(args);
    }

    public String getProperty(String name) {
        if( db==null ) {
            throw new DBException("Closed");
        }
        return db.getProperty(name);
    }

    private NativeReadOptions convert(ReadOptions options) {
        if(options==null) {
            return null;
        }
        NativeReadOptions rc = new NativeReadOptions();
        rc.fillCache(options.fillCache());
        rc.verifyChecksums(options.verifyChecksums());
        if(options.snapshot()!=null) {
            rc.snapshot(((JniSnapshot) options.snapshot()).snapshot());
        }
        return rc;
    }

    private NativeWriteOptions convert(WriteOptions options) {
        if(options==null) {
            return null;
        }
        NativeWriteOptions rc = new NativeWriteOptions();
        rc.sync(options.sync());
        if(options.snapshot()) {
            throw new UnsupportedOperationException("WriteOptions snapshot not supported");
        }
        return rc;
    }
    
     private NativeColumnFamilyHandle convert(ColumnFamilyHandle columnHandle) {
    	
    	if(columnHandle==null) {
    		return null;
    	}
    	NativeColumnFamilyHandle rce = new NativeColumnFamilyHandle(columnHandle);
		return rce;   
    }

    
    
    public ColumnFamilyHandle createColumnFamily(JniColumnFamilyOptions columnOptions, File path) throws IOException {
    	NativeColumnFamilyHandle handle = new NativeColumnFamilyHandle();
    	handle = db.createColumnFamily(convert(columnOptions), path);	
    	return new JniColumnFamilyHandle(handle);
    }
    
    private NativeColumnFamilyOptions convert(JniColumnFamilyOptions columnFamily) {
    	if(columnFamily==null) {
    		return null;
    	}
    	NativeColumnFamilyOptions rce= new NativeColumnFamilyOptions();
    	rce.write_buffer_size(columnFamily.writeBufferSize());
		return rce;
    	
    }
    
    public void put( ColumnFamilyHandle handle, byte[] key, byte[] value) throws DBException{
    	put(convert(handle), key, value,new WriteOptions());
    }


    public void dropColumnFamily(ColumnFamilyHandle columnHandle) throws IOException {
		  db.dropColumnFamily(convert(columnHandle));
    }
   
    public void destroydb(File path, Options options) {
		 try {
				NativeDB.destroy(path,convert(options));
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		
	}
    
    public void destroyColumnFamily(ColumnFamilyHandle columnHandle) {
		  try {
			db.destroyColumnFamilyHandle(convert(columnHandle));
		} catch (org.fusesource.rocksdbjni.internal.NativeDB.DBException e) {
			e.printStackTrace();
		}
		
	}
    
    public void compactRange(CompactRangeOptions compactRangeOptions,byte[] begin, byte[] end) throws DBException {
        if( db==null ) {
            throw new DBException("Closed");
        }
        db.compactRange(convert(compactRangeOptions),begin, end);
    }

    private NativeCompactRangeOptions convert(CompactRangeOptions options) {
        if(options==null) {
            return null;
        }
        NativeCompactRangeOptions rc = new NativeCompactRangeOptions(options);
        return rc;
    }
    
    public void suspendCompactions() throws InterruptedException {
        throw new UnsupportedOperationException();
    }

    public void resumeCompactions() {
        throw new UnsupportedOperationException();
    }




	@Override
	public void deleteRange(ColumnFamilyHandle handle, byte[] begin, byte[] end) throws DBException {
			deleteRange(handle, begin, end, new WriteOptions());
			
	}

	private void deleteRange(ColumnFamilyHandle handle, byte[] begin, byte[] end, WriteOptions writeOptions) {
        if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.deleteRange(convert(writeOptions),convert(handle), begin, end);
            
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
		
	}

	@Override
	public void singleDelete(byte[] key) throws DBException {
		singleDelete(key, new WriteOptions());
	}

	private void singleDelete(byte[] key, WriteOptions writeOptions) {
		if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.singleDelete(convert(writeOptions), key);
            
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
		
	}

	public void singleDelete(ColumnFamilyHandle columnFamilyHandles, byte[] key) {
		singleDelete(key,columnFamilyHandles, new WriteOptions());
		
	}
	
	private void singleDelete(byte[] key,ColumnFamilyHandle columnFamilyHandles, WriteOptions writeOptions) {
		if( db==null ) {
            throw new DBException("Closed");
        }
        try {
            db.singleDelete(convert(writeOptions),convert(columnFamilyHandles), key);
            
        } catch (NativeDB.DBException e) {
            throw new DBException(e.getMessage(), e);
        }
		
	}

	@Override
    public boolean getIntProperty(String key) throws DBException {
	  if( db==null ) {
      throw new DBException("Closed");
    }
    try {
     return db.getIntProperty(key);
      
     } catch (NativeDB.DBException e) {
      throw new DBException(e.getMessage(), e);
    }
	
  }
	
	@Override
	public int numberLevels() throws DBException {
		return db.numberLevels();
		
	}
}
