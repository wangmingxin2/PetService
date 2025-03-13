package com.wang.petService;

import com.wang.petService.controller.ChatMessagesController;
import com.wang.petService.utils.AdminContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetServiceApplicationTests {

    @Test
    void contextLoads() {
        System.out.println(new ChatMessagesController().getUserConversations(1));
    }

}
