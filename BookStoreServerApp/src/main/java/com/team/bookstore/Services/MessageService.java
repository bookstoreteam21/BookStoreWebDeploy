package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.MessageResponse;
import com.team.bookstore.Entities.Message;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Enums.Object;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Exceptions.ObjectException;
import com.team.bookstore.Mappers.MessageMapper;
import com.team.bookstore.Repositories.MessageRepository;
import com.team.bookstore.Repositories.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.MessageSpecification.*;

@Service
@Log4j2
public class MessageService {
    @Autowired
    MessageRepository messageRepository;
    @Autowired
    MessageMapper messageMapper;
    @Autowired
    UserRepository userRepository;
    @Secured("ROLE_ADMIN")
    public List<MessageResponse> getAllMessages(){
        try{
            return messageRepository.findAll().stream().map(messageMapper::toMessageResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.MESSAGE.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<MessageResponse> findMessagesBy(String keyword){
        try{
            Specification<Message> spec = CreateMessageKeywordSpec(keyword);
            return messageRepository.findAll(spec).stream().map(messageMapper::toMessageResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ObjectException(keyword,
                    ErrorCodes.NOT_EXIST);
        }
    }
    public MessageResponse createMessage(Message message){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null) {
                throw new ApplicationException(ErrorCodes.UNAUTHENTICATED);
            }
            int sender_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            if(!userRepository.existsById(message.getReceiver().getId())){
                throw new ObjectException(Object.RECEIVER.getName(),
                        ErrorCodes.NOT_EXIST);
            }
            User sender =
                    userRepository.findUserById(sender_id);
            User receiver =
                    userRepository.findUserById(message.getReceiver().getId());
            message.setSender(sender);
            message.setReceiver(receiver);
            message.setMessage_status(0);
            return messageMapper.toMessageResponse(messageRepository.save(message));
        } catch (Exception e){
            log.info(e);
            throw new ObjectException(Object.MESSAGE.getName(),
                    ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public MessageResponse deleteMessage(int id){
        try{
            if(!messageRepository.existsById(id)){
                throw new ObjectException(Object.MESSAGE.getName(),
                        ErrorCodes.NOT_EXIST);
            }
            Message existMessage = messageRepository.findMessageById(id);
            messageRepository.delete(existMessage);
            return messageMapper.toMessageResponse(existMessage);
        } catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.MESSAGE.getName(),
                    ErrorCodes.CANNOT_DELETE);
        }
    }
    List<MessageResponse> findMessageByPairUsers(int user_id1,
                                                        int user_id2){
        try{
            Specification<Message> spec = CreateMessageSenderReceiverSpec(user_id1,
                    user_id2);
            return messageRepository.findAll(spec).stream().map(messageMapper::toMessageResponse).collect(Collectors.toList());
        }catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.MESSAGE.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    List<Message> findMessageOfSender(int sender_id){
        try{
            Specification<Message> spec = CreateMessageSenderSpec(sender_id);
            return new ArrayList<>(messageRepository.findAll(spec));
        } catch (Exception e){
            log.info(e);
            throw new ObjectException(Object.MESSAGE.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    Set<Integer> getReceiverIDs(List<Message> messageList){
        try{
            Set<Integer> receiver_ids = new HashSet<>();
            messageList.forEach(message -> {
                receiver_ids.add(message.getReceiver().getId());
            });
            return receiver_ids;
        }
        catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.MESSAGE.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    public Set<List<MessageResponse>> loadMyChat(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UNAUTHENTICATED);
            }
            int sender_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            Set<Integer> receiver_ids =
                    getReceiverIDs(findMessageOfSender(sender_id));
            Set<List<MessageResponse>> allMyChats = new HashSet<>();
            receiver_ids.forEach(receiver_id->{
                allMyChats.add(findMessageByPairUsers(sender_id,
                        receiver_id));
            });
            return allMyChats;
        } catch (Exception e){
            log.info(e);
            throw new ObjectException(Object.MESSAGE.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
}