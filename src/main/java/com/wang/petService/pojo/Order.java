package com.wang.petService.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wmx
 * @since 2025-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("orders")
public class Order   {
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    private Integer userId;

    private Integer serviceId;

    private Integer petId;

    private Integer serviceProviderId;

    private Integer orderAmount ;

    private String orderStatus;

    private LocalDateTime scheduledTime;

    private LocalDateTime completedTime;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;

}
