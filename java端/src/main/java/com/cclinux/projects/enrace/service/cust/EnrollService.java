package com.cclinux.projects.enrace.service.cust;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cclinux.framework.annotation.LoginIgnore;
import com.cclinux.framework.core.domain.PageParams;
import com.cclinux.framework.core.domain.PageResult;
import com.cclinux.framework.core.mapper.UpdateWhere;
import com.cclinux.framework.core.mapper.Where;
import com.cclinux.framework.core.mapper.WhereJoin;
import com.cclinux.framework.exception.AppException;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.framework.helper.TimeHelper;
import com.cclinux.projects.enrace.mapper.EnrollJoinMapper;
import com.cclinux.projects.enrace.mapper.EnrollMapper;
import com.cclinux.projects.enrace.model.EnrollJoinModel;
import com.cclinux.projects.enrace.model.EnrollModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Notes: 报名模块业务逻辑
 * @Author: cclinux0730 (weixin)
 * @Ver: ccminicloud-framework 3.2.1
 */

@Service("EnRaceEnrollService")
public class EnrollService extends BaseMyCustService {

    @Resource(name = "EnRaceEnrollMapper")
    private EnrollMapper enrollMapper;

    @Resource(name = "EnRaceEnrollJoinMapper")
    private EnrollJoinMapper enrollJoinMapper;


    /**
     * 计算状态
     */
    public String getEnrollStatusDesc(long id) {
        EnrollModel enroll = enrollMapper.getOne(id);
        if (ObjectUtil.isNull((enroll))) return "";

        return getEnrollStatusDesc(enroll.getEnrollStatus(), enroll.getEnrollStart(), enroll.getEnrollEnd(), enroll.getEnrollJoinCnt(), enroll.getEnrollMaxCnt());
    }


    /**
     * 计算状态
     */
    public String getEnrollStatusDesc(Map<String, Object> map) {
        if (ObjectUtil.isNull((map))) return "";

        int status = MapUtil.getInt(map, "enrollStatus");

        long start = MapUtil.getLong(map, "enrollStart");
        long end = MapUtil.getLong(map, "enrollEnd");

        long joinCnt = MapUtil.getLong(map, "enrollJoinCnt");
        long maxCnt = MapUtil.getLong(map, "enrollMaxCnt");

        return getEnrollStatusDesc(status, start, end, joinCnt, maxCnt);
    }

    /**
     * 计算状态
     */
    public String getEnrollStatusDesc(int status, long start, long end, long joinCnt, long maxCnt) {


        long time = TimeHelper.timestamp();

        if (status == EnrollModel.STATUS.STOP) return "报名停止";
        else if (NumberUtil.compare(start, time) > 0) return "报名未开始";
        else if (NumberUtil.compare(end, time) < 0) return "报名结束";
        else if (joinCnt >= maxCnt) return "报名已满";
        else return "报名中";
    }

    /**
     * 报名列表
     */
    @LoginIgnore
    public PageResult getEnrollList(PageParams pageRequest) {

        Where<EnrollModel> where = new Where<>();
        where.eq("ENROLL_STATUS", EnrollModel.STATUS.NORMAL);

        long cateId = pageRequest.getParamLong("cateId");
        if (NumberUtil.compare(cateId, 0) > 0) where.eq("ENROLL_CATE_ID", cateId);

        // 关键字查询
        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            where.and(wrapper -> {
                wrapper.or().like("ENROLL_TITLE", search);
                wrapper.or().like("ENROLL_OBJ", search);
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
                case "today": {
                    long start = TimeHelper.getDayFirstTimestamp();
                    long end = start + 86400 * 1000 - 1;
                    where.between("ENROLL_START", start, end);
                    break;
                }
                case "tomorrow": {
                    long start = TimeHelper.getDayFirstTimestamp() + 86400 * 1000;
                    long end = start + 86400 * 1000 - 1;
                    where.between("ENROLL_START", start, end);
                    break;
                }
                case "month": {
                    long start = TimeHelper.getMonthFirstTimestamp();
                    long end = TimeHelper.getMonthLastTimestamp();
                    where.between("ENROLL_START", start, end);
                    break;
                }
                case "sort": {
                    where.fmtOrderBySort(sortVal, "");
                    break;
                }
            }

        }

        // 排序
        where.orderByAsc("ENROLL_ORDER");
        where.orderByDesc("ENROLL_ID");


        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return enrollMapper.getPageList(page, where, "ENROLL_STATUS,ENROLL_ID,ENROLL_TITLE,ENROLL_OBJ," + "ENROLL_START," + "ENROLL_END,ENROLL_CATE_NAME,ENROLL_MAX_CNT,ENROLL_JOIN_CNT");
    }


