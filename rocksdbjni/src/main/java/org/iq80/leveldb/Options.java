package org.iq80.leveldb;

public class Options
{
    private boolean createIfMissing = true;
    private boolean errorIfExists;
    private int writeBufferSize = 4 << 20;

    private int maxOpenFiles = 1000;

    private CompressionType compressionType = CompressionType.SNAPPY;
    private boolean verifyChecksums = true;
    private boolean paranoidChecks=true;
    private DBComparator comparator;
    private Logger logger;
    private long cacheSize;
    //====================================
    
    
    
    private boolean use_fsync = false;
    private boolean allow_mmap_reads = false;
    private boolean allow_mmap_writes = false;
    private boolean new_table_reader_for_compaction_inputs = false;
    private long max_total_wal_size =0;
    private long delete_obsolete_files_period_micros = 60 * 60 * 1000000;
    private int max_background_jobs = 2;
    private int base_background_compactions = -1;
    private int max_background_compactions =-1;
    private long max_subcompactions = 1;
    private int max_background_flushes = -1;
    private long max_log_file_size =0;
    private long log_file_time_to_roll =0;
    private long keep_log_file_num =1000;
    private long db_write_buffer_size =0;
    private long compaction_readahead_size =0;
    private long random_access_max_buffer_size =1024 * 1024;
    private long writable_file_max_buffer_size =1024 * 1024;
    //====================================
    static void checkArgNotNull(Object value, String name)
    {
        if (value == null) {
            throw new IllegalArgumentException("The " + name + " argument cannot be null");
        }
    }
   
    public Options usefsync(boolean value) {
        this.use_fsync = value;
        return this;
    }
    public boolean usefsync() {
        return use_fsync;
    }
    
    public Options allowmmapreads(boolean value) {
        this.allow_mmap_reads = value;
        return this;
    }
    public boolean allowmmapreads() {
        return allow_mmap_reads;
    }
    
    public Options allowmmapwrites(boolean value) {
        this.allow_mmap_writes = value;
        return this;
    }
    public boolean allowmmapwrites() {
        return allow_mmap_writes;
    }
    
    public Options newtablereaderforcompactioninputs(boolean value) {
        this.new_table_reader_for_compaction_inputs = value;
        return this;
    }
    public boolean newtablereaderforcompactioninputs() {
        return new_table_reader_for_compaction_inputs;
    }
    public int maxbackgroundjobs()
    {
    	return max_background_jobs;
    }
    
    public Options maxbackgroundjobs(int  max_background_jobs) {
        this.max_background_jobs = max_background_jobs;
        return this;
    }
    
    public int basebackgroundcompactions()
    {
    	return base_background_compactions;
    }
    
        public Options basebackgroundcompactions(int  base_background_compactions) {
        this.base_background_compactions = base_background_compactions;
        return this;
    }
        
        public int maxbackgroundcompactions()
        {
        	return max_background_compactions;
        }
    
    public Options maxbackgroundcompactions(int  max_background_compactions) {
        this.max_background_compactions = max_background_compactions;
        return this;
    }
    
    public Options maxsubcompactions(long value) {
        this.max_subcompactions = value;
        return this;
    }
    public int maxbackgroundflushes()
    {
    	return max_background_flushes;
    }
    
    public Options maxbackgroundflushes(int  max_background_flushes) {
        this.max_background_flushes = max_background_flushes;
        return this;
    }
    
    public Options maxlogfilesize(long value) {
        this.max_log_file_size = value;
        return this;
    }
    
    public long maxlogfilesize() {
        return max_log_file_size;
    }
    
    public Options logfiletimetoroll(long value) {
        this.log_file_time_to_roll = value;
        return this;
    }
    
    
    public Options keeplogfilenum(long value) {
        this.keep_log_file_num = value;
        return this;
    }
    
    public Options dbwritebuffersize(long value) {
        this.db_write_buffer_size = value;
        return this;
    }
    public long dbwritebuffersize() {
        return db_write_buffer_size;
    }
    
    public Options compactionreadaheadsize(long value) {
        this.compaction_readahead_size = value;
        return this;
    }
    
    
    public Options randomaccessmaxbuffersize(long value) {
        this.random_access_max_buffer_size = value;
        return this;
    }
    
    public Options writablefilemaxbuffersize(long value) {
        this.writable_file_max_buffer_size = value;
        return this;
    }
    
    public Options deleteobsoletefilesperiodmicros(long value) {
        this.delete_obsolete_files_period_micros = value;
        return this;
    }
    
    public Options maxtotalwalsize(long value) {
        this.max_total_wal_size = value;
        return this;
    }
    
    public long maxtotalwalsize() {
        return max_total_wal_size;
    }
    
    public long deleteobsoletefilesperiodmicros() {
        return delete_obsolete_files_period_micros;
    }
    
    public long writablefilemaxbuffersize() {
        return writable_file_max_buffer_size;
    }
    
    public long randomaccessmaxbuffersize() {
        return random_access_max_buffer_size;
    }
    
    public long compactionreadaheadsize() {
        return compaction_readahead_size;
    }
    
    public long keeplogfilenum() {
        return keep_log_file_num;
    }
    
    
    public long logfiletimetoroll() {
        return log_file_time_to_roll;
    }
    
    public long maxsubcompactions() {
        return max_subcompactions;
    }
    
    
    public boolean createIfMissing()
    {
        return createIfMissing;
    }

    public Options createIfMissing(boolean createIfMissing)
    {
        this.createIfMissing = createIfMissing;
        return this;
    }

    public boolean errorIfExists()
    {
        return errorIfExists;
    }

    public Options errorIfExists(boolean errorIfExists)
    {
        this.errorIfExists = errorIfExists;
        return this;
    }

    public int writeBufferSize()
    {
        return writeBufferSize;
    }

    public Options writeBufferSize(int writeBufferSize)
    {
        this.writeBufferSize = writeBufferSize;
        return this;
    }

    public int maxOpenFiles()
    {
        return maxOpenFiles;
    }

    public Options maxOpenFiles(int maxOpenFiles)
    {
        this.maxOpenFiles = maxOpenFiles;
        return this;
    }

    public int blockRestartInterval()
    {
    	return 0;
    }

    public Options blockRestartInterval(int blockRestartInterval)
    {
        return this;
    }

    public int blockSize()
    {
    	return 0;
    }

    public Options blockSize(int blockSize)
    {
        return this;
    }

    public CompressionType compressionType()
    {
        return compressionType;
    }

    public Options compressionType(CompressionType compressionType)
    {
        checkArgNotNull(compressionType, "compressionType");
        this.compressionType = compressionType;
        return this;
    }

    public boolean verifyChecksums()
    {
        return verifyChecksums;
    }

    public Options verifyChecksums(boolean verifyChecksums)
    {
        this.verifyChecksums = verifyChecksums;
        return this;
    }

    public long cacheSize()
    {
        return cacheSize;
    }

    public Options cacheSize(long cacheSize)
    {
        this.cacheSize = cacheSize;
        return this;
    }

    public DBComparator comparator()
    {
        return comparator;
    }

    public Options comparator(DBComparator comparator)
    {
        this.comparator = comparator;
        return this;
    }

    public Logger logger()
    {
        return logger;
    }

    public Options logger(Logger logger)
    {
        this.logger = logger;
        return this;
    }

    public boolean paranoidChecks()
    {
        return paranoidChecks;
    }

    public Options paranoidChecks(boolean paranoidChecks)
    {
        this.paranoidChecks = paranoidChecks;
        return this;
    }
   
}
