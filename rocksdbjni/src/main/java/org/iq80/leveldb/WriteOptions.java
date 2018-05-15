package org.iq80.leveldb;

public class WriteOptions
{
    private boolean sync;
    private boolean snapshot;
    boolean disableWAL ;
    
    boolean ignore_missing_column_families;
    
    boolean no_slowdown ;
    
    boolean low_pri ;

    public boolean sync()
    {
        return sync;
    }

    public WriteOptions sync(boolean sync)
    {
        this.sync = sync;
        return this;
    }

    public boolean snapshot()
    {
        return snapshot;
    }

    public WriteOptions snapshot(boolean snapshot)
    {
        this.snapshot = snapshot;
        return this;
    }
    
    public boolean disableWAL() {
        return sync;
    }

    public WriteOptions disableWAL(boolean disableWAL) {
        this.disableWAL = disableWAL;
        return this;
    }


    public boolean ignore_missing_column_families() {
        return ignore_missing_column_families;
    }

    public WriteOptions ignore_missing_column_families(boolean ignore_missing_column_families) {
        this.ignore_missing_column_families = ignore_missing_column_families;
        return this;
    }


    public boolean no_slowdown() {
        return no_slowdown;
    }

    public WriteOptions no_slowdown(boolean no_slowdown) {
        this.no_slowdown = no_slowdown;
        return this;
    }


    public boolean low_pri() {
        return low_pri;
    }

    public WriteOptions low_pri(boolean low_pri) {
        this.low_pri = low_pri;
        return this;
    }

}
