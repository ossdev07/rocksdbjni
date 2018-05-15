/*
 * Copyright (C) 2018, FuseSource Corp.  All rights reserved.
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

import org.fusesource.hawtjni.runtime.JniClass;
import org.fusesource.hawtjni.runtime.JniMethod;
import org.fusesource.rocksdbjni.internal.NativeComparator.ComparatorJNI;
import org.iq80.leveldb.ColumnFamilyHandle;

import static org.fusesource.hawtjni.runtime.ClassFlag.CPP;
import static org.fusesource.hawtjni.runtime.MethodFlag.CPP_METHOD;

import org.fusesource.hawtjni.runtime.JniArg;

public class NativeColumnFamilyHandle extends NativeObject{
	
  
	@JniClass(name="rocksdb::ColumnFamilyHandle", flags={CPP})
    private static class ColumnFamilyHandleJNI {
        static {
            NativeDB.LIBRARY.load();
        }
	
        @JniMethod(cast="rocksdb::Comparator *", flags={CPP_METHOD})
        static final native long GetComparator(
                long self);
		
        @JniMethod(copy="rocksdb::Status", flags={CPP_METHOD})
        static final native long GetDescriptor(
                long self,
                @JniArg(cast="rocksdb::ColumnFamilyDescriptor *") long desc
                );
        
	@JniMethod(flags={CPP_METHOD},copy="std::string")
    public static final native String GetName (long self);
	
	@JniMethod(flags={CPP_METHOD}, cast="uint32_t")
    public static final native long GetID (long self);
	
	
	}
	NativeColumnFamilyHandle(long self) {
		super(self);
	}

	public NativeColumnFamilyHandle() {
		super();
	}

	public NativeColumnFamilyHandle(ColumnFamilyHandle columnHandle) {
		
	}

	public long getID() {
		assertAllocated();
		return ColumnFamilyHandleJNI.GetID(self);
		
    }
	
	public String getName() {
     return ColumnFamilyHandleJNI.GetName(self);   
    }

	
	public ComparatorJNI getComparator() {
        return new NativeComparator.ComparatorJNI(ColumnFamilyHandleJNI.GetComparator(self));
    }
	
	public long getDescriptor(NativeDescriptor descriptor) {
        NativeDB.checkArgNotNull(descriptor, "descriptor");
         long s = ColumnFamilyHandleJNI.GetDescriptor(self, descriptor.pointer());
         return s;
       
	}
}
