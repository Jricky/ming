package ming.admin.facade.permit;

import ming.admin.facade.BaseFacade;
import ming.admin.vo.base.TransRes;
import ming.admin.vo.trans.UserReq;
import ming.admin.vo.trans.UserRoleReq;
import ming.dto.permit.UserDto;
import ming.dto.permit.UserRoleDto;
import ming.service.permit.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserFacade extends BaseFacade {

	@Resource
	private UserService userService;

	public TransRes userAdd(UserReq userReq) {
		UserDto userDto = buildUserDto(userReq);
		userService.add(userDto);
		return buildRes(userReq);
	}

	public TransRes userDelete(UserReq userReq) {
		UserDto userDto = buildUserDto(userReq);
		userService.delete(userDto);
		return buildRes(userReq);
	}

	public TransRes userModify(UserReq userReq) {
		UserDto userDto = buildUserDto(userReq);
		userService.modify(userDto);
		return buildRes(userReq);
	}

	public TransRes userRoleModify(UserRoleReq userRoleReq) {
		UserRoleDto userRoleDto = buildUserRoleDto(userRoleReq);
		userService.modifyRoleList(userRoleDto);
		return buildRes(userRoleReq);
	}

	private UserDto buildUserDto(UserReq userReq) {
		UserDto userDto = new UserDto();
		userDto.setUserId(userReq.getUserId());
		userDto.setName(userReq.getName());
		userDto.setNickName(userReq.getNickName());
		userDto.setPassword(userReq.getPassword());
		userDto.setPasswordConfirm(userReq.getPasswordConfirm());
		userDto.setStatus(userReq.getStatus());
		return userDto;
	}

	private UserRoleDto buildUserRoleDto(UserRoleReq userRoleReq) {
		UserRoleDto userRoleDto = new UserRoleDto();
		userRoleDto.setUserId(userRoleReq.getUserId());
		userRoleDto.setRoleList(buildUserRoleList(userRoleReq));
		return userRoleDto;
	}

	private List<String> buildUserRoleList(UserRoleReq userRoleReq) {
		String roleListStr = userRoleReq.getRoleListStr().replace(" ", "");
		String[] roleListArray = StringUtils.split(roleListStr, ",");
		List<String> roleList = new ArrayList<>();
		for (String userRoleId : roleListArray) {
			roleList.add(userRoleId);
		}
		return roleList;
	}
}
