package com.graph.framework.bean;

import java.util.List;

/**
 * Create by xiaow on 2018/11/13
 * @Version 1.0.0
 */
public class Page<T> extends BaseBean {

    private long startRow = 0; //开始记录
    private Integer pageSize = 20; //页面大小
    private long totalRow; //总记录条数
    private long totalPage; //总页数
    private long curPage = 1; //当前页码
    private List<T> list;

    /**
     * getter
     * @return
     */
    public long getStartRow() {
        return startRow;
    }

    /**
     *  setter
     * @param startRow
     */
    public void setStartRow(long startRow) {
        this.startRow = startRow;
    }

    /**
     * getter
     * @return
     */
    public Integer getPageSize() {
        return pageSize;
    }

    /**
     *  setter
     * @param pageSize
     */
    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * getter
     * @return
     */
    public long getTotalRow() {
        return totalRow;
    }

    /**
     *  setter
     * @param totalRow
     */
    public void setTotalRow(long totalRow) {
        this.totalRow = totalRow;
    }

    /**
     * getter
     * @return
     */
    public long getTotalPage() {
        return totalPage;
    }

    /**
     * sertter
     * @param totalPage
     */
    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    /**
     * getter
     * @return
     */
    public long getCurPage() {
        return curPage;
    }

    /**
     * setter
     * @param curPage
     */
    public void setCurPage(long curPage) {
        this.curPage = curPage;
    }

    /**
     * getter
     * @return
     */
    public List<T> getList() {
        return list;
    }

    /**
     * setter
     * @param list
     */
    public void setList(List<T> list) {
        this.list = list;
    }
}
