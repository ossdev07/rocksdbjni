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

import org.fusesource.hawtjni.runtime.JniClass;
import org.fusesource.hawtjni.runtime.JniField;
import org.iq80.leveldb.CompactRangeOptions;


@JniClass(name="rocksdb::CompactRangeOptions", flags={STRUCT, CPP})
public class NativeCompactRangeOptions  {
	static {
		NativeDB.LIBRARY.load();
	}

	private boolean exclusive_manual_compaction = true;

	private boolean change_level = false;

	private int target_level = -1;

	@JniField(cast="uint32_t")
		private long target_path_id = 0;


	public NativeCompactRangeOptions(CompactRangeOptions options) {

	}
	public NativeCompactRangeOptions exclusiveManualCompaction(boolean value) {
		this.exclusive_manual_compaction = value;
		return this;
	}
	public boolean exclusiveManualCompaction() {
		return exclusive_manual_compaction;
	}

	public NativeCompactRangeOptions changeLevel(boolean value) {
		this.change_level = value;
		return this;
	}
	public boolean changeLevel() {
		return change_level;
	}

	public NativeCompactRangeOptions targetLevel(int value) {
		this.target_level = value;
		return this;
	}
	public int targetLevel() {
		return target_level;
	}


	public NativeCompactRangeOptions targetPathId(long value) {
		this.target_path_id = value;
		return this;
	}
	public long targetPathId() {
		return target_path_id;
	}

}
