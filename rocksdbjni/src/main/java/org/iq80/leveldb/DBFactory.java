package org.iq80.leveldb;

import java.io.File;
import java.io.IOException;

import org.fusesource.rocksdbjni.internal.NativeDB.DBException;

/**
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public interface DBFactory
{
    DB open(File path, Options options)
            throws IOException;
    
    DB openForReadOnly(File path, Options options)
            throws IOException;
    
    void destroy(File path, Options options)
            throws IOException;

    void repair(File path, Options options)
            throws IOException;
}