package com.cclinux.projects.enrace.service.cust;

import com.cclinux.framework.core.mapper.Where;
import com.cclinux.framework.helper.FormHelper;
import com.cclinux.framework.helper.TimeHelper;
import com.cclinux.projects.enrace.mapper.EnrollMapper;
import com.cclinux.projects.enrace.mapper.NewsMapper;
import com.cclinux.projects.enrace.model.EnrollModel;
import com.cclinux.projects.enrace.model.NewsModel;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Notes: 首页
 * @Author: cclinux0730 (weixin)
 * @Ver: ccminicloud-framework 3.2.1
 */

@Service("EnRaceHomeService")
public class HomeService extends BaseMyCustService {

    @Resource(name = "EnRaceNewsMapper")
    private NewsMapper newsMapper;

    @Resource(name = "EnRaceEnrollMapper")
    private EnrollMapper enrollMapper;

    /**
     * 首页列表
     */
    public Map<String, Object> getHomeList() {
        Map<String, Object> ret = new HashMap<>();

        Where<NewsModel> whereNews = new Where<>();
        whereNews.eq("NEWS_VOUCH",1);
        whereNews.orderByAsc("NEWS_ORDER");
        whereNews.orderByDesc("NEWS_ID");
        List<Map<String, Object>> newsList = newsMapper.getAllListMap(whereNews, "NEWS_TITLE,NEWS_OBJ,NEWS_ID," +
                "NEWS_CATE_NAME,ADD_TIME");

        for (Map<String, Object> record : newsList) {
            FormHelper.fmtDBObj(record, "newsObj", "cover,desc");
            TimeHelper.fmtDBTime(record,"addTime","yyyy-MM-dd");
        }

        ret.put("newsList", newsList);

        Where<EnrollModel> whereEnroll = new Where<>();
        whereEnroll.eq("ENROLL_VOUCH",1);
        whereEnroll.orderByAsc("ENROLL_ORDER");
        whereEnroll.orderByDesc("ENROLL_ID");
        List<Map<String, Object>> enrollList = enrollMapper.getAllListMap(whereEnroll, "ENROLL_TITLE," +
                "ENROLL_JOIN_CNT," +
                "ENROLL_OBJ," +
                "ENROLL_ID," +
                "ENROLL_CATE_NAME, ADD_TIME");

        for (Map<String, Object> record : enrollList) {
            FormHelper.fmtDBObj(record, "enrollObj", "cover");
        }

        ret.put("enrollList", enrollList);

        return ret;
    }

}
