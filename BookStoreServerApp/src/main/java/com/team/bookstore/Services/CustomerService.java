package com.team.bookstore.Services;

import com.team.bookstore.Dtos.Responses.AuthenticationResponse;
import com.team.bookstore.Dtos.Responses.CustomerInformationResponse;
import com.team.bookstore.Dtos.Responses.UserResponse;
import com.team.bookstore.Entities.CustomerInformation;
import com.team.bookstore.Entities.User;
import com.team.bookstore.Entities.User_Role;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Enums.Object;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Exceptions.ObjectException;
import com.team.bookstore.Mappers.UserMapper;
import com.team.bookstore.Repositories.CustomerInformationRepository;
import com.team.bookstore.Repositories.RoleRepository;
import com.team.bookstore.Repositories.UserRepository;
import com.team.bookstore.Repositories.User_RoleRepository;
import com.team.bookstore.Utilities.ImageUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.UserSpecifications.GenerateCustomerKeywordSpec;

@Service
@Log4j2
public class CustomerService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    User_RoleRepository user_roleRepository;
    @Autowired
    CustomerInformationRepository customerInformationRepository;
    @Autowired
    UserService userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    RoleService roleService;
    @Autowired
    PasswordEncoder passwordEncoder;
    public UserResponse customerRegister(User user){
        try {
            if(userRepository.existsByUsername(user.getUsername())){
                throw new ObjectException(user.getUsername(),ErrorCodes.HAS_BEEN_EXIST);
            }
            String decodePassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(decodePassword);
            User_Role user_role = new User_Role();
            user_role.setUser(user);
            user_role.setRole(roleRepository.findRoleByRolename("CUSTOMER"));
            user.getUser_role().add(user_role);
            return userMapper.toUserResponse(userRepository.save(user));
        }catch (ObjectException e){
            log.info(e);
            throw e;
        }
        catch (Exception e){
            log.info(e);
            throw new ApplicationException(ErrorCodes.REGISTER_FAILD);
        }
    }
    public CustomerInformationResponse getMyInfo(){
        try{
            Authentication authentication =
                    SecurityContextHolder.getContext().getAuthentication();
            if(authentication == null){
                throw new ApplicationException(ErrorCodes.UNAUTHENTICATED);
            }
            if(!userRepository.existsByUsername(authentication.getName())){
                throw new ObjectException(Object.USER.getName(),ErrorCodes.NOT_EXIST);
            }
            int customer_id =
                    userRepository.findUsersByUsername(authentication.getName()).getId();
            return userMapper.toCustomerInformationResponse(customerInformationRepository.findCustomerInformationById(customer_id));
        }catch (Exception e){
            log.info(e);
            throw new ObjectException(Object.MY_INF.getName(),ErrorCodes.NOT_EXIST);
        }
    }
    public CustomerInformationResponse createCustomerInformation(int id,
                                                                 MultipartFile image,
            CustomerInformation customerInformation) {
        try{
            byte[] compressImage = ImageUtils.compressImage(image.getBytes(),
                    0.2f);
            customerInformation.setAvatar(compressImage);
            if(!userRepository.existsById(id)){
                throw new ObjectException(Object.USER.getName(),
                        ErrorCodes.NOT_EXIST);
            }
            log.info("000000000000000000000000000000000000000000000000000");
            customerInformation.setId(id);
            log.info("111111111111111111111111111111111111111111111111111111");
            if(customerInformationRepository.existsCustomerInformationByEmail(customerInformation.getEmail())){
                throw new ObjectException(customerInformation.getEmail(),
                        ErrorCodes.HAS_BEEN_EXIST);
            }
            if(customerInformationRepository.existsCustomerInformationByPhonenumber(customerInformation.getPhonenumber())){
                throw new ObjectException(customerInformation.getPhonenumber(),ErrorCodes.HAS_BEEN_EXIST);
            }
            CustomerInformation savedCustomerInformation =
                    customerInformationRepository.save(customerInformation);
            log.info("iddddd" + savedCustomerInformation.getId());
            log.info("nameeeeeee"+ savedCustomerInformation.getFullname());
            return userMapper.toCustomerInformationResponse(savedCustomerInformation);

        } catch(ObjectException | IOException e){
            log.info(e);
        }
        return null;
    }
    public CustomerInformationResponse updateCustomerInformation(int id,
                                                                 MultipartFile image,
                                                                 CustomerInformation customerInformation){
        try{
            byte[] compressImage = ImageUtils.compressImage(image.getBytes(),
                    0.2f);
            customerInformation.setAvatar(compressImage);
            if(!userRepository.existsById(customerInformation.getId()) && customerInformationRepository.existsCustomerInformationById(customerInformation.getId())){
                throw new ApplicationException(ErrorCodes.USER_NOT_EXIST);
            }
            customerInformation.setId(id);
            if(customerInformationRepository.existsCustomerInformationByEmail(customerInformation.getEmail())){
                throw new ObjectException(customerInformation.getEmail(),
                        ErrorCodes.HAS_BEEN_EXIST);
            }
            if(customerInformationRepository.existsCustomerInformationByPhonenumber(customerInformation.getPhonenumber())){
                throw new ObjectException(customerInformation.getPhonenumber(),ErrorCodes.HAS_BEEN_EXIST);
            }
            CustomerInformation savedCustomerInformation = customerInformationRepository.save(customerInformation);
            return userMapper.toCustomerInformationResponse(savedCustomerInformation);
        }catch(Exception e){
            log.info(e);
            throw new ObjectException(customerInformation.getFullname(),
                    ErrorCodes.CANNOT_UPDATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<CustomerInformationResponse> getAllCustomerInformation(){
        try {
            return customerInformationRepository.findAll().stream().map(userMapper::toCustomerInformationResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ObjectException(Object.CUSTOMERINF.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    @Secured("ROLE_STAFF")
    public List<CustomerInformationResponse> findCustomerInformationBy(String keyword){
        try{
            Specification<CustomerInformation> spec =
                    GenerateCustomerKeywordSpec(keyword);
            return customerInformationRepository.findAll(spec).stream().map(userMapper::toCustomerInformationResponse).collect(Collectors.toList());
        } catch(Exception e){
            log.info(e);
            throw new ObjectException(keyword,ErrorCodes.NOT_EXIST);
        }
    }
}
