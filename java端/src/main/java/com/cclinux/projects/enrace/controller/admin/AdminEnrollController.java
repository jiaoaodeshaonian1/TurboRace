package com.cclinux.projects.enrace.controller.admin;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ObjectUtil;
import com.cclinux.framework.annotation.DemoShow;
import com.cclinux.framework.core.domain.ApiResult;
import com.cclinux.framework.core.domain.PageParams;
import com.cclinux.framework.core.domain.PageResult;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.framework.helper.TimeHelper;
import com.cclinux.framework.validate.DataCheck;
import com.cclinux.projects.enrace.model.EnrollModel;
import com.cclinux.projects.enrace.service.admin.AdminEnrollService;
import com.cclinux.projects.enrace.service.cust.EnrollService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController("EnRaceAdminEnrollController")
public class AdminEnrollController extends com.cclinux.projects.enrace.controller.admin.BaseMyAdminController {

    @Resource(name = "EnRaceAdminEnrollService")
    private AdminEnrollService adminEnrollService;

    @Resource(name = "EnRaceEnrollService")
    private EnrollService enrollService;


    @RequestMapping(value = "/admin/enroll/list")
    public ApiResult getAdminEnrollList(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = PageParams.PAGE_CHECK_RULE;
        DataCheck.check(input, RULES);

        // 业务
        PageParams pageParams = new PageParams(input);
        PageResult ret = adminEnrollService.getAdminEnrollList(pageParams);

        List<Map<String, Object>> list = ret.getList();

        for (Map<String, Object> record : list) {

            record.put("statusDesc", enrollService.getEnrollStatusDesc(record));

            FormHelper.removeField(record, "enrollForms");
            FormHelper.removeField(record, "enrollObj");

            TimeHelper.db2Time(record, "enrollStart", "yyyy-MM-dd HH:mm");
            TimeHelper.db2Time(record, "enrollEnd", "yyyy-MM-dd HH:mm");
        }

        return ApiResult.success(ret);
    }

    @RequestMapping(value = "/admin/enroll/join/list")
    public ApiResult getAdminEnrollJoinList(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "enrollId:must|int",
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
        PageResult ret = adminEnrollService.getAdminEnrollJoinList(pageParams);

        List<Map<String, Object>> list = ret.getList();

        for (Map<String, Object> record : list) {

            FormHelper.db2Forms(record, "userForms");
            FormHelper.removeField(record, "userObj");
        }

        return ApiResult.success(ret);
    }

    @DemoShow
    @RequestMapping(value = "/admin/enroll/del")
    public ApiResult delEnroll(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "id:must|long"
        };
        DataCheck.check(input, RULES);

        // 业务
        long enrollId = MapUtil.getLong(input, "id");
        adminEnrollService.delEnroll(enrollId);