    /**
     * 热门比赛排行
     */
    @LoginIgnore
    public PageResult getEnrollRankList(PageParams pageRequest) {

        Where<EnrollModel> where = new Where<>();
        where.eq("ENROLL_STATUS", EnrollModel.STATUS.NORMAL);

        // 排序
        where.orderByDesc("ENROLL_JOIN_CNT");
        where.orderByDesc("ENROLL_ID");


        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return enrollMapper.getPageList(page, where, "ENROLL_STATUS,ENROLL_ID,ENROLL_TITLE,ENROLL_OBJ," + "ENROLL_START," + "ENROLL_END,ENROLL_CATE_NAME,ENROLL_MAX_CNT,ENROLL_JOIN_CNT");
    }

    /**
     * 取得我的报名详情
     */
    public Map<String, Object> getMyEnrollJoinDetail(long userId, long enrollJoinId) {
        Where<EnrollJoinModel> where = new Where<>();
        where.eq("ENROLL_JOIN_ID", enrollJoinId);
        where.eq("ENROLL_JOIN_USER_ID", userId);
        Map<String, Object> ret = enrollJoinMapper.getOneMap(where);
        if (ObjectUtil.isEmpty(ret)) return ret;

        long enrollId = MapUtil.getLong(ret, "enrollJoinEnrollId");
        ret.put("enroll", enrollMapper.getOneMap(enrollId, "ENROLL_TITLE,ENROLL_START,ENROLL_END"));

        return ret;
    }


    /**
     * 我的报名列表
     */
    @LoginIgnore
    public PageResult getMyEnrollJoinList(long userId, PageParams pageRequest) {

        WhereJoin<EnrollJoinModel> where = new WhereJoin<>();
        where.leftJoin(EnrollModel.class, EnrollModel::getEnrollId, EnrollJoinModel::getEnrollJoinEnrollId);

        where.eq("t.ENROLL_JOIN_USER_ID", userId);

        // 关键字查询
        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            where.and(wrapper -> {
                wrapper.or().like("t.ENROLL_JOIN_CODE", search);
                wrapper.or().like("t1.ENROLL_TITLE", search);
            });
        }

