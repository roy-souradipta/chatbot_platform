package com.chatbot.controllers;

import com.chatbot.dtos.ChatMessageRequestDto;
import com.chatbot.dtos.ChatResponseDto;
import com.chatbot.exception.InvalidRequestException;
import com.chatbot.exception.InvalidSessionException;
import com.chatbot.exception.JsonParsingException;
import com.chatbot.service.client.ChatRequestAndResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/clientChatMessage")
public class ChatController {


    @Autowired
    private ChatRequestAndResponseHandler chatRequestAndResponseHandler;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Void> acceptClientChatRequest(@RequestBody @Valid ChatMessageRequestDto chatRequestDto,
                                                        HttpServletRequest request){
        String chatMessageId = chatRequestAndResponseHandler.handleChatRequest(chatRequestDto);
        return ResponseEntity.accepted().header("poll_location", request.getRequestURI()
                .concat("/").concat(chatMessageId)).build();
    }

    @RequestMapping(value= "/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<ChatResponseDto> getChatResponse(@PathVariable String messageId){
        return ResponseEntity.ok().body(chatRequestAndResponseHandler.getChatResponse(messageId));
    }

    @ExceptionHandler
    public void handleDataSetWhenEmpty(EmptyResultDataAccessException exception, HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.NOT_FOUND.value());
    }

    @ExceptionHandler
    public void handleInvalidRequest(InvalidRequestException exception, HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler
    public void handleInvalidSession(InvalidSessionException exception, HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }

    @ExceptionHandler
    public void handleErrorInJsonParsingSession(JsonParsingException exception, HttpServletResponse response) throws IOException{
        response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value());
    }
}
