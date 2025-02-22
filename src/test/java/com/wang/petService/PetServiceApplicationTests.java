package com.wang.petService;

import com.wang.petService.utils.AdminContext;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PetServiceApplicationTests {

    @Test
    void contextLoads() {
        AdminContext.setAdminId(1);
        System.out.println(AdminContext.getAdminId());
    }

}
