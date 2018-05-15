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
 * Provides a java interface to the C++ rocksdb::ReadOptions class.
 *
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
@JniClass(name="rocksdb::ReadOptions", flags={STRUCT, CPP})
public class NativeReadOptions {

	@JniField
    private boolean verify_checksums = false;

	@JniField
    private boolean fill_cache = true;

	@JniField
    private boolean tailing = false;

	@JniField
    private boolean managed = false;

	@JniField
    private boolean total_order_seek = false;

	@JniField
    private boolean prefix_same_as_start = false;

	@JniField
    private boolean pin_data = false;

	@JniField
    private boolean background_purge_on_iterator_cleanup = false;


	@JniField
    private boolean ignore_range_deletions = false;
    
	@JniField(cast="const rocksdb::Snapshot*")
    private long snapshot=0;

	@JniField(cast="const rocksdb::Slice*")
    private long iterate_lower_bound=0;

	 @JniField(cast="const rocksdb::Slice*")
    private long iterate_upper_bound=0;

	 @JniField(cast="size_t")
    private long readahead_size=0;

	 @JniField(cast="uint64_t")
    private long max_skippable_internal_keys=0;

	 @JniField(cast="rocksdb::ReadTier")
	private long read_tier=0;





    public boolean fillCache() {
        return fill_cache;
    }

    public NativeReadOptions fillCache(boolean fill_cache) {
        this.fill_cache = fill_cache;
        return this;
    }
    
    public long readaheadsize() {
        return readahead_size;
    }

    public NativeReadOptions readaheadsize(long readahead_size) {
        this.readahead_size = readahead_size;
        return this;
    }
    
    public long maxskippableinternalkeys() {
        return max_skippable_internal_keys;
    }

    public NativeReadOptions maxskippableinternalkeys(long max_skippable_internal_keys) {
        this.max_skippable_internal_keys = max_skippable_internal_keys;
        return this;
    }

    public NativeSnapshot snapshot() {
        if( snapshot == 0 ) {
            return null;
        } else {
            return new NativeSnapshot(snapshot);
        }
    }

    public NativeReadOptions snapshot(NativeSnapshot snapshot) {
        if( snapshot==null ) {
            this.snapshot = 0;
        } else {
            this.snapshot = snapshot.pointer();
        }
        return this;
    }
    
    public NativeReadOptions read_tier(NativeReadTier read_tier) {
        if( read_tier==null ) {
            this.read_tier = 0;
        } else {
            this.read_tier = read_tier.pointer();
        }
        return this;
    }

   
    public NativeSlice iteratelowerbound() {
        if( iterate_lower_bound == 0 ) {
            return null;
        } else {
        	
            return new NativeSlice(iterate_lower_bound);
        }
    }

    public NativeReadOptions iteratelowerbound(NativeSlice lower_buffer) {
        if( lower_buffer==null ) {
            this.iterate_lower_bound = 0;
        } else {
            this.iterate_lower_bound = lower_buffer.pointer();
        }
        return this;
    }
    
    public NativeSlice iterateupperbound() {
        if( iterate_upper_bound == 0 ) {
            return null;
        } else {
            return new NativeSlice(iterate_upper_bound);
        }
    }

    public NativeReadOptions iterateupperbound(NativeSlice iterate_upper_bound) {
        if( iterate_upper_bound==null ) {
            this.iterate_upper_bound = 0;
        } else {
            this.iterate_upper_bound = iterate_upper_bound.pointer();
        }
        return this;
    }
    
    
    public boolean verifyChecksums() {
        return verify_checksums;
    }

    public NativeReadOptions verifyChecksums(boolean verify_checksums) {
        this.verify_checksums = verify_checksums;
        return this;
    }
   
    public boolean Tailing() {
        return tailing;
    }

    public NativeReadOptions Tailing(boolean tailing) {
        this.tailing = tailing;
        return this;
    }
    
    public boolean Managed() {
        return managed;
    }

    public NativeReadOptions Managed(boolean managed) {
        this.managed = managed;
        return this;
    }
    
    public boolean totalorderseek() {
        return total_order_seek;
    }

    public NativeReadOptions totalorderseek(boolean total_order_seek) {
        this.total_order_seek = total_order_seek;
        return this;
    }
    
    public boolean prefixsameasstart() {
        return prefix_same_as_start;
    }

    public NativeReadOptions prefixsameasstart(boolean prefix_same_as_start) {
        this.prefix_same_as_start = prefix_same_as_start;
        return this;
    }
    
    public boolean pindata() {
        return pin_data;
    }

    public NativeReadOptions pindata(boolean pin_data) {
        this.pin_data = pin_data;
        return this;
    }
    
    public boolean backgroundpurgeoniteratorcleanup() {
        return background_purge_on_iterator_cleanup;
    }

    public NativeReadOptions backgroundpurgeoniteratorcleanup(boolean background_purge_on_iterator_cleanup) {
        this.background_purge_on_iterator_cleanup = background_purge_on_iterator_cleanup;
        return this;
    }
    
    public boolean ignorerangedeletions() {
        return ignore_range_deletions;
    }

    public NativeReadOptions ignorerangedeletions(boolean ignore_range_deletions) {
        this.ignore_range_deletions = ignore_range_deletions;
        return this;
    }
}
