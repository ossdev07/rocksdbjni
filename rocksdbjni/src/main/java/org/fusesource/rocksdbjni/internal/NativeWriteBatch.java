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

import org.fusesource.hawtjni.runtime.JniArg;
import org.fusesource.hawtjni.runtime.JniClass;
import org.fusesource.hawtjni.runtime.JniMethod;

import static org.fusesource.hawtjni.runtime.ArgFlag.BY_VALUE;
import static org.fusesource.hawtjni.runtime.ArgFlag.NO_OUT;
import static org.fusesource.hawtjni.runtime.ClassFlag.CPP;
import static org.fusesource.hawtjni.runtime.MethodFlag.*;
import static org.fusesource.hawtjni.runtime.ArgFlag.CRITICAL;

/**
 * Provides a java interface to the C++ rocksdb::WriteBatch class.
 *
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public class NativeWriteBatch extends NativeObject {

    @JniClass(name="rocksdb::WriteBatch", flags={CPP})
    private static class WriteBatchJNI {
        static {
            NativeDB.LIBRARY.load();
        }

        @JniMethod(flags={CPP_NEW})
        public static final native long create();
        @JniMethod(flags={CPP_DELETE})
        public static final native void delete(
                long self);

        @JniMethod(flags={CPP_METHOD})
        static final native void Put(
                long self,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice value
                );
        
        @JniMethod(flags={CPP_METHOD})
        static final native void Put(
                long self,
                @JniArg(cast="rocksdb::ColumnFamilyHandle *", flags={NO_OUT, CRITICAL}) long column_family,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice value
                );

        @JniMethod(flags={CPP_METHOD})
        static final native void Delete(
                long self,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key
                );
        
        @JniMethod(flags={CPP_METHOD})
        static final native void Delete(
                long self,
                @JniArg(cast="rocksdb::ColumnFamilyHandle *", flags={NO_OUT, CRITICAL}) long column_family,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key
                );
        
        @JniMethod(flags={CPP_METHOD})
        static final native void SingleDelete(
                long self,
                @JniArg(cast="rocksdb::ColumnFamilyHandle *", flags={NO_OUT, CRITICAL}) long column_family,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key
                );
        
        @JniMethod(flags={CPP_METHOD})
        static final native void SingleDelete(
                long self,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key
                );
				
		 @JniMethod(flags={CPP_METHOD})
        static final native void DeleteRange(
                long self,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice value
                );
        
        
        @JniMethod(flags={CPP_METHOD})
        static final native void DeleteRange(
                long self,
                @JniArg(cast="rocksdb::ColumnFamilyHandle *") long handle,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice key,
                @JniArg(flags={BY_VALUE, NO_OUT}) NativeSlice value
                );
        
        @JniMethod(flags={CPP_METHOD})
        static final native void Clear(
                long self
                );

    }

    public NativeWriteBatch() {
        super(WriteBatchJNI.create());
    }

    public void delete() {
        assertAllocated();
        WriteBatchJNI.delete(self);
        self = 0;
    }

    public void put(byte[] key, byte[] value) {
        NativeDB.checkArgNotNull(key, "key");
        NativeDB.checkArgNotNull(value, "value");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
            NativeBuffer valueBuffer = NativeBuffer.create(value);
            try {
                put(keyBuffer, valueBuffer);
            } finally {
                valueBuffer.delete();
            }
        } finally {
            keyBuffer.delete();
        }
    }
    
    private void put(NativeBuffer keyBuffer, NativeBuffer valueBuffer) {
        put(new NativeSlice(keyBuffer), new NativeSlice(valueBuffer));
    }

    private void put(NativeSlice keySlice, NativeSlice valueSlice) {
        assertAllocated();
        WriteBatchJNI.Put(self, keySlice, valueSlice);
    }

    
    public void put(NativeColumnFamilyHandle handle,byte[] key, byte[] value) {
        NativeDB.checkArgNotNull(key, "key");
        NativeDB.checkArgNotNull(value, "value");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
            NativeBuffer valueBuffer = NativeBuffer.create(value);
            try {
                put(handle,keyBuffer, valueBuffer);
            } finally {
                valueBuffer.delete();
            }
        } finally {
            keyBuffer.delete();
        }
    }
    
    
    private void put(NativeColumnFamilyHandle handle,NativeBuffer keyBuffer, NativeBuffer valueBuffer) {
        put(handle,new NativeSlice(keyBuffer), new NativeSlice(valueBuffer));
    }
    
    private void put(NativeColumnFamilyHandle handle,NativeSlice keySlice, NativeSlice valueSlice) {
        assertAllocated();
        WriteBatchJNI.Put(self,handle.pointer(), keySlice, valueSlice);
    }
    
  

    public void delete(NativeColumnFamilyHandle handle,byte[] key) {
        NativeDB.checkArgNotNull(key, "key");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
            delete(handle,keyBuffer);
        } finally {
            keyBuffer.delete();
        }
    }

    private void delete(NativeColumnFamilyHandle handle,NativeBuffer keyBuffer) {
        delete(handle,new NativeSlice(keyBuffer));
    }

    private void delete(NativeColumnFamilyHandle handle,NativeSlice keySlice) {
        assertAllocated();
        WriteBatchJNI.Delete(self,handle.pointer(), keySlice);
    }
    
    public void single_delete(NativeColumnFamilyHandle handle,byte[] key) {
        NativeDB.checkArgNotNull(key, "key");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
        	single_delete(handle,keyBuffer);
        } finally {
            keyBuffer.delete();
        }
    }

    private void single_delete(NativeColumnFamilyHandle handle,NativeBuffer keyBuffer) {
    	single_delete(handle,new NativeSlice(keyBuffer));
    }

    private void single_delete(NativeColumnFamilyHandle handle,NativeSlice keySlice) {
        assertAllocated();
        WriteBatchJNI.SingleDelete(self,handle.pointer(), keySlice);
    }
    
    public void single_delete( byte[] key) {
        NativeDB.checkArgNotNull(key, "key");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
        	single_delete(keyBuffer);
        } finally {
            keyBuffer.delete();
        }
    }

    private void single_delete(NativeBuffer keyBuffer) {
    	single_delete(new NativeSlice(keyBuffer));
    }

    private void single_delete( NativeSlice keySlice) {
        assertAllocated();
        WriteBatchJNI.SingleDelete(self, keySlice);
    }

   
    public void deleteRange(byte[] key, byte[] value) {
        NativeDB.checkArgNotNull(key, "key");
        NativeDB.checkArgNotNull(value, "value");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
            NativeBuffer valueBuffer = NativeBuffer.create(value);
            try {
            	deleteRange(keyBuffer, valueBuffer);
            } finally {
                valueBuffer.delete();
            }
        } finally {
            keyBuffer.delete();
        }
    }

    private void deleteRange(NativeBuffer keyBuffer, NativeBuffer valueBuffer) {
    	deleteRange(new NativeSlice(keyBuffer), new NativeSlice(valueBuffer));
    }

    private void deleteRange(NativeSlice keySlice, NativeSlice valueSlice) {
        assertAllocated();
        WriteBatchJNI.DeleteRange(self, keySlice, valueSlice);
    }

   public void deleteRange(NativeColumnFamilyHandle handle,byte[] key, byte[] value) {
        NativeDB.checkArgNotNull(key, "key");
        NativeDB.checkArgNotNull(value, "value");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
            NativeBuffer valueBuffer = NativeBuffer.create(value);
            try {
            	deleteRange(handle,keyBuffer, valueBuffer);
            } finally {
                valueBuffer.delete();
            }
        } finally {
            keyBuffer.delete();
        }
    }

    private void deleteRange(NativeColumnFamilyHandle handle,NativeBuffer keyBuffer, NativeBuffer valueBuffer) {
    	deleteRange(handle,new NativeSlice(keyBuffer), new NativeSlice(valueBuffer));
    }

    private void deleteRange(NativeColumnFamilyHandle handle,NativeSlice keySlice, NativeSlice valueSlice) {
        assertAllocated();
        WriteBatchJNI.DeleteRange(self,handle.pointer(), keySlice, valueSlice);
    }


    public void delete(byte[] key) {
        NativeDB.checkArgNotNull(key, "key");
        NativeBuffer keyBuffer = NativeBuffer.create(key);
        try {
            delete(keyBuffer);
        } finally {
            keyBuffer.delete();
        }
    }

    private void delete(NativeBuffer keyBuffer) {
        delete(new NativeSlice(keyBuffer));
    }

    private void delete(NativeSlice keySlice) {
        assertAllocated();
        WriteBatchJNI.Delete(self, keySlice);
    }

    
    
    public void clear() {
        assertAllocated();
        WriteBatchJNI.Clear(self);
    }

}