        // 条件查询
        String sortType = pageRequest.getSortType();
        String sortVal = pageRequest.getSortVal();
        if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
            switch (sortType) {
                case "sort": {
                    where.fmtOrderBySort(sortVal, "");
                    break;
                }
            }

        }

        // 排序
        where.orderByDesc("ENROLL_JOIN_ID");


        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return enrollJoinMapper.getPageJoinList(page, where, "t.*,t1.ENROLL_TITLE,t1.ENROLL_OBJ");
    }


    /**
     * 单个浏览
     */
    @LoginIgnore
    public Map<String, Object> view(long id, long userId) {

        // PV
        UpdateWhere<EnrollModel> uw = new UpdateWhere<>();
        uw.eq("ENROLL_ID", id);
        enrollMapper.inc(uw, "ENROLL_VIEW_CNT");

        Where<EnrollModel> where = new Where<>();
        where.eq("ENROLL_ID", id);
        where.eq("ENROLL_STATUS", EnrollModel.STATUS.NORMAL);

        Map<String, Object> ret = enrollMapper.getOneMap(where);
        if (ObjectUtil.isNull((ret))) return null;

        if (NumberUtil.equals(userId, 0)) {
            ret.put("myEnrollJoinId", "");
            ret.put("myEnrollJoinTag", "");

            return ret;
        }

        // 判断是否有报名
        Where<EnrollJoinModel> whereJoin = new Where<>();
        whereJoin.eq("ENROLL_JOIN_USER_ID", userId);
        whereJoin.eq("ENROLL_JOIN_ENROLL_ID", id);
        whereJoin.eq("ENROLL_JOIN_STATUS", EnrollModel.STATUS.NORMAL);
        EnrollJoinModel enrollJoin = enrollJoinMapper.getOne(whereJoin);
        if (ObjectUtil.isNotNull(enrollJoin)) {
            // 已选报名
            ret.put("myEnrollJoinId", enrollJoin.getEnrollJoinId());
            ret.put("myEnrollJoinTag", "已报名");
        }


        return ret;
    }

    /**
     * 报名前获取关键信息
     */
    public Map<String, Object> detailForEnrollJoin(long userId, long enrollId) {
        Where<EnrollModel> where = new Where<>();
        where.eq("ENROLL_ID", enrollId);
        where.eq("ENROLL_STATUS", EnrollModel.STATUS.NORMAL);
        Map<String, Object> ret = enrollMapper.getOneMap(where, "ENROLL_TITLE");
        if (ObjectUtil.isEmpty(ret)) throw new AppException("该比赛不存在");
        logger.info(ret.toString());

        // 取出本人最近一次的填写表单
        Where<EnrollJoinModel> whereJoin = new Where<>();
        whereJoin.eq("ENROLL_JOIN_USER_ID", userId);
        whereJoin.orderByDesc("ENROLL_JOIN_ID");
        Map<String, Object> retJoin = enrollJoinMapper.getOneMap(whereJoin, "ENROLL_JOIN_FORMS");

        if (ObjectUtil.isEmpty(retJoin)) {
            ret.put("myForms", null);
        } else {
            ret.put("myForms", retJoin.get("enrollJoinForms"));

        }

        return ret;
    }

    /**
     * 报名
     */
    public long enrollJoin(long userId, long enrollId) {

        return 0;
    }

    /**
     * 取消我的报名取消即为删除记录
     */
    public void cancelMyEnrollJoin(long userId, long enrollJoinId) {
        Where<EnrollJoinModel> whereJoin = new Where<>();
        whereJoin.eq("ENROLL_JOIN_ID", enrollJoinId);
        whereJoin.eq("ENROLL_JOIN_STATUS", EnrollJoinModel.STATUS.NORMAL);
        EnrollJoinModel enrollJoin = enrollJoinMapper.getOne(whereJoin);

        if (ObjectUtil.isEmpty(enrollJoin)) throw new AppException("未找到可取消的报名记录");


        EnrollModel enroll = enrollMapper.getOne(enrollJoin.getEnrollJoinEnrollId());
        if (ObjectUtil.isEmpty(enroll)) throw new AppException("该课程不存在，无法取消");

        if (NumberUtil.compare(enroll.getEnrollEnd(), TimeHelper.timestamp()) < 0)
            throw new AppException("该报名已经结束，无法取消");

        enrollJoinMapper.delete(enrollJoinId);

        // 统计
        statEnrollJoin(enrollJoin.getEnrollJoinEnrollId());
    }


    /**
     * 统计数量
     */
    public void statEnrollJoin(long id) {
        Where<EnrollJoinModel> whereJoin = new Where<>();
        whereJoin.eq("ENROLL_JOIN_ENROLL_ID", id);
        long cnt = enrollJoinMapper.count(whereJoin);

        UpdateWhere<EnrollModel> uw = new UpdateWhere<>();
        uw.eq("ENROLL_ID", id);
        uw.set("ENROLL_JOIN_CNT", cnt);
        enrollMapper.edit(uw);
    }

    /**
     * 按天获取报名项目
     */
    public List<Map<String, Object>> getEnrollListByDay(String day) {
        long start = TimeHelper.time2Timestamp(day + " 00:00:00");
        long end = TimeHelper.time2Timestamp(day + " 23:59:59");

        Where<EnrollModel> where = new Where<>();
        where.eq("ENROLL_STATUS", EnrollModel.STATUS.NORMAL);
        where.between("ENROLL_START", start, end);

        where.orderByAsc("ENROLL_ORDER");
        where.orderByDesc("ENROLL_ID");

        String fields = "ENROLL_ID,ENROLL_TITLE,ENROLL_START,ENROLL_OBJ";

        List<Map<String, Object>> list = enrollMapper.getAllListMap(where, fields);

        for (Map<String, Object> record : list) {
            FormHelper.fmtDBObj(record, "enrollObj", "cover");
            record.put("timeDesc", TimeHelper.timestamp2Time(MapUtil.getLong(record, "enrollStart"), "HH:mm"));
        }

        return list;
    }

    /**
     * 获取从某天开始可报名的日期
     *
     * @param {*} fromDay  日期 Y-M-D
     */
    public List<Map<String, Object>> getEnrollHasDaysFromDay(String fromDay) {
        long start = TimeHelper.time2Timestamp(fromDay + " 00:00:00");


        Where<EnrollModel> where = new Where<>();
        where.eq("ENROLL_STATUS", EnrollModel.STATUS.NORMAL);
        where.ge("ENROLL_START", start);


        String fields = "ENROLL_START";

        List ret = new ArrayList();

        List<EnrollModel> list = enrollMapper.getAllList(where, fields);
        for (EnrollModel enrollModel : list) {
            String day = TimeHelper.timestamp2Time(enrollModel.getEnrollStart(), "yyyy-MM-dd");
            if (!ret.contains(day)) ret.add(day);
        }

        return ret;
    }
}
