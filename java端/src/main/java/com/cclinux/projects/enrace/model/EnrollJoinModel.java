package com.cclinux.projects.enrace.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cclinux.framework.core.model.BaseModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Notes: 活动报名实体
 * @Author: cclinux0730 (weixin)
 * @Date: 2024/11/30 7:18
 * @Ver: ccminicloud-framework 3.2.1
 */

@Data
@EqualsAndHashCode(callSuper = true)
@TableName("enrace_enroll_join")
public class EnrollJoinModel extends BaseModel {
    /**
     * 状态
     */
    public static final class STATUS {
        public static final int STOP = 0; //非正常
        public static final int NORMAL = 1; // 正常
    }


    @TableId(value = "ENROLL_JOIN_ID", type = IdType.AUTO)
    private Long enrollJoinId;

    @TableField("ENROLL_JOIN_ENROLL_ID")
    private long enrollJoinEnrollId;

    @TableField("ENROLL_JOIN_USER_ID")
    private long enrollJoinUserId;


    @TableField("ENROLL_JOIN_STATUS")
    private int enrollJoinStatus;


}
