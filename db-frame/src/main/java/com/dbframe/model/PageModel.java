package com.dbframe.model;



public class PageModel {

    public final static int NO_ROW_OFFSET = 0;
    public final static int NO_ROW_LIMIT = Integer.MAX_VALUE;

    private int offset;
    private int limit;

    public PageModel() {
      this.offset = NO_ROW_OFFSET;
      this.limit = NO_ROW_LIMIT;
    }

    public PageModel(int offset, int limit) {
      this.offset = offset;
      this.limit = limit;
    }

    public int getOffset() {
      return offset;
    }

    public int getLimit() {
      return limit;
    }
	
}
