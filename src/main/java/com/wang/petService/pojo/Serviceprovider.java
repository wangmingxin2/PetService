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
@TableName("serviceproviders")
@ApiModel(value="Serviceproviders对象", description="")
public class Serviceprovider implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "service_provider_id", type = IdType.AUTO)
    private Integer serviceProviderId;

    private String name;

    private String phone;

    private String email;

    private String address;

    private BigDecimal rating;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private String createdBy;

    private String updatedBy;


}
