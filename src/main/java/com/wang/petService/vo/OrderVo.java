package com.wang.petService.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

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
public class OrderVo implements Serializable {
    private Integer orderId;

    private String userName;

    private String serviceName;

    private String petName;

    private String serviceProviderName;

    private Integer orderAmount ;

    private String orderStatus;

    private LocalDateTime scheduledTime;

    private LocalDateTime completedTime;

    private LocalDateTime createdTime;

    private LocalDateTime updatedTime;

    private String createdBy;

    private String updatedBy;

}
