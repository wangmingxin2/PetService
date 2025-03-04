package com.wang.petService.service;

import com.wang.petService.pojo.Messages;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wmx
 * @since 2025-02-16
 */
public interface IMessagesService extends IService<Messages> {

    int getUnreadCount();

    boolean markAllAsRead();
}
