package com.revconnect.service;

import java.util.List;
import com.revconnect.dao.MessageDAO;
import com.revconnect.model.Message;

public class MessageService {

    private MessageDAO dao = new MessageDAO();

    // ✅ Send message
    public boolean sendMessage(int senderId, int receiverId, String message) {
        return dao.sendMessage(senderId, receiverId, message);
    }

    // ✅ View chat between two users
    public List<Message> viewChat(int user1, int user2) {
        return dao.getChat(user1, user2);
    }

    // ✅ Alias (for compatibility)
    public List<Message> getMessagesBetweenUsers(int user1, int user2) {
        return dao.getChat(user1, user2);
    }

    // ✅ Mark messages as read
    public void markRead(int senderId, int receiverId) {
        dao.markAsRead(senderId, receiverId);
    }

    // ✅ Alias (for compatibility)
    public void markMessagesAsRead(int receiverId, int senderId) {
        dao.markAsRead(senderId, receiverId);
    }

    // ✅ Delete chat
    public boolean deleteChat(int user1, int user2) {
        return dao.deleteConversation(user1, user2);
    }

    // ✅ Alias
    public boolean deleteConversation(int user1, int user2) {
        return dao.deleteConversation(user1, user2);
    }

    // ✅ Block user
    public boolean blockUser(int blockerId, int blockedId) {
        return dao.blockUser(blockerId, blockedId);
    }
}
