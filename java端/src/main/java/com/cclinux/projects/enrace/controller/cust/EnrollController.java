package com.cclinux.projects.enrace.controller.cust;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cclinux.framework.annotation.LoginIgnore;
import com.cclinux.framework.core.domain.ApiResult;
import com.cclinux.framework.core.domain.PageParams;
import com.cclinux.framework.core.domain.PageResult;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.framework.helper.TimeHelper;
import com.cclinux.framework.validate.DataCheck;
import com.cclinux.projects.enrace.service.cust.EnrollService;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Notes: 报名模块
 * @Author: cclinux0730 (weixin)
 * @Date: 2024/3/7 5:57
 * @Ver: ccminicloud-framework 3.2.1
 */


@RestController("EnRaceEnrollController")
public class EnrollController extends BaseMyCustController {

    @Resource(name = "EnRaceEnrollService")
    EnrollService enrollService;

    @LoginIgnore
    @RequestMapping(value = "/enroll/view")
    public ApiResult view(@RequestBody Map<String, Object> input, @RequestAttribute long userId) {

        // 数据校验
        String[] RULES = {
                "id:must|id|name=id"
        };
        DataCheck.check(input, RULES);


        // 业务
        long id = MapUtil.getLong(input, "id");
        Map<String, Object> ret = enrollService.view(id, userId);


        if (ObjectUtil.isNotEmpty(ret)) {

            String start = TimeHelper.timestamp2Time(MapUtil.getLong(ret, "enrollStart"), "yyyy-MM-dd HH:mm");
            String end = TimeHelper.timestamp2Time(MapUtil.getLong(ret, "enrollEnd"), "yyyy-MM-dd HH:mm");
            String time = start + " ~ " + end;
            ret.put("time", time);

            TimeHelper.db2Time(ret, "enrollStop", "yyyy-MM-dd HH:mm");

            ret.put("statusDesc", enrollService.getEnrollStatusDesc(id));
            ret.remove("enrollForms");

        }


        return ApiResult.success(ret);
    }

    @RequestMapping(value = "/enroll/detail/for/join")
    public ApiResult detailForEnrollJoin(@RequestBody Map<String, Object> input, @RequestAttribute long userId) {

        // 数据校验
        String[] RULES = {
                "enrollId:must|id|name=id"
        };
        DataCheck.check(input, RULES);


        // 业务
        long enrollId = MapUtil.getLong(input, "enrollId");
        Map<String, Object> ret = enrollService.detailForEnrollJoin(userId, enrollId);

        return ApiResult.success(ret);
    }

    @RequestMapping(value = "/enroll/join")
    public ApiResult enrollJoin(@RequestBody Map<String, Object> input, @RequestAttribute long userId) {

        // 数据校验
        String[] RULES = {
                "enrollId:must|id|name=id"
        };
        DataCheck.check(input, RULES);


        // 业务
        long enrollId = MapUtil.getLong(input, "enrollId");
        String forms = MapUtil.getStr(input, "forms");
        String obj = MapUtil.getStr(input, "obj");

        enrollService.enrollJoin(userId, enrollId);

        return ApiResult.success();
    }

    @RequestMapping(value = "/enroll/my/join/cancel")
    public ApiResult cancelMyEnrollJoin(@RequestBody Map<String, Object> input, @RequestAttribute long userId) {

        // 数据校验
        String[] RULES = {
                "enrollJoinId:must|id|name=id"
        };
        DataCheck.check(input, RULES);


        // 业务
        long enrollJoinId = MapUtil.getLong(input, "enrollJoinId");

        enrollService.cancelMyEnrollJoin(userId, enrollJoinId);

        return ApiResult.success();
    }


    @LoginIgnore
    @RequestMapping(value = "/enroll/rank/list")
    public ApiResult getEnrollRankList(@RequestBody Map<String, Object> input) {
        // 数据校验
        String[] RULES = {
                "cateId:int",
                "search:string|name=搜索条件",
                "page:must|int|default=1",
                "size:must|int|size|default=10",
                "isTotal:bool",
                "sortType:string|name=搜索类型",
                "sortVal:string|name=搜索类型值",
                "orderBy:string|name=排序",
                "whereEx:string|name=附加查询条件",
                "oldTotal:int"
        };
        DataCheck.check(input, RULES);

        // 业务
        PageParams pageParams = new PageParams(input);
        pageParams.setSize(30L);
        PageResult ret = enrollService.getEnrollRankList(pageParams);

        List<Map<String, Object>> list = ret.getList();

        for (Map<String, Object> record : list) {
            FormHelper.fmtDBObjExclude(record, "enrollObj", "desc");

        }

        return ApiResult.success(ret);
    }

