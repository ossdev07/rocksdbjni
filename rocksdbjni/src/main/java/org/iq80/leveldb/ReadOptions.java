package org.iq80.leveldb;

public class ReadOptions
{
    private boolean verifyChecksums;
    private boolean fillCache = true;
    private Snapshot snapshot;
    private boolean tailing = false;
    private boolean managed = false;
    private boolean total_order_seek = false;
    private boolean prefix_same_as_start = false;
    private boolean pin_data = false;
    private boolean background_purge_on_iterator_cleanup = false;
    private boolean ignore_range_deletions = false;
    private long readahead_size=0;
    private long max_skippable_internal_keys=0;
    
    
    
    public long readaheadsize() {
        return readahead_size;
    }

    public ReadOptions readaheadsize(long readahead_size) {
        this.readahead_size = readahead_size;
        return this;
    }
    
    public long maxskippableinternalkeys() {
        return max_skippable_internal_keys;
    }

    public ReadOptions maxskippableinternalkeys(long max_skippable_internal_keys) {
        this.max_skippable_internal_keys = max_skippable_internal_keys;
        return this;
    }

    public Snapshot snapshot()
    {
        return snapshot;
    }

    public ReadOptions snapshot(Snapshot snapshot)
    {
        this.snapshot = snapshot;
        return this;
    }

    public boolean fillCache()
    {
        return fillCache;
    }

    public ReadOptions fillCache(boolean fillCache)
    {
        this.fillCache = fillCache;
        return this;
    }

    public boolean verifyChecksums()
    {
        return verifyChecksums;
    }

    public ReadOptions verifyChecksums(boolean verifyChecksums)
    {
        this.verifyChecksums = verifyChecksums;
        return this;
    }
    
    public boolean Tailing() {
        return tailing;
    }

    public ReadOptions Tailing(boolean tailing) {
        this.tailing = tailing;
        return this;
    }
    
    public boolean Managed() {
        return managed;
    }

    public ReadOptions Managed(boolean managed) {
        this.managed = managed;
        return this;
    }
    
    public boolean totalorderseek() {
        return total_order_seek;
    }

    public ReadOptions totalorderseek(boolean total_order_seek) {
        this.total_order_seek = total_order_seek;
        return this;
    }
    
    public boolean prefixsameasstart() {
        return prefix_same_as_start;
    }

    public ReadOptions prefixsameasstart(boolean prefix_same_as_start) {
        this.prefix_same_as_start = prefix_same_as_start;
        return this;
    }
    
    public boolean pindata() {
        return pin_data;
    }

    public ReadOptions pindata(boolean pin_data) {
        this.pin_data = pin_data;
        return this;
    }
    
    public boolean backgroundpurgeoniteratorcleanup() {
        return background_purge_on_iterator_cleanup;
    }

    public ReadOptions backgroundpurgeoniteratorcleanup(boolean background_purge_on_iterator_cleanup) {
        this.background_purge_on_iterator_cleanup = background_purge_on_iterator_cleanup;
        return this;
    }
    
    public boolean ignorerangedeletions() {
        return ignore_range_deletions;
    }

    public ReadOptions ignorerangedeletions(boolean ignore_range_deletions) {
        this.ignore_range_deletions = ignore_range_deletions;
        return this;
    }
}