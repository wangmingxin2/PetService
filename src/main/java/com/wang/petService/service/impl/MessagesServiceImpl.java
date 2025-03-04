package com.wang.petService.service.impl;

import com.wang.petService.pojo.Messages;
import com.wang.petService.mapper.MessagesMapper;
import com.wang.petService.service.IMessagesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wmx
 * @since 2025-02-16
 */
@Service
public class MessagesServiceImpl extends ServiceImpl<MessagesMapper, Messages> implements IMessagesService {
    @Override
    public int getUnreadCount() {
        return Math.toIntExact(lambdaQuery().eq(Messages::getIsRead, false).count());
    }

    @Override
    public boolean markAllAsRead() {
        return lambdaUpdate().set(Messages::getIsRead, true).update();
    }
}
