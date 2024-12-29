package com.cclinux.projects.enrace.service.admin;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
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
import com.cclinux.framework.helper.FileHelper;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.projects.enrace.comm.ProjectConfig;
import com.cclinux.projects.enrace.mapper.UserMapper;
import com.cclinux.projects.enrace.model.UserModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Notes: 用户管理业务逻辑
 * @Author: cclinux0730 (weixin)
 * @Date: 2024/12/15 12:10
 * @Ver: ccminicloud-framework 3.2.1
 */


@Service("EnRaceAdminUserService")
public class AdminUserService extends BaseMyAdminService {


    @Resource(name = "EnRaceUserMapper")
    private UserMapper userMapper;


    /**
     * 用户列表
     */
    public PageResult getAdminUserList(PageParams pageRequest) {

        Where<UserModel> where = new Where<>();

        // 关键字查询
        String search = pageRequest.getSearch();
        if (StrUtil.isNotEmpty(search)) {
            where.and(wrapper -> {
                wrapper.or().like("USER_NAME", search);
                wrapper.or().like("USER_ACCOUNT", search);
            });
        }

        // 条件查询
        String sortType = pageRequest.getSortType();
        String sortVal = pageRequest.getSortVal();
        if (StrUtil.isNotEmpty(sortType) && StrUtil.isNotEmpty(sortVal)) {
            switch (sortType) {
                case "status": {
                    where.eq("USER_STATUS", Convert.toInt(sortVal));
                    break;
                }
                case "sort": {
                    where.fmtOrderBySort(sortVal, "");
                    break;
                }
            }

        }

        // 排序
        where.orderByDesc("USER_ID");


        Page page = new Page(pageRequest.getPage(), pageRequest.getSize());
        return userMapper.getPageList(page, where, "*");
    }

    /**
     * 删除用户
     */
    public void delUser(long id) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }


    /**
     * 获取单个用户
     */
    public Map<String, Object> getUserDetail(long id) {
        return userMapper.getOneMap(id);
    }

    /**
     * 修改用户状态
     */
    public void statusUser(long id, int status) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }


    /**
     * 重置用户密码
     */
    public void editUserPwd(long id, String pwd) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
    }


    /**
     * 导出名单
     */
    public Map<String, Object> exportUserJoinExcel(String fmt) {
        appError("{校园比赛}该功能暂不开放，如有需要请加作者微信：cclinux0730");
        return null;
    }


}
