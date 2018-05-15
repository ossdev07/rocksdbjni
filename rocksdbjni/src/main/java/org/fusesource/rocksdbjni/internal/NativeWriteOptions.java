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

import org.fusesource.hawtjni.runtime.JniClass;
import org.fusesource.hawtjni.runtime.JniField;

import static org.fusesource.hawtjni.runtime.ClassFlag.CPP;
import static org.fusesource.hawtjni.runtime.ClassFlag.STRUCT;

/**
 * Provides a java interface to the C++ rocksdb::WriteOptions class.
 *
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
@JniClass(name="rocksdb::WriteOptions", flags={STRUCT, CPP})
public class NativeWriteOptions {

    @JniField
    boolean sync=false;

@JniField
    boolean disableWAL = false;


@JniField
    boolean ignore_missing_column_families = false;


@JniField
    boolean no_slowdown = false;

@JniField
    boolean low_pri = false;





    public boolean sync() {
        return sync;
    }

    public NativeWriteOptions sync(boolean sync) {
        this.sync = sync;
        return this;
    }


    public boolean disableWAL() {
        return sync;
    }

    public NativeWriteOptions disableWAL(boolean disableWAL) {
        this.disableWAL = disableWAL;
        return this;
    }


    public boolean ignore_missing_column_families() {
        return ignore_missing_column_families;
    }

    public NativeWriteOptions ignore_missing_column_families(boolean ignore_missing_column_families) {
        this.ignore_missing_column_families = ignore_missing_column_families;
        return this;
    }


    public boolean no_slowdown() {
        return no_slowdown;
    }

    public NativeWriteOptions no_slowdown(boolean no_slowdown) {
        this.no_slowdown = no_slowdown;
        return this;
    }


    public boolean low_pri() {
        return low_pri;
    }

    public NativeWriteOptions low_pri(boolean low_pri) {
        this.low_pri = low_pri;
        return this;
    }


	 
}
