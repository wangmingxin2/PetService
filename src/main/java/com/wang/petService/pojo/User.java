package com.wang.petService.pojo;

import java.math.BigDecimal;

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
@TableName("users")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "user_id", type = IdType.AUTO)
    private Integer userId;

    private String openId;

    private String name;

    private String avatarUrl;

    private String phone;

    private String email;

    private String address;

    private BigDecimal balance;

    private BigDecimal totalSpent;

    private String membershipLevel;

    private BigDecimal discountRate;

//    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
//    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;
//    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
//    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;


}
