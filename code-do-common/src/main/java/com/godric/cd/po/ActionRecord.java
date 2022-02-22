package com.godric.cd.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("action_record")
public class ActionRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private Long uid;

    private Integer targetType;

    private Integer actionType;

    private Long targetId;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