        return ApiResult.success();
    }

    @DemoShow
    @RequestMapping(value = "/admin/enroll/join/del")
    public ApiResult delEnrollJoin(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "enrollJoinId:must|long"
        };
        DataCheck.check(input, RULES);

        // 业务
        long enrollJoinId = MapUtil.getLong(input, "enrollJoinId");
        adminEnrollService.delEnrollJoin(enrollJoinId);

        return ApiResult.success();
    }


    @DemoShow
    @RequestMapping(value = "/admin/enroll/status")
    public ApiResult statusEnroll(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "id:must|long",
                "status:must|int|name=状态"
        };
        DataCheck.check(input, RULES);

        // 业务
        long id = MapUtil.getLong(input, "id");
        int status = MapUtil.getInt(input, "status");
        adminEnrollService.statusEnroll(id, status);

        Map<String, Object> ret = new HashMap<>();
        ret.put("statusDesc", enrollService.getEnrollStatusDesc(id));

        return ApiResult.success(ret);
    }

    @DemoShow
    @RequestMapping(value = "/admin/enroll/order")
    public ApiResult orderEnroll(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "id:must|long",
                "order:must|int|name=排序号"
        };
        DataCheck.check(input, RULES);

        // 业务
        long id = MapUtil.getLong(input, "id");
        int order = MapUtil.getInt(input, "order");
        adminEnrollService.orderEnroll(id, order);

        return ApiResult.success();
    }

    @DemoShow
    @RequestMapping(value = "/admin/enroll/vouch")
    public ApiResult vouchEnroll(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "id:must|long",
                "vouch:must|int"
        };
        DataCheck.check(input, RULES);

        // 业务
        long id = MapUtil.getLong(input, "id");
        int vouch = MapUtil.getInt(input, "vouch");
        adminEnrollService.vouchEnroll(id, vouch);

        return ApiResult.success();
    }

    @DemoShow
    @RequestMapping(value = "/admin/enroll/insert")
    public ApiResult insertEnroll(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "title:must|string|min=4|max=50|name=标题",
                "cateId:must|long|name=分类",
                "cateName:must|string|name=分类名",
                "order:must|int|min=0|max=9999|name=排序号",
                "maxCnt:must|int|name=人数上限",
                "start:must|string|name=活动开始时间",
                "end:must|string|name=活动结束时间",
                "forms:must|string|name=表单",
                "obj:must|string|name=表单",
        };
        DataCheck.check(input, RULES);

        // 业务
        EnrollModel enroll = this.inputToEnroll(input);

        long id = adminEnrollService.insertEnroll(enroll);


        return ApiResult.success(MapUtil.of("id", id));
    }

    @DemoShow
    @RequestMapping(value = "/admin/enroll/edit")
    public ApiResult editEnroll(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "id:must|long",
                "title:must|string|min=4|max=50|name=标题",
                "cateId:must|long|name=分类",
                "cateName:must|string|name=分类名",
                "order:must|int|min=0|max=9999|name=排序号",
                "maxCnt:must|int|name=人数上限",
                "start:must|string|name=活动开始时间",
                "end:must|string|name=活动结束时间",
                "forms:must|string|name=表单",
                "obj:must|string|name=表单",
        };
        DataCheck.check(input, RULES);

        // 业务
        EnrollModel enroll = this.inputToEnroll(input);

        adminEnrollService.editEnroll(enroll);

        String statusDesc = enrollService.getEnrollStatusDesc(enroll.getEnrollId());

        return ApiResult.success(MapUtil.of("statusDesc", statusDesc));
    }


    @RequestMapping(value = "/admin/enroll/detail")
    public ApiResult getEnrollDetail(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "id:must|id|name=id"
        };
        DataCheck.check(input, RULES);

        // 业务
        long id = MapUtil.getLong(input, "id");
        Map<String, Object> ret = adminEnrollService.getEnrollDetail(id);
        if (ObjectUtil.isNotEmpty(ret)) {
            TimeHelper.db2Time(ret, "enrollStart", "yyyy-MM-dd HH:mm");
            TimeHelper.db2Time(ret, "enrollEnd", "yyyy-MM-dd HH:mm");

        }


        return ApiResult.success(ret);
    }

    @RequestMapping(value = "/admin/enroll/join/export")
    public ApiResult exportEnrollJoinExcel(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "enrollId:must|id|name=id",
                "fmt:must|string|name=格式"
        };
        DataCheck.check(input, RULES);

        // 业务
        long enrollId = MapUtil.getLong(input, "enrollId");
        String fmt = MapUtil.getStr(input, "fmt");
        Map<String, Object> ret = adminEnrollService.exportEnrollJoinExcel(enrollId, fmt);

        return ApiResult.success(ret);
    }

    @DemoShow
    @RequestMapping(value = "/admin/enroll/join/clear")
    public ApiResult clearEnrollAll(@RequestBody Map<String, Object> input) {

        // 数据校验
        String[] RULES = {
                "enrollId:must|id|name=id"
        };
        DataCheck.check(input, RULES);

        // 业务
        long enrollId = MapUtil.getLong(input, "enrollId");
        adminEnrollService.clearEnrollAll(enrollId);

        return ApiResult.success();
    }

    private EnrollModel inputToEnroll(Map<String, Object> input) {
        EnrollModel enroll = new EnrollModel();

        enroll.setEnrollId(MapUtil.getLong(input, "id"));

        enroll.setEnrollTitle(MapUtil.getStr(input, "title"));
        enroll.setEnrollCateId(MapUtil.getLong(input, "cateId"));
        enroll.setEnrollCateName(MapUtil.getStr(input, "cateName"));
        enroll.setEnrollOrder(MapUtil.getInt(input, "order"));


        enroll.setEnrollMaxCnt(MapUtil.getLong(input, "maxCnt"));

        String start = MapUtil.getStr(input, "start") + ":00";
        enroll.setEnrollStart(TimeHelper.time2Timestamp(start));

        String end = MapUtil.getStr(input, "end") + ":00";
        enroll.setEnrollEnd(TimeHelper.time2Timestamp(end));

        enroll.setEnrollForms(MapUtil.getStr(input, "forms"));
        enroll.setEnrollObj(MapUtil.getStr(input, "obj"));

        return enroll;
    }

}
