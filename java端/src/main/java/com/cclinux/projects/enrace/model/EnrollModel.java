package com.cclinux.projects.enrace.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cclinux.framework.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Notes: 报名实体
 * @Author: cclinux0730 (weixin) 
 * @Ver: ccminicloud-framework 3.2.1
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("enrace_enroll")
public class EnrollModel extends BaseModel {
    /**
     * 状态
     */
    public static final class STATUS {
        public static final int STOP = 0; //非正常
        public static final int NORMAL = 1; // 使用中
    }

    @TableId(value = "ENROLL_ID", type = IdType.AUTO)
    private Long enrollId;

    @TableField("ENROLL_TITLE")
    private String enrollTitle;

    @TableField("ENROLL_STATUS")
    private int enrollStatus;

    @TableField("ENROLL_CATE_ID")
    private long enrollCateId;

    @TableField("ENROLL_CATE_NAME")
    private String enrollCateName;

    // 最大人数
    @TableField("ENROLL_MAX_CNT")
    private long enrollMaxCnt;

    // 开始时间
    @TableField("ENROLL_START")
    private long enrollStart;

    // 结束时间
    @TableField("ENROLL_END")
    private long enrollEnd;

    @TableField("ENROLL_ORDER")
    private int enrollOrder;

    @TableField("ENROLL_VOUCH")
    private int enrollVouch;

    @TableField("ENROLL_VIEW_CNT")
    private long enrollViewCnt;

    @TableField("ENROLL_JOIN_CNT")
    private long enrollJoinCnt;

    @TableField("ENROLL_FORMS")
    private String enrollForms;

    @TableField("ENROLL_OBJ")
    private String enrollObj;



}
