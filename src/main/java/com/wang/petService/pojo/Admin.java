package com.wang.petService.pojo;

import com.baomidou.mybatisplus.annotation.*;

import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author wmx
 * @since 2025-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("admin")
@ApiModel(value="Admin对象", description="")
public class Admin implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "admin_id", type = IdType.AUTO)
    private Integer adminId;

    private String username;

    private String password;

    private String email;

    private String role;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;


}
