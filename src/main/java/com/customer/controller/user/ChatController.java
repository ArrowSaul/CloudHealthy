package com.customer.controller.user;

import com.customer.entity.Chat;
import com.customer.service.ChatService;
import com.customer.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user/chat")
@Slf4j
public class ChatController {

    @Autowired
    private ChatService chatService;

    /**
     * 获取订单的聊天记录
     */
    @GetMapping("/history/{orderId}")
    public Result<List<Chat>> getChatHistory(@PathVariable Long orderId) {
        List<Chat> chatList = chatService.getChatsByOrderId(orderId);
        return Result.success(chatList);
    }

    /**
     * 标记消息为已读
     */
    @PutMapping("/read/{chatId}")
    public Result<String> markAsRead(@PathVariable Long chatId) {
        chatService.markAsRead(chatId);
        return Result.success();
    }

    /**
     * 获取未读消息数量
     */
    @GetMapping("/unread")
    public Result<Integer> getUnreadCount(@RequestParam Long orderId, @RequestParam Integer role) {
        int count = chatService.getUnreadCount(orderId, role);
        return Result.success(count);
    }
} 