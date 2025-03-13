package com.lxy.learn.request;

import com.github.pagehelper.PageHelper;
import com.google.common.base.CaseFormat;
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
public class PageRequest {
    /**
     * 页大小记录数
     */
    public Integer pageSize = 100;

    /**
     * 当前页
     */
    public Integer pageNo = 1;

    /**
     * 排序字段
     */
    public String column = "id";

    /**
     * 排序方向 DESC, ASC
     */
    public String order = "DESC";

    /**
     * 是否记录总记录数
     */
    public Boolean count = true;


    public void startPage() {
        startPage(this.column);
    }

    public void startPage(String sortField) {
        if (CommonUtils.isNotEmpty(sortField)) {
            PageHelper.orderBy(sortField + " " + this.order);
        }
        PageHelper.startPage(pageNo, pageSize, count);
    }

    /**
     * 转PageHelper分页参数
     * @return
     */
    public PageHelperRequest toPageHelperRequest() {
        PageHelperRequest pageHelperRequest = new PageHelperRequest();
        if (CommonUtils.isNotEmpty(this.pageNo)) {
            pageHelperRequest.setCurrentPage(this.pageNo);
        }
        if (CommonUtils.isNotEmpty(this.pageSize)) {
            pageHelperRequest.setPageSize(this.pageSize);
        }
        if (CommonUtils.isNotEmpty(this.column)) {
            pageHelperRequest.setSortField(CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, this.column));
        }
        if (CommonUtils.isNotEmpty(this.order)) {
            pageHelperRequest.setDirection(this.order);
        }

        return pageHelperRequest;
    }

    /**
     * 转PageHelper分页参数（Metadata）
     * @return
     */
    public PageHelperRequest toMetadataPageHelperRequest() {
        PageHelperRequest pageHelperRequest = new PageHelperRequest();
        if (CommonUtils.isNotEmpty(this.pageNo)) {
            pageHelperRequest.setCurrentPage(this.pageNo);
        }
        if (CommonUtils.isNotEmpty(this.pageSize)) {
            pageHelperRequest.setPageSize(this.pageSize);
        }
        if (CommonUtils.isNotEmpty(this.column)) {
            pageHelperRequest.setSortField(this.column);
        }
        if (CommonUtils.isNotEmpty(this.order)) {
            pageHelperRequest.setDirection(this.order);
        }
        return pageHelperRequest;
    }
}
