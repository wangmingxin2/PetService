package com.wang.petService.controller;

/**
 * @Author wmx
 * @Date 2025/4/11 9:29
 */
import com.wang.petService.pojo.AIMessage;
import com.wang.petService.utils.AIModelService;
import com.wang.petService.utils.RedisUtil;
import com.wang.petService.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ai")
@RequiredArgsConstructor
public class AIModelController {

    private final AIModelService aiModelService;

    private final RedisUtil redisUtil;
    private final RedisTemplate<String, Object> redisTemplate;

    @PostMapping("/chat")
    public Result chat(@RequestBody Map<String, String> request) {
        String prompt = request.getOrDefault("prompt", "");
        if (prompt.isEmpty()) {
            return Result.error("提示信息不能为空");
        }
        List<AIMessage> history = (List<AIMessage>) redisUtil.get(request.get("userId"));
        System.out.println("获取前"+history);
        if (history == null) history = new ArrayList<>();

        // 添加新消息
        history.add(new AIMessage("user", prompt, LocalDateTime.now()));
        //拼接上下文
        // 调用 AI 模型服务生成响应
        String response = aiModelService.generateResponse(prompt);

        Map<String, String> result = new HashMap<>();
        result.put("response", response);
        // 添加 AI 模型的响应消息
        history.add(new AIMessage("ai", response, LocalDateTime.now()));
        redisUtil.set(request.get("userId"), history, -1);
        System.out.println("获取后"+history);
        return Result.success(result);
    }
    @GetMapping("/history")
    public Result<List<AIMessage>> getHistory(@RequestParam String userId) {
        List<AIMessage> history = (List<AIMessage>) redisUtil.get(userId);
        if (history == null) {
            return Result.success(new ArrayList<>());
        }
        return Result.success(history);
    }
    @DeleteMapping("/clear")
    public Result clearHistory(@RequestParam String userId) {
        redisTemplate.delete(userId);
        return Result.success("历史记录已清除");
    }
}