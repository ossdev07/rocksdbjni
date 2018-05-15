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
package org.fusesource.rocksdbjni.test;

import junit.framework.TestCase;
import org.fusesource.rocksdbjni.JniDBFactory;
import org.fusesource.rocksdbjni.internal.JniColumnFamilyHandle;
import org.fusesource.rocksdbjni.internal.JniColumnFamilyOptions;
import org.fusesource.rocksdbjni.internal.JniDB;
import org.iq80.leveldb.*;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.*;

import static org.fusesource.rocksdbjni.JniDBFactory.asString;
import static org.fusesource.rocksdbjni.JniDBFactory.bytes;

/**
 * A Unit test for the DB class implementation.
 *
 * @author <a href="http://hiramchirino.com">Hiram Chirino</a>
 */
public class DBTest extends TestCase {
    DBFactory factory = JniDBFactory.factory;

    File getTestDirectory(String name) throws IOException {
        File rc = new File(new File("test-data"), name);
        rc.mkdirs();
        return rc;
    }
  	

    @Test
    public void testOpen() throws IOException {

        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);
        
        db.put(bytes("Tampa"), bytes("green"));
        
        db.destroydb(path, options);
       
        db.close();

        // Try again.. this time we expect a failure since it exists.
        options = new Options().errorIfExists(true);
        try {
            factory.open(path, options);
            fail("Expected exception.");
        } catch (IOException e) {
        	
        }
    }
    @Test
    public void testRepair() throws IOException, DBException {
        testCRUD();
        factory.repair(new File(new File("test-data"), getName()), new Options());
    }

    @Test
    public void testCRUD() throws IOException, DBException {

        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        WriteOptions wo = new WriteOptions().sync(false);
        ReadOptions ro = new ReadOptions().fillCache(true).verifyChecksums(true);

        db.put(bytes("Tampa"), bytes("green"));
        db.put(bytes("London"), bytes("red"));
        db.put(bytes("New York"), bytes("blue"));

        assertEquals(db.get(bytes("Tampa"), ro), bytes("green"));
        assertEquals(db.get(bytes("London"), ro), bytes("red"));
        assertEquals(db.get(bytes("New York"), ro), bytes("blue"));

        db.delete(bytes("New York"), wo);
        assertNull(db.get(bytes("New York"), ro));

        // rocksdb does not consider deleting something that does not exist an error.
        db.delete(bytes("New York"), wo);

        db.close();
    }

    @Test
    public void testIterator() throws IOException, DBException {

        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        db.put(bytes("Tampa"), bytes("green"));
        db.put(bytes("London"), bytes("red"));
        db.put(bytes("New York"), bytes("blue"));

        ArrayList<String> expecting = new ArrayList<String>();
        expecting.add("London");
        expecting.add("New York");
        expecting.add("Tampa");

        ArrayList<String> actual = new ArrayList<String>();

        DBIterator iterator = db.iterator();
        for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
            actual.add(asString(iterator.peekNext().getKey()));
        }
        iterator.close();
        assertEquals(expecting, actual);

        db.close();
    }

    @Test
    public void testSnapshot() throws IOException, DBException {

        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        db.put(bytes("Tampa"), bytes("green"));
        db.put(bytes("London"), bytes("red"));
        db.delete(bytes("New York"));

        ReadOptions ro = new ReadOptions().snapshot(db.getSnapshot());

        db.put(bytes("New York"), bytes("blue"));

        assertEquals(db.get(bytes("Tampa"), ro), bytes("green"));
        assertEquals(db.get(bytes("London"), ro), bytes("red"));

        // Should not be able to get "New York" since it was added
        // after the snapshot
        assertNull(db.get(bytes("New York"), ro));

        ro.snapshot().close();

        // Now try again without the snapshot..
        ro.snapshot(null);
        assertEquals(db.get(bytes("New York"), ro), bytes("blue"));

        db.close();
    }

    @Test
    public void testWriteBatch() throws IOException, DBException {

        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        db.put(bytes("NA"), bytes("Na"));

        WriteBatch batch = db.createWriteBatch();
        batch.delete(bytes("NA"));
        batch.put(bytes("Tampa"), bytes("green"));
        batch.put(bytes("London"), bytes("red"));
        batch.put(bytes("New York"), bytes("blue"));
        db.write(batch);
        batch.close();

        ArrayList<String> expecting = new ArrayList<String>();
        expecting.add("London");
        expecting.add("New York");
        expecting.add("Tampa");

        ArrayList<String> actual = new ArrayList<String>();

        DBIterator iterator = db.iterator();
        for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
            actual.add(asString(iterator.peekNext().getKey()));
        }
        iterator.close();
        assertEquals(expecting, actual);
        

        db.close();
    }

    @Test
    public void testApproximateSizes() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        Random r = new Random(0);
        String data="";
        for(int i=0; i < 1024; i++) {
            data+= 'a'+r.nextInt(26);
        }
        for(int i=0; i < 5*1024; i++) {
            db.put(bytes("row"+i), bytes(data));
        }

        long[] approximateSizes = db.getApproximateSizes(new Range(bytes("row"), bytes("s")));
        assertNotNull(approximateSizes);
        assertEquals(1, approximateSizes.length);
        assertTrue("Wrong size", approximateSizes[0] > 0);

        db.close();
    }

    @Test
    public void testGetProperty() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        Random r = new Random(0);
        String data="";
        for(int i=0; i < 1024; i++) {
            data+= 'a'+r.nextInt(26);
        }
        for(int i=0; i < 5*1024; i++) {
            db.put(bytes("row"+i), bytes(data));
        }

        String stats = db.getProperty("rocksdb.stats");
        assertNotNull(stats);
        assertTrue(stats.contains("Compaction Stats"));
     	assertTrue(stats.contains("DB Stats"));

        db.close();
    }

    @Test
    public void testCustomComparator1() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);
        options.comparator(new DBComparator() {

            public int compare(byte[] key1, byte[] key2) {
                return new String(key1).compareTo(new String(key2));
            }

            public String name() {
            	
                return getName();
            }

            public byte[] findShortestSeparator(byte[] start, byte[] limit) {
                return start;
            }

            public byte[] findShortSuccessor(byte[] key) {
                return key;
            }
        });

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        ArrayList<String> expecting = new ArrayList<String>();
        for(int i=0; i < 26; i++) {
            String t = ""+ ((char) ('a' + i));
            expecting.add(t);
            db.put(bytes(t), bytes(t));
        }

        ArrayList<String> actual = new ArrayList<String>();

        DBIterator iterator = db.iterator();
        for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
            actual.add(asString(iterator.peekNext().getKey()));
        }
        iterator.close();
        assertEquals(expecting, actual);


        db.close();
    }

    @Test
    public void testCustomComparator2() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);
        options.comparator(new DBComparator() {

            public int compare(byte[] key1, byte[] key2) {
                return new String(key1).compareTo(new String(key2)) * -1;
            }

            public String name() {
                return getName();
            }

            public byte[] findShortestSeparator(byte[] start, byte[] limit) {
                return start;
            }

            public byte[] findShortSuccessor(byte[] key) {
                return key;
            }
        });

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        ArrayList<String> expecting = new ArrayList<String>();
        for(int i=0; i < 26; i++) {
            String t = ""+ ((char) ('a' + i));
            expecting.add(t);
            db.put(bytes(t), bytes(t));
        }
        Collections.reverse(expecting);

        ArrayList<String> actual = new ArrayList<String>();
        DBIterator iterator = db.iterator();
        for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
            actual.add(asString(iterator.peekNext().getKey()));
        }
        iterator.close();
        assertEquals(expecting, actual);

        db.close();
    }

   /* @Test
    public void testLogger() throws IOException, InterruptedException, DBException {
        final List<String> messages = Collections.synchronizedList(new ArrayList<String>());

        Options options = new Options().createIfMissing(true);
        options.logger(new Logger() {
            public void log(String message) {
                messages.add(message);
                System.out.println("DBTest message ="+message);
            }
        });

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        for( int j=0; j < 1; j++) {
            Random r = new Random(0);
            String data="";
            for(int i=0; i < 1024; i++) {
                data+= 'a'+r.nextInt(26);
                }
          //System.out.println(data);
            for(int i=0; i < 1*1024; i++) {
                db.put(bytes("row"+i), bytes(data));
            }
            Thread.sleep(100);
        }

        db.close();
        
        assertFalse(messages.isEmpty());

    }*/

    @Test
    public void testCompactRanges() throws IOException, InterruptedException, DBException {
        Options options = new Options().createIfMissing(true);
        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);
        if( db instanceof JniDB) {
            Random r = new Random(0);
            String data="";
            for(int i=0; i < 1024; i++) {
                data+= 'a'+r.nextInt(26);
            }
            for(int i=0; i < 5*1024; i++) {
                db.put(bytes("row"+i), bytes(data));
            }
            for(int i=0; i < 5*1024; i++) {
                db.delete(bytes("row" + i));
            }

            String stats = db.getProperty("rocksdb.stats");

            //                                               Compaction
                                    //Level    Files   Size     Score Read(GB)  Rn(GB) Rnp1(GB) Write(GB)
            //-------------------------------------------------------------------
	        assertFalse(stats.contains("L0     0/0     0.00 KB"));

            // After the compaction, level 1 should not have any files in it..

	        CompactRangeOptions compact = new CompactRangeOptions();
            ((JniDB) db).compactRange(compact,null, null);

            stats = db.getProperty("rocksdb.stats");
	         assertTrue(stats.contains("L0      0/0    0.00 KB"));
	         assertTrue(stats.contains("L1      0/0    0.00 KB"));

        }
        db.close();
    }

    public void assertEquals(byte[] arg1, byte[] arg2) {
        assertTrue(Arrays.equals(arg1, arg2));
    }

    @Test
    public void testIssue26() throws IOException {

        JniDBFactory.pushMemoryPool(1024 * 512);
        try {
            Options options = new Options();
            options.createIfMissing(true);

            DB db = factory.open(getTestDirectory(getName()), options);

            for (int i = 0; i < 1024 * 1024; i++) {
                byte[] key = ByteBuffer.allocate(4).putInt(i).array();
                byte[] value = ByteBuffer.allocate(4).putInt(-i).array();
                db.put(key, value);
                assertTrue(Arrays.equals(db.get(key), value));
            }
            db.close();
        } finally {
            JniDBFactory.popMemoryPool();
        }

    }

    @Test
    public void testSeekForPrev() throws IOException, DBException { 
    	
    Options options = new Options().createIfMissing(true);
    File path = getTestDirectory(getName());
    DB db = factory.open(path, options);
    
    db.put(bytes("c1"), bytes("black"));
    db.put(bytes("a3"), bytes("green"));
    db.put(bytes("a5"), bytes("red"));
    db.put(bytes("b1"), bytes("blue"));
    
         
    ArrayList<String> expecting = new ArrayList<String>();
    
    expecting.add("b1");
    
    ArrayList<String> actual = new ArrayList<String>();
    
    //Checking refresh without readoptions.snapshot
    
    DBIterator iterator = db.iterator();
    
    iterator.refreshIterator();
    
    iterator.close();
    
    //Checking refresh with readoptions.snapshot
    
    ReadOptions ro = new ReadOptions().snapshot(db.getSnapshot());
    
    iterator = db.iterator();
    
    iterator.seekForPrev(bytes("b10"));
    
    actual.add(asString(iterator.peekNext().getKey()));
    
    iterator.refreshIterator();
    
    iterator.close();
    
   assertEquals(expecting, actual);

    db.close();
}
    @Test
    public void testNewWriteBatch() throws IOException ,DBException
    {
    	Options options=new Options().createIfMissing(true);
    	
    	
    	
    	 File path=getTestDirectory(getName());
         DB db=factory.open(path, options);
         
         
        WriteBatch batch=db.createWriteBatch();
        
        ColumnFamilyHandle handle = new JniColumnFamilyHandle();
         
        //to test functionality of singledelete with only the assosiated key wee have to use put functionality
        //with only the key and value.
        
        batch.put(bytes("New York"), bytes("red"));
        batch.put(bytes("Canada"), bytes("blue"));
            
        db.write(batch);
        
        assertEquals(db.get(bytes("Canada")),bytes("blue"));
               
        batch.single_delete(bytes("Canada"));
         
        db.write(batch);
        
        assertNotSame(db.get(bytes("Canada")),bytes("blue"));
        
        assertNull(db.get(bytes("Canada")));
        
        batch.put(handle,bytes("Tampa"), bytes("green"));
        batch.put(handle,bytes("London"), bytes("red"));
        
        db.write(batch);
        
        assertEquals(db.get(bytes("Tampa")),bytes("green"));
        assertEquals(db.get(bytes("London")),bytes("red"));
        
        batch.single_delete(handle, bytes("Tampa"));
        batch.delete(handle,bytes("London"));
        
        db.write(batch);
        
        assertNull(db.get(bytes("Tampa")));
        assertNull(db.get(bytes("London")));  
        
        batch.put(handle,bytes("Tampa"), bytes("green"));
        
        db.write(batch);
        
        batch.deleteRange(handle,bytes("Tampa"), bytes("green"));
        
        db.write(batch);
        
        assertNull(db.get(bytes("Tampa")));
        
        db.write(batch);
        batch.close();
         
        db.close();
        
    }

    @Test
    public void testIssue27() throws IOException {

        Options options = new Options();
        options.createIfMissing(true);
        DB db = factory.open(getTestDirectory(getName()), options);
        db.close();

        try {
            db.iterator();
            fail("Expected a DBException");
        } catch(DBException e) {
        }

    } 
    
    
    @Test
    public void testColumnFamily() throws IOException, DBException {
    Options options = new Options().createIfMissing(true);
    File path = getTestDirectory(getName());
    DB db = factory.open(path, options);

    if( db instanceof JniDB) {

         JniColumnFamilyOptions columnOptions = new JniColumnFamilyOptions();

         ColumnFamilyHandle columnFamilyHandles =  new JniColumnFamilyHandle();
         columnFamilyHandles =((JniDB) db).createColumnFamily(columnOptions,path);

        ((JniDB) db).put(columnFamilyHandles,bytes("Tampa"), bytes("green"));
        ((JniDB) db).put(columnFamilyHandles,bytes("Verma"), bytes("red"));
        ReadOptions ro = new ReadOptions().fillCache(true).verifyChecksums(true);
        assertEquals((db.get(bytes("Tampa"), ro)), bytes("green"));
        assertEquals((db.get(bytes("Verma"), ro)), bytes("red"));
        ((JniDB) db).singleDelete(columnFamilyHandles,bytes("Verma"));
        assertNotSame((db.get(bytes("Verma"), ro)), bytes("red"));
        ((JniDB) db).destroyColumnFamily(columnFamilyHandles);
        WriteOptions wo = new WriteOptions().sync(false);
        ((JniDB) db).delete(bytes("New York"), wo, columnFamilyHandles);

        }
        db.close();
    }

       @Test
    	public void testDeleteRange() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);
        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);

        if( db instanceof JniDB) {

         JniColumnFamilyOptions columnOptions = new JniColumnFamilyOptions();

         ColumnFamilyHandle columnFamilyHandles =  new JniColumnFamilyHandle();

         columnFamilyHandles =((JniDB) db).createColumnFamily(columnOptions,path);


         ((JniDB) db).put(columnFamilyHandles,bytes("key1"), bytes("green"));

        ((JniDB) db).put(columnFamilyHandles,bytes("key2"), bytes("red"));
        ((JniDB) db).put(columnFamilyHandles,bytes("key3"), bytes("blue"));
        ((JniDB) db).put(columnFamilyHandles,bytes("key4"), bytes("white"));
        ((JniDB) db).put(columnFamilyHandles,bytes("key5"), bytes("white"));

         ((JniDB) db).deleteRange(columnFamilyHandles,bytes("key2"), bytes("key5"));
         assertNull(db.get(bytes("key2")));
         assertNull(db.get(bytes("key3")));
         assertNull(db.get(bytes("key4")));

         ((JniDB) db).destroyColumnFamily(columnFamilyHandles);

        }
        db.close();
    } 

      @Test
       public void testOpenForReadOnly() throws IOException, DBException {
       Options options = new Options().createIfMissing(true);
       File path = getTestDirectory(getName());
       DB db = factory.open(path, options);
       db.put(bytes("Tampa"), bytes("green"));
       DB db2 = factory.openForReadOnly(path,options);
       assertEquals(db2.get(bytes("Tampa")), bytes("green"));

       try {
                db2.put(bytes("Tampa"), bytes("red"));
               fail("Expected exception.");
       } catch (Exception e) {
       }
       db2.close();
       db.close();
   } 
    @Test
    public void testGetIntProperty() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);

       File path = getTestDirectory(getName());
       DB db = factory.open(path, options);

       Random r = new Random(0);
       String data="";
       for(int i=0; i < 1024; i++) {
           data+= 'a'+r.nextInt(26);
       }
       for(int i=0; i < 5*1024; i++) {
           db.put(bytes("row"+i), bytes(data));
       }


        assertNotNull(db.getIntProperty("rocksdb.estimate-num-keys"));

       db.close();
   }
 
    
    @Test
    public void testKeyMayExist() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);
        
        ReadOptions ro = new ReadOptions().fillCache(true).verifyChecksums(true);

        db.put(bytes("Tampa"), bytes("green"));
        db.put(bytes("London"), bytes("red"));
        db.put(bytes("New York"), bytes("blue"));
        
        assertTrue(db.keyMayExist(bytes("Tampa"), ro));
        assertTrue(db.keyMayExist(bytes("London"), ro));
        assertTrue(db.keyMayExist(bytes("New York"), ro));

        assertFalse(db.keyMayExist(bytes("New Yorkhsd"), ro));

        db.close();
    }
    
    @Test
    public void testgetPropertyIterator() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);
        
        db.put(bytes("Tampa"), bytes("green"));
        db.put(bytes("London"), bytes("red"));
        db.put(bytes("New York"), bytes("blue"));

        ArrayList<String> expecting = new ArrayList<String>();
        expecting.add("London");
        expecting.add("New York");
        expecting.add("Tampa");

        ArrayList<String> actual = new ArrayList<String>();

        DBIterator iterator = db.iterator();
        for (iterator.seekToFirst(); iterator.hasNext(); iterator.next()) {
            actual.add(asString(iterator.peekNext().getKey()));
        }
        
        String prop = iterator.getProperty("rocksdb.iterator.super-version-number");
        assertEquals(prop, "1");
        
        iterator.close();
        assertEquals(expecting, actual);


        db.close();
    }
    
    @Test
    public void testNumberLevels() throws IOException, DBException {
        Options options = new Options().createIfMissing(true);

        File path = getTestDirectory(getName());
        DB db = factory.open(path, options);
        
        db.put(bytes("Tampa"), bytes("green"));
        db.put(bytes("London"), bytes("red"));
        db.put(bytes("New York"), bytes("blue"));

        assertNotNull(db.numberLevels());

        db.close();
    }
    
}
