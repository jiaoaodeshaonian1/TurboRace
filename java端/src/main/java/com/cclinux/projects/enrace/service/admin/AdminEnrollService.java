package com.cclinux.projects.enrace.service.admin;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cclinux.framework.core.domain.PageParams;
import com.cclinux.framework.core.domain.PageResult;
import com.cclinux.framework.core.mapper.UpdateWhere;
import com.cclinux.framework.core.mapper.Where;
import com.cclinux.framework.core.mapper.WhereJoin;
import com.cclinux.framework.helper.FileHelper;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.projects.enrace.comm.ProjectConfig;
import com.cclinux.projects.enrace.mapper.EnrollJoinMapper;
import com.cclinux.projects.enrace.mapper.EnrollMapper;
import com.cclinux.projects.enrace.model.EnrollJoinModel;
import com.cclinux.projects.enrace.model.EnrollModel;
import com.cclinux.projects.enrace.model.UserModel;
import com.cclinux.projects.enrace.service.cust.EnrollService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Notes: 报名业务逻辑
 * @Author: cclinux0730 (weixin)
 * @Ver: ccminicloud-framework 3.2.1
 */


@Service("EnRaceAdminEnrollService")
public class AdminEnrollService extends BaseMyAdminService {


    @Resource(name = "EnRaceEnrollMapper")
    private EnrollMapper enrollMapper;

    @Resource(name = "EnRaceEnrollJoinMapper")
    private EnrollJoinMapper enrollJoinMapper;

    @Resource(name = "EnRaceEnrollService")
    private EnrollService enrollService;


    /**
     * 添加报名
     */
    public long insertEnroll(EnrollModel enroll) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
        return 0;
    }

    /**
     * 修改报名
     */
    public void editEnroll(EnrollModel enroll) {

        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");

    }


    /**
     * 比赛列表
     */
    public PageResult getAdminEnrollList(PageParams pageRequest) {

        Where<EnrollModel> where = new Where<>();

        // 关键字查询
        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            where.and(wrapper -> {
                wrapper.or().like("ENROLL_TITLE", search);
            });
        }

        // 条件查询
        String sortType = pageRequest.getSortType();
        String sortVal = pageRequest.getSortVal();
        if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
            switch (sortType) {
                case "cateId": {
                    where.eq("ENROLL_CATE_ID", Convert.toLong(sortVal));
                    break;
                }
                case "status": {
                    where.eq("ENROLL_STATUS", Convert.toInt(sortVal));
                    break;
                }
                case "vouch": {
                    where.eq("ENROLL_VOUCH", 1);
                    break;
                }
                case "top": {
                    where.eq("ENROLL_ORDER", 0);
                    break;
                }
                case "sort": {
                    logger.info("SortVal" + sortVal);
                    where.fmtOrderBySort(sortVal, "");
                    break;
                }
            }

        }

        // 排序
        where.orderByAsc("ENROLL_ORDER");
        where.orderByDesc("ENROLL_ID");


        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return enrollMapper.getPageList(page, where, "*");
    }

    /**
     * 活动报名名单
     */
    public PageResult getAdminEnrollJoinList(PageParams pageRequest) {

        WhereJoin<EnrollJoinModel> where = new WhereJoin<>();
        where.leftJoin(UserModel.class, UserModel::getUserId, EnrollJoinModel::getEnrollJoinUserId);


        long enrollId = pageRequest.getParamLong("enrollId");
        where.eq("ENROLL_JOIN_ENROLL_ID", enrollId);

        // 关键字查询
        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            where.and(wrapper -> {
                wrapper.or().like("t1.USER_OBJ", search);
                wrapper.or().like("t1.USER_NAME", search);
            });
        }

        // 条件查询
        String sortType = pageRequest.getSortType();
        String sortVal = pageRequest.getSortVal();
        if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
            switch (sortType) {
                case "status": {
                    where.eq("ENROLL_JOIN_STATUS", Convert.toInt(sortVal));
                    break;
                }
                case "sort": {
                    logger.info("SortVal" + sortVal);
                    where.fmtOrderBySort(sortVal, "");
                    break;
                }
            }

        }

        // 排序
        where.orderByDesc("ENROLL_JOIN_ID");

        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return enrollJoinMapper.getPageJoinList(page, where, "t.*,t1.USER_NAME,t1.USER_FORMS,t1.USER_OBJ");
    }

    /**
     * 删除报名
     */
    public void delEnroll(long id) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");

    }

    /**
     * 删除报名
     */
    public void delEnrollJoin(long enrollJoinId) {

        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }


    /**
     * 获取单个报名
     */
    public Map<String, Object> getEnrollDetail(long id) {
        return enrollMapper.getOneMap(id);
    }

    /**
     * 修改报名状态
     */
    public void statusEnroll(long id, int status) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }


    /**
     * 修改排序
     */
    public void orderEnroll(long id, int order) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }


    /**
     * 首页设定
     */
    public void vouchEnroll(long id, int vouch) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }

    /**
     * 导出名单
     */
    public Map<String, Object> exportEnrollJoinExcel(long enrollId, String fmt) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
        return null;
    }

    /**
     * 清空名单
     */
    public void clearEnrollAll(long enrollId) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }


}