    @LoginIgnore
    @RequestMapping(value = "/enroll/list")
    public ApiResult getEnrollList(@RequestBody Map<String, Object> input) {
        // 数据校验
        String[] RULES = {
                "cateId:int",
                "search:string|name=搜索条件",
                "page:must|int|default=1",
                "size:must|int|size|default=10",
                "isTotal:bool",
                "sortType:string|name=搜索类型",
                "sortVal:string|name=搜索类型值",
                "orderBy:string|name=排序",
                "whereEx:string|name=附加查询条件",
                "oldTotal:int"
        };
        DataCheck.check(input, RULES);

        // 业务
        PageParams pageParams = new PageParams(input);
        PageResult ret = enrollService.getEnrollList(pageParams);

        List<Map<String, Object>> list = ret.getList();

        for (Map<String, Object> record : list) {
            record.put("statusDesc", enrollService.getEnrollStatusDesc(record));

            TimeHelper.db2Time(record, "enrollStart", "yyyy-MM-dd HH:mm");

            FormHelper.fmtDBObjExclude(record, "enrollObj", "desc");

        }

        return ApiResult.success(ret);
    }

    @RequestMapping(value = "/enroll/my/join/list")
    public ApiResult getMyEnrollJoinList(@RequestBody Map<String, Object> input, @RequestAttribute long userId) {
        // 数据校验
        String[] RULES = PageParams.PAGE_CHECK_RULE;
        DataCheck.check(input, PageParams.PAGE_CHECK_RULE);

        // 业务
        PageParams pageParams = new PageParams(input);
        PageResult ret = enrollService.getMyEnrollJoinList(userId, pageParams);

        List<Map<String, Object>> list = ret.getList();

        for (Map<String, Object> record : list) {
            FormHelper.fmtDBObj(record, "enrollObj");
        }

        return ApiResult.success(ret);
    }

    @RequestMapping(value = "/enroll/my/join/detail")
    public ApiResult getMyEnrollJoinDetail(@RequestBody Map<String, Object> input, @RequestAttribute long userId) {
        // 数据校验
        String[] RULES = {
                "enrollJoinId:must|id|name=id"
        };
        DataCheck.check(input, RULES);

        // 业务
        long enrollJoinId = MapUtil.getLong(input, "enrollJoinId");
        Map<String, Object> ret = enrollService.getMyEnrollJoinDetail(userId, enrollJoinId);

        if (ObjectUtil.isNotEmpty(ret)) {

            Map<String, Object> enroll = BeanUtil.beanToMap(ret.get("enroll"));
            String start = TimeHelper.timestamp2Time(MapUtil.getLong(enroll, "enrollStart"), "yyyy-MM-dd HH:mm");
            String end = TimeHelper.timestamp2Time(MapUtil.getLong(enroll, "enrollEnd"), "yyyy-MM-dd HH:mm");
            String time = start + " ~ " + end;
            ret.put("time", time);

            TimeHelper.db2Time(ret, "enrollJoinCheckTime", "yyyy-MM-dd HH:mm:ss");
        }

        return ApiResult.success(ret);
    }

    @LoginIgnore
    @RequestMapping(value = "/enroll/list/by/day")
    public ApiResult getEnrollListByDay(@RequestBody Map<String, Object> input) {
        // 数据校验
        String[] RULES = {
                "day:must|string|name=日期"
        };
        DataCheck.check(input, RULES);

        // 业务
        String day = MapUtil.getStr(input, "day");
        Object ret = enrollService.getEnrollListByDay(day);

        return ApiResult.success(ret);
    }

    @LoginIgnore
    @RequestMapping(value = "/enroll/list/has/day")
    public ApiResult getEnrollHasDaysFromDay(@RequestBody Map<String, Object> input) {
        // 数据校验
        String[] RULES = {
                "fromDay:must|string|name=日期"
        };
        DataCheck.check(input, RULES);

        // 业务
        String fromDay = MapUtil.getStr(input, "fromDay");
        Object ret = enrollService.getEnrollHasDaysFromDay(fromDay);

        return ApiResult.success(ret);
    }
}
