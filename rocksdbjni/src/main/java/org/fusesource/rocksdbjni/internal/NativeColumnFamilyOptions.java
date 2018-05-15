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

import static org.fusesource.hawtjni.runtime.ClassFlag.CPP;
import static org.fusesource.hawtjni.runtime.ClassFlag.STRUCT;
import static org.fusesource.hawtjni.runtime.MethodFlag.*;

import org.fusesource.hawtjni.runtime.JniArg;
import org.fusesource.hawtjni.runtime.JniClass;
import org.fusesource.hawtjni.runtime.JniField;
import org.fusesource.hawtjni.runtime.JniMethod;


@JniClass(name="rocksdb::ColumnFamilyOptions", flags={STRUCT, CPP})
public class NativeColumnFamilyOptions {

	static {
		NativeDB.LIBRARY.load();

	}

	@JniField(cast="size_t")
		private long write_buffer_size = 4096 ;

	public NativeColumnFamilyOptions write_buffer_size(long value) {
		this.write_buffer_size = value;
		return this;
	}

	public long write_buffer_size() {
		return write_buffer_size;
	}


	private int level0_file_num_compaction_trigger=4;

	@JniField(cast="uint64_t")
		private long max_bytes_for_level_base = 268435456;

	private boolean disable_auto_compactions = false;

	public NativeColumnFamilyOptions level0_file_num_compaction_trigger(int value) {
		this.level0_file_num_compaction_trigger = value;
		return this;
	}

	public int level0_file_num_compaction_trigger() {
		return level0_file_num_compaction_trigger;	
	}	

	public NativeColumnFamilyOptions disable_auto_compactions(boolean value) {
		this.disable_auto_compactions = value;
		return this;
	}

	public boolean disable_auto_compactions() {
		return disable_auto_compactions;	
	}	

	public NativeColumnFamilyOptions max_bytes_for_level_base(long value) {
		this.max_bytes_for_level_base = value;
		return this;
	}

	public long max_bytes_for_level_base() {
		return max_bytes_for_level_base;
	}
}


