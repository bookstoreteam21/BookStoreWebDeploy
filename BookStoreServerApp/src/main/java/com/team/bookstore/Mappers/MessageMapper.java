package com.team.bookstore.Mappers;

import com.team.bookstore.Dtos.Requests.MessageRequest;
import com.team.bookstore.Dtos.Responses.MessageResponse;
import com.team.bookstore.Entities.Message;
import com.team.bookstore.Entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MessageMapper {
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "message_status",ignore = true)
    @Mapping(target = "createAt",ignore = true)
    @Mapping(target = "updateAt",ignore = true)
    @Mapping(target = "createBy",ignore = true)
    @Mapping(target = "updateBy",ignore = true)
    @Mapping(target = "sender",ignore = true)
    @Mapping(target = "receiver",source = "receiver_id",qualifiedByName =
            "toReceiver")
    Message toMessage(MessageRequest messageRequest);
    @Named("toReceiver")
    default User toReceiver(int receiver_id){
        User user = new User();
        user.setId(receiver_id);
        return user;
    }
    @Mapping(target = "sender_id",source = "sender",qualifiedByName =
            "toSender_id")
    @Mapping(target = "receiver_id",source = "receiver",qualifiedByName =
            "toReceiver_id")
    @Mapping(target = "sender_name",source = "sender", qualifiedByName =
            "toSenderName")
    @Mapping(target = "receiver_name",source = "receiver",qualifiedByName =
            "toReceiverName")
    @Mapping(target = "sender_avatar",source = "sender",qualifiedByName =
            "toSenderAvatar")
    @Mapping(target = "receiver_avatar",source = "receiver",qualifiedByName =
            "toReceiverAvatar")
    MessageResponse toMessageResponse(Message message);
    @Named("toSender_id")
    default int toSender_id(User sender){
        return sender.getId();
    }
    @Named("toReceiver_id")
    default int toReceiver_id(User receiver){
        return receiver.getId();
    }
    @Named("toSenderName")
    default String toSenderName(User sender){
        if(sender.getCustomer_information() !=null){
            return sender.getCustomer_information().getFullname();
        } else if(sender.getStaff_information()!=null){
            return sender.getStaff_information().getFullname();
        } else{
            return null;
        }
    }
    @Named("toReceiverName")
    default String toReceiverName(User receiver){
        if(receiver.getCustomer_information() !=null){
            return receiver.getCustomer_information().getFullname();
        } else if(receiver.getStaff_information()!=null){
            return receiver.getStaff_information().getFullname();
        } else{
            return null;
        }
    }
    @Named("toSenderAvatar")
    default byte[] toSenderAvatar(User sender){
        if(sender.getCustomer_information() !=null){
            return sender.getCustomer_information().getAvatar();
        } else if(sender.getStaff_information()!=null){
            return sender.getStaff_information().getAvatar();
        } else{
            return null;
        }
    }
    @Named("toReceiverAvatar")
    default byte[] toReceiverAvatar(User receiver){
        if(receiver.getCustomer_information() !=null){
            return receiver.getCustomer_information().getAvatar();
        } else if(receiver.getStaff_information()!=null){
            return receiver.getStaff_information().getAvatar();
        } else{
            return null;
        }
    }
}
