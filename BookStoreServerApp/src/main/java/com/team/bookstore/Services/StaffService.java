package com.team.bookstore.Services;
import com.team.bookstore.Dtos.Responses.CustomerInformationResponse;
import com.team.bookstore.Dtos.Responses.StaffInformationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Entities.StaffInformation;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Entities.User_Role;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Enums.Object;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Exceptions.ObjectException;
import com.team.bookstore.Mappers.UserMapper;
import com.team.bookstore.Repositories.RoleRepository;
import com.team.bookstore.Repositories.StaffInformationRepository;
import com.team.bookstore.Repositories.UserRepository;
import com.team.bookstore.Repositories.User_RoleRepository;
import com.team.bookstore.Utilities.ImageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.UserSpecifications.GenerateStaffKeywordSpec;

@Service
@Log4j2
public class StaffService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    User_RoleRepository user_roleRepository;
    @Autowired
    StaffInformationRepository staffInformationRepository;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserResponse staffRegister(User user){
        try {
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication==null){
                throw new ApplicationException(ErrorCodes.UNAUTHENTICATED);
            }
            User authUser =
                    userRepository.findUsersByUsername(authentication.getName());
            if(authUser == null){
                throw new ApplicationException(ErrorCodes.UNAUTHORISED);
            }
            if(userRepository.existsByUsername(user.getUsername())){
                throw new ObjectException(user.getUsername(),
                        ErrorCodes.HAS_BEEN_EXIST);
            }
            String decodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(decodePassword);
            User_Role user_role = new User_Role();
            user_role.setUser(user);
            user_role.setRole(roleRepository.findRoleByRolename("STAFF"));
            user.getUser_role().add(user_role);
            return userMapper.toUserResponse(userRepository.save(user));
        } catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.REGISTER_FAILD);
        }
    }
    public StaffInformationResponse createStaffInformation(int id,
                                                           MultipartFile image,
                                                                 StaffInformation staffInformation){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication==null){
                throw new ApplicationException(ErrorCodes.UNAUTHENTICATED);
            }
            User authUser =
                    userRepository.findUsersByUsername(authentication.getName());
            if(authUser == null){
                throw new ApplicationException(ErrorCodes.UNAUTHORISED);
            }
            staffInformation.setAvatar(image.getBytes());
            if(!userRepository.existsById(id)){
                throw new ObjectException(Object.USER.getName(),
                        ErrorCodes.NOT_EXIST);
            }
            staffInformation.setId(id);
            if(staffInformationRepository.existsStaffInformationByEmail(staffInformation.getEmail())){
                throw new ObjectException(staffInformation.getEmail(),
                        ErrorCodes.HAS_BEEN_EXIST);
            }
            if(staffInformationRepository.existsStaffInformationByPhonenumber(staffInformation.getPhonenumber())){
                throw new ObjectException(staffInformation.getPhonenumber(),
                        ErrorCodes.HAS_BEEN_EXIST);
            }
            StaffInformation savedStaffInformation = staffInformationRepository.save(staffInformation);
            return userMapper.toStaffInformationResponse(savedStaffInformation);

        } catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.STAFFINF.getName(),
                    ErrorCodes.CANNOT_CREATE);
        }
    }
    public StaffInformationResponse getMyInfo(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UNAUTHENTICATED);
            }
            if(!userRepository.existsByUsername(authentication.getName())){
                throw new ObjectException(Object.USER.getName(),
                        ErrorCodes.NOT_EXIST);
            }
            int staff_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            return userMapper.toStaffInformationResponse(staffInformationRepository.findStaffInformationById(staff_id));
        }catch (Exception e){
            log.info(e);
            throw new ObjectException(Object.MY_INF.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    public StaffInformationResponse updateStaffInformation(int id,
                                                           MultipartFile image,
                                                                 StaffInformation staffInformation){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication==null){
                throw new ApplicationException(ErrorCodes.UNAUTHENTICATED);
            }
            User authUser =
                    userRepository.findUsersByUsername(authentication.getName());
            if(authUser == null){
                throw new ApplicationException(ErrorCodes.UNAUTHORISED);
            }
            staffInformation.setAvatar(image.getBytes());
            if(!userRepository.existsById(staffInformation.getId()) && staffInformationRepository.existsStaffInformationById(staffInformation.getId())){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            staffInformation.setId(id);
            if(staffInformationRepository.existsStaffInformationByEmail(staffInformation.getEmail())&&!staffInformationRepository.existsStaffInformationByIdAndEmail(id,staffInformation.getEmail())){
                throw new ObjectException(staffInformation.getEmail(),
                        ErrorCodes.HAS_BEEN_EXIST);
            }
            if(staffInformationRepository.existsStaffInformationByPhonenumber(staffInformation.getPhonenumber())&&!staffInformationRepository.existsStaffInformationByIdAndPhonenumber(id,staffInformation.getPhonenumber())){
                throw new ObjectException(staffInformation.getPhonenumber(),
                        ErrorCodes.HAS_BEEN_EXIST);
            }
            StaffInformation savedStaffInformation = staffInformationRepository.save(staffInformation);
            return userMapper.toStaffInformationResponse(savedStaffInformation);
        }catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.STAFFINF.getName(),
                    ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<StaffInformationResponse> getAllStaffInformation(){
        try {
            return staffInformationRepository.findAll().stream().map(userMapper::toStaffInformationResponse).collect(Collectors.toList());
        }catch (Exception e){
            log.info(e);
            throw new ObjectException(Object.STAFFINF.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<StaffInformationResponse> findStaffInformationBy(String keyword){
        try{
            Specification<StaffInformation> spec =
                    GenerateStaffKeywordSpec(keyword);
            return staffInformationRepository.findAll(spec).stream().map(userMapper::toStaffInformationResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.STAFFINF.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
}