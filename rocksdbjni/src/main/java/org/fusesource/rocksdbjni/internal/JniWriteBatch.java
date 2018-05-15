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

import org.fusesource.rocksdbjni.internal.NativeWriteBatch;
import org.iq80.leveldb.ColumnFamilyHandle;
import org.iq80.leveldb.WriteBatch;

/**
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public class JniWriteBatch implements WriteBatch {

    private final NativeWriteBatch writeBatch;

    JniWriteBatch(NativeWriteBatch writeBatch) {
        this.writeBatch = writeBatch;
    }
    

    public void close() {
        writeBatch.delete();
    }

    public WriteBatch put(byte[] key, byte[] value) {
        writeBatch.put(key, value);
        return this;
    }

    public WriteBatch delete(byte[] key) {
        writeBatch.delete(key);
        return this;
	}
    
	 public NativeWriteBatch writeBatch() {
        return writeBatch;
    }

	@Override
	public WriteBatch deleteRange(byte[] key, byte[] value) {
		 writeBatch.deleteRange(key, value);
	        return this;
	}

	@Override
	public WriteBatch deleteRange(ColumnFamilyHandle columnFamilyHandles, byte[] key, byte[] value) {
		writeBatch.deleteRange(convert(columnFamilyHandles),key, value);
        return this;
	}
	
        private NativeColumnFamilyHandle convert(ColumnFamilyHandle columnHandle) {
    	
    	if(columnHandle==null) {
    		return null;
    	}
    	NativeColumnFamilyHandle rce = new NativeColumnFamilyHandle(columnHandle);
		return rce;   
    }

		
    public WriteBatch put(ColumnFamilyHandle handle,byte[] key, byte[] value) {
        writeBatch.put(convert(handle),key, value);
        return this;
    }

    public WriteBatch delete(ColumnFamilyHandle handle,byte[] key) {
        writeBatch.delete(convert(handle),key);
        return this;
    }
    

	@Override
	public WriteBatch single_delete(ColumnFamilyHandle handle, byte[] key) {
		writeBatch.single_delete(convert(handle),key);
        return this;
	}
	
	@Override
	public WriteBatch single_delete(  byte[] key) {
		writeBatch.single_delete(key);
        return this;
	}
}
