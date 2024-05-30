package com.team.bookstore.Services;
import com.team.bookstore.Dtos.Responses.PermissionResponse;
import com.team.bookstore.Entities.Permission;
import com.team.bookstore.Enums.ErrorCodes;
import com.team.bookstore.Enums.Object;
import com.team.bookstore.Exceptions.ApplicationException;
import com.team.bookstore.Exceptions.ObjectException;
import com.team.bookstore.Mappers.PermissionMapper;
import com.team.bookstore.Repositories.PermissionRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.team.bookstore.Specifications.PermissionSpecification.GeneratePermissionKeywordSpec;

@Service
@Log4j2
public class PermissionService {
    @Autowired
    PermissionMapper permissionMapper;
    @Autowired
    PermissionRepository permissionRepository;
    @Secured("ROLE_ADMIN")
    public PermissionResponse createPermission(Permission permission){
        try{
            if(permissionRepository.existsPermissionByPermissionname(permission.getPermissionname())){
                throw new ObjectException(permission.getPermissionname(),
                        ErrorCodes.NOT_EXIST);
            }
            return permissionMapper.toPermissionResponse(permissionRepository.save(permission));
        }catch(Exception e){
            throw new ObjectException(permission.getPermissionname(),
                    ErrorCodes.CANNOT_CREATE);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<PermissionResponse> getAllPermission(){
        try{
            return permissionRepository.findAll().stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ObjectException(Object.PERMISSION.getName(),
                    ErrorCodes.NOT_EXIST);
        }
    }
    @Secured("ROLE_ADMIN")
    public List<PermissionResponse> findPermission(String keyword){
        try{
            Specification<Permission> spec =
                    GeneratePermissionKeywordSpec(keyword);
            return permissionRepository.findAll(spec).stream().map(permissionMapper::toPermissionResponse).collect(Collectors.toList());
        } catch (Exception e){
            log.info(e);
            throw new ObjectException(keyword,
                    ErrorCodes.NOT_EXIST);
        }
    }
    @Secured("ROLE_ADMIN")
    public PermissionResponse deletePermission(String permissionname){
        try{
            if(!permissionRepository.existsPermissionByPermissionname(permissionname)){
                throw new ObjectException(permissionname,
                        ErrorCodes.NOT_EXIST);
            }
            Permission existPermission =
                    permissionRepository.findPermissionByPermissionname(permissionname);
            permissionRepository.delete(existPermission);
            return permissionMapper.toPermissionResponse(existPermission);
        } catch(Exception e){
            log.info(e);
            throw new ObjectException(permissionname,
                    ErrorCodes.CANNOT_DELETE);
        }
    }
}
