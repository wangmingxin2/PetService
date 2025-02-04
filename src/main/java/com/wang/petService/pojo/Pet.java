package com.wang.petService.pojo;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
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
 * @since 2025-02-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("pets")
@ApiModel(value="Pets对象", description="")
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

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private String createdBy;

    private String updatedBy;


}
