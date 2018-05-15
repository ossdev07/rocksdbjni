package org.iq80.leveldb;

import org.fusesource.hawtjni.runtime.JniField;
import org.fusesource.rocksdbjni.internal.NativeCompactRangeOptions;

public class CompactRangeOptions {

	  private boolean exclusive_manual_compaction = true;
      
      private boolean change_level = false;
      
      private int target_level = -1;
      
      @JniField(cast="uint32_t")
      private long target_path_id = 0;
      
      
      public CompactRangeOptions exclusiveManualCompaction(boolean value) {
          this.exclusive_manual_compaction = value;
          return this;
      }
      public boolean exclusiveManualCompaction() {
          return exclusive_manual_compaction;
      }
      
      public CompactRangeOptions changeLevel(boolean value) {
          this.change_level = value;
          return this;
      }
      public boolean changeLevel() {
          return change_level;
      }
      
      public CompactRangeOptions targetLevel(int value) {
          this.target_level = value;
          return this;
      }
      public int targetLevel() {
          return target_level;
      }
      
      
      public CompactRangeOptions targetPathId(long value) {
          this.target_path_id = value;
          return this;
      }
      public long targetPathId() {
          return target_path_id;
      }
	
}
