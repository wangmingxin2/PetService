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
@TableName("pets")
public class Pet implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "pet_id", type = IdType.AUTO)
    private Integer petId;

    private Integer userId;

    private String name;

    private String type;

    private String breed;

    private Integer age;

    private BigDecimal weight;

    private String imageUrl;

    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createdTime;
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updatedTime;
    @TableField(fill = FieldFill.INSERT)
    private String createdBy;
    @TableField(fill = FieldFill.UPDATE)
    private String updatedBy;


}
