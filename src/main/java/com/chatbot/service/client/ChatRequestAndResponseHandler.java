package com.chatbot.service.client;

import com.chatbot.constants.AppConstants;
import com.chatbot.dtos.ChatMessageRequestDto;
import com.chatbot.dtos.ChatResponseDto;
import com.chatbot.dtos.NLPRequestDto;
import com.chatbot.entities.ChatConversation;
import com.chatbot.entities.ChatSession;
import com.chatbot.entities.PersistentMessage;
import com.chatbot.exception.InvalidRequestException;
import com.chatbot.exception.InvalidSessionException;
import com.chatbot.repository.ChatConversationRepository;
import com.chatbot.repository.ChatMessageRepository;
import com.chatbot.repository.ChatSessionRepository;
import com.chatbot.service.nlp.NLPMessageSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class ChatRequestAndResponseHandler {

    public static final String DEFAULT_SESSION_ID = "b64b414b-4ece-48a3-b1ce-a29c9239240a";

    @Autowired
    private ChatSessionRepository chatSessionRepository;

    @Autowired
    private ChatConversationRepository chatConversationRepository;

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    @Autowired
    private NLPMessageSender nlpMessageSender;

    public String handleChatRequest(ChatMessageRequestDto chatRequestDto) {
        Instant currentInstant = Instant.now();
        String sessionId = retrieveActiveSession(chatRequestDto, currentInstant);
        chatRequestDto.setSessionId(sessionId);
        return persistMessageAndSendToNLP(chatRequestDto, currentInstant);
    }

    public ChatResponseDto getChatResponse(String messageId) {
        ChatConversation chatConversation;
        PersistentMessage persistentMessage = chatMessageRepository.getChatMessage(UUID.fromString(messageId));
        UUID conversationId = persistentMessage.getConversationId();
        if (persistentMessage.getConversationId() != null) {
            chatConversation = chatConversationRepository.getConversation(conversationId);
            return ChatResponseDto.from(chatConversation, persistentMessage);
        }
        return ChatResponseDto.from(persistentMessage);
    }

    private String persistMessageAndSendToNLP(ChatMessageRequestDto chatRequestDto, Instant currentInstant) {
        UUID newMessageId = UUID.randomUUID();
        NLPRequestDto nlpRequestDto;
        PersistentMessage persistentMessage = chatRequestDto.toPersistentMessage(currentInstant, newMessageId);
        if(messagePartOfAnExistingConversation(chatRequestDto)){
            ChatConversation chatConversation = getCurrentConversation(chatRequestDto);
            persistentMessage.setConversationId(chatConversation.getId());
            nlpRequestDto = NLPRequestDto.from(persistentMessage, chatConversation);
        }else{
            nlpRequestDto = NLPRequestDto.from(persistentMessage);
        }
        chatMessageRepository.saveMessage(persistentMessage);
        nlpMessageSender.sendRequestMessageToNlp(nlpRequestDto);
        return newMessageId.toString();
    }

    private ChatConversation getCurrentConversation(ChatMessageRequestDto chatRequestDto) {
        return chatConversationRepository.getConversation(
                UUID.fromString(chatRequestDto.getConversationId()));
    }

    private boolean messagePartOfAnExistingConversation(ChatMessageRequestDto chatRequestDto) {
        return Optional.ofNullable(chatRequestDto.getConversationId()).isPresent();
    }

    private String retrieveActiveSession(ChatMessageRequestDto chatRequestDto, Instant currentInstant) {
        String sessionId = chatRequestDto.getSessionId();
        if (sessionId == null){
            throw new InvalidRequestException("Invalid SessionId");
        };
        if(sessionId.equals(DEFAULT_SESSION_ID)){
            sessionId = createNewSession(currentInstant, chatRequestDto);
        }else if(!activeSession(sessionId, currentInstant)){
            throw new InvalidSessionException("Session Does not exist or not valid");
        }
        return sessionId;
    }

    private boolean activeSession(String sessionId, Instant currentInstant) {
        ChatSession chatSession = chatSessionRepository.getSessionData(UUID.fromString(sessionId));
        return currentInstant.isAfter(chatSession.getStartTime().toInstant())
                && (chatSession.getEndTime() == null || currentInstant.isBefore(chatSession.getEndTime().toInstant()));
    }

    private String createNewSession(Instant currentInstant, ChatMessageRequestDto chatRequestDto) {
        UUID newSessionId = UUID.randomUUID();
        ChatSession newChatSession = new ChatSession(newSessionId,
                Timestamp.from(currentInstant),
                Timestamp.from(currentInstant.plusMillis(
                        AppConstants.appTimeoutInMilliseconds)), chatRequestDto.getUserId(),
                chatRequestDto.getBotAppId(), "true");
        chatSessionRepository.saveNewSession(newChatSession);
        return newSessionId.toString();
    }
}
