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
import org.fusesource.hawtjni.runtime.JniMethod;

import static org.fusesource.hawtjni.runtime.ArgFlag.POINTER_ARG;
import static org.fusesource.hawtjni.runtime.ClassFlag.CPP;
import static org.fusesource.hawtjni.runtime.ClassFlag.STRUCT;
import static org.fusesource.hawtjni.runtime.FieldFlag.CONSTANT;
import static org.fusesource.hawtjni.runtime.FieldFlag.FIELD_SKIP;
import static org.fusesource.hawtjni.runtime.FieldFlag.POINTER_FIELD;
import static org.fusesource.hawtjni.runtime.MethodFlag.POINTER_RETURN;
import static org.fusesource.hawtjni.runtime.MethodFlag.CONSTANT_INITIALIZER;


/**
 * Provides a java interface to the C++ rocksdb::Options class.
 *
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
@JniClass(name="rocksdb::Options", flags={STRUCT, CPP})
public class NativeOptions {
	
	    static {
        NativeDB.LIBRARY.load();
        init();
    }

    
	@JniMethod(flags={CONSTANT_INITIALIZER})
    private static final native void init();

    @JniField(flags={CONSTANT}, cast="Env*", accessor="rocksdb::Env::Default()")
    private static long DEFAULT_ENV;
    
    @JniField(flags={FIELD_SKIP})
    private NativeComparator comparatorObject = NativeComparator.BYTEWISE_COMPARATOR;
    
    @JniField(cast="const rocksdb::Comparator*")
    private long comparator = comparatorObject.pointer();
    
    private boolean create_if_missing = false;
    private boolean error_if_exists = false;
    private boolean paranoid_checks = true;
    private boolean create_missing_column_families = false;
    private int num_levels = 2;
    
    public NativeOptions createmissingcolumnfamilies(boolean value) {
        this.create_missing_column_families = value;
        return this;
    }
    public boolean createmissingcolumnfamilies() {
        return create_missing_column_families;
    }
    
    private boolean use_fsync = false;
    
    public NativeOptions usefsync(boolean value) {
        this.use_fsync = value;
        return this;
    }
    public boolean usefsync() {
        return use_fsync;
    }
    
    private boolean allow_mmap_reads = false;
    
    public NativeOptions allowmmapreads(boolean value) {
        this.allow_mmap_reads = value;
        return this;
    }
    public boolean allowmmapreads() {
        return allow_mmap_reads;
    }
    
    private boolean allow_mmap_writes = false;
    
    public NativeOptions allowmmapwrites(boolean value) {
        this.allow_mmap_writes = value;
        return this;
    }
    public boolean allowmmapwrites() {
        return allow_mmap_writes;
    }
    private boolean new_table_reader_for_compaction_inputs = false;
    
    public NativeOptions newtablereaderforcompactioninputs(boolean value) {
        this.new_table_reader_for_compaction_inputs = value;
        return this;
    }
    public boolean newtablereaderforcompactioninputs() {
        return new_table_reader_for_compaction_inputs;
    }
    
    @JniField(cast="size_t")
    private long write_buffer_size = 4 << 20;
    
    private int max_open_files = 1000;
    
    @JniField(cast="uint64_t")
    private long max_total_wal_size =0;
    
    @JniField(cast="uint64_t")
    private long delete_obsolete_files_period_micros = 60 * 60 * 1000000;

    

    @JniField(flags={FIELD_SKIP})
    private NativeLogger infoLogObject = null;

    @JniField(cast="rocksdb::Env*")
    private long env = DEFAULT_ENV;

    @JniField(cast="rocksdb::CompressionType")
    private int compression = NativeCompressionType.kSnappyCompression.value;
    
    @JniField
    private int max_background_jobs = 2;
      
    public NativeOptions maxbackgroundjobs(int  max_background_jobs) {
        this.max_background_jobs = max_background_jobs;
        return this;
    }
    
    @JniField
    private int base_background_compactions = -1;
    
    public NativeOptions basebackgroundcompactions(int  base_background_compactions) {
        this.base_background_compactions = base_background_compactions;
        return this;
    }

    @JniField
    private int max_background_compactions =-1;
    
    public NativeOptions maxbackgroundcompactions(int  max_background_compactions) {
        this.max_background_compactions = max_background_compactions;
        return this;
    }

    @JniField(cast="uint32_t")
    private long max_subcompactions = 1;
    
    public NativeOptions maxsubcompactions(long value) {
        this.max_subcompactions = value;
        return this;
    }
    public long maxsubcompactions() {
        return max_subcompactions;
    }

    @JniField
    private int max_background_flushes = -1;
    
    public NativeOptions maxbackgroundflushes(int  max_background_flushes) {
        this.max_background_flushes = max_background_flushes;
        return this;
    }

    @JniField(cast="size_t")
    private long max_log_file_size =0;
    
    public NativeOptions maxlogfilesize(long value) {
        this.max_log_file_size = value;
        return this;
    }
    public long maxlogfilesize() {
        return max_log_file_size;
    }
    
    @JniField(cast="size_t")
    private long log_file_time_to_roll =0;
    
    public NativeOptions logfiletimetoroll(long value) {
        this.log_file_time_to_roll = value;
        return this;
    }
    public long logfiletimetoroll() {
        return log_file_time_to_roll;
    }
    
    @JniField(cast="size_t")
    private long keep_log_file_num =1000;
    
    public NativeOptions keeplogfilenum(long value) {
        this.keep_log_file_num = value;
        return this;
    }
    public long keeplogfilenum() {
        return keep_log_file_num;
    }
    
    @JniField(cast="size_t")
    private long db_write_buffer_size =0;
    
    public NativeOptions dbwritebuffersize(long value) {
        this.db_write_buffer_size = value;
        return this;
    }
    public long dbwritebuffersize() {
        return db_write_buffer_size;
    }
    
    @JniField(cast="size_t")
    private long compaction_readahead_size =0;
    
    public NativeOptions compactionreadaheadsize(long value) {
        this.compaction_readahead_size = value;
        return this;
    }
    public long compactionreadaheadsize() {
        return compaction_readahead_size;
    }
    
    @JniField(cast="size_t")
    private long random_access_max_buffer_size =1024 * 1024;
    
    public NativeOptions randomaccessmaxbuffersize(long value) {
        this.random_access_max_buffer_size = value;
        return this;
    }
    public long randomaccessmaxbuffersize() {
        return random_access_max_buffer_size;
    }
    
    @JniField(cast="size_t")
    private long writable_file_max_buffer_size =1024 * 1024;
    
    public NativeOptions writablefilemaxbuffersize(long value) {
        this.writable_file_max_buffer_size = value;
        return this;
    }
    public long writablefilemaxbuffersize() {
        return writable_file_max_buffer_size;
    }
    
    public NativeOptions deleteobsoletefilesperiodmicros(long value) {
        this.delete_obsolete_files_period_micros = value;
        return this;
    }
    public long deleteobsoletefilesperiodmicros() {
        return delete_obsolete_files_period_micros;
    }
    
    public NativeOptions maxtotalwalsize(long value) {
        this.max_total_wal_size = value;
        return this;
    }
    public long maxtotalwalsize() {
        return max_total_wal_size;
    }

    public NativeOptions createIfMissing(boolean value) {
        this.create_if_missing = value;
        return this;
    }
    public boolean createIfMissing() {
        return create_if_missing;
    }

    public NativeOptions errorIfExists(boolean value) {
        this.error_if_exists = value;
        return this;
    }
    public boolean errorIfExists() {
        return error_if_exists;
    }

    public NativeOptions paranoidChecks(boolean value) {
        this.paranoid_checks = value;
        return this;
    }
    public boolean paranoidChecks() {
        return paranoid_checks;
    }

    public NativeOptions writeBufferSize(long value) {
        this.write_buffer_size = value;
        return this;
    }
    public long writeBufferSize() {
        return write_buffer_size;
    }

    public NativeOptions maxOpenFiles(int value) {
        this.max_open_files = value;
        return this;
    }
    public int maxOpenFiles() {
        return max_open_files;
    }


    public NativeComparator comparator() {
        return comparatorObject;
    }

    public NativeOptions comparator(NativeComparator comparator) {
        if( comparator==null ) {
            throw new IllegalArgumentException("comparator cannot be null");
        }
        this.comparatorObject = comparator;
        this.comparator = comparator.pointer();
        return this;
    }

    
    public NativeLogger infoLog() {
        return infoLogObject;
      }

    public NativeOptions infoLog(NativeLogger logger) {
    this.infoLogObject = logger;
    return this;
    }
      
    public NativeCompressionType compression() {
        if(compression == NativeCompressionType.kNoCompression.value) {
            return NativeCompressionType.kNoCompression;
        } else if(compression == NativeCompressionType.kSnappyCompression.value) {
            return NativeCompressionType.kSnappyCompression;
        } else {
            return NativeCompressionType.kSnappyCompression;
        }
    }

    public NativeOptions compression(NativeCompressionType compression) {
        this.compression = compression.value;
        return this;
    }
    
    public NativeOptions numLevels(int value) {
        this.num_levels = value;
        return this;
      }
      public int numLevels() {
        return num_levels;
    }

}
