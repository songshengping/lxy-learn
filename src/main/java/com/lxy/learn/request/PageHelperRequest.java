package com.lxy.learn.request;

import com.github.pagehelper.PageHelper;
import com.lxy.learn.utils.CommonUtils;
import lombok.Data;

/**
 * 分页请求类
 * @Author Jeremy
 * @Description 分页请求类
 * @Version 1.0.0
 * @Date 2025/3/13 19:26
 **/
@Data
public class PageHelperRequest {
    /**
     * 页大小记录数
     */
    public Integer pageSize = 100;

    /**
     * 当前页
     */
    public Integer currentPage = 1;

    /**
     * 排序字段
     */
    public String sortField = "id";

    /**
     * 排序方向 DESC, ASC
     */
    public String direction = "DESC";

    /**
     * 是否记录总记录数
     */
    public Boolean count = true;


    public void startPage() {
        startPage(this.sortField);
    }

    public void startPage(String sortField) {
        if (CommonUtils.isNotEmpty(sortField)) {
            PageHelper.orderBy(sortField + " " + this.direction);
        }
        PageHelper.startPage(currentPage, pageSize, count);
    }
}
