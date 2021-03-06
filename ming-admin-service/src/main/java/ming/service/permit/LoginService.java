package ming.service.permit;

import ming.constant.AdminErrorCode;
import ming.constant.TokenItem;
import ming.dao.permit.UserMapper;
import ming.dto.permit.LoginDto;
import ming.framework.exception.SystemException;
import ming.framework.util.EncryptUtil;
import ming.framework.validate.Validator;
import ming.po.permit.User;
import ming.token.TokenService;
import ming.validate.permit.LoginValidate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginService {

	@Resource
	private UserMapper userMapper;
	@Resource
	private LoginValidate loginValidate;
	@Resource
	private TokenService tokenService;

	public void login(LoginDto loginDto) {
		loginValidate.checkVerifyCodeIsEqual(loginDto.getToken(), loginDto.getVerifyCode());
		User user = userMapper.selectById(loginDto.getUserId());
		validateUserIsExist(user);
		validatePassword(loginDto.getPassword(), user.getPassword());
		fillLoginDto(loginDto, user);
	}

	private void validateUserIsExist(User user) {
		Validator.notNull(user, AdminErrorCode.USER_NOT_EXIST);
	}

	private void validatePassword(String password, String dbPassword) {
		if (!EncryptUtil.bcryptCheck(password, dbPassword)) {
			throw new SystemException(AdminErrorCode.PASSWORD_LOGIN_ERROR);
		}
	}

	private void fillLoginDto(LoginDto loginDto, User user) {
		loginDto.setUserId(user.getUserId());
		loginDto.setNickName(user.getNickName());
		loginDto.setLoginToken(getLoginToken(loginDto));
	}

	private String getLoginToken(LoginDto loginDto) {
		String loginToken = tokenService.createLoginToken();
		tokenService.putItem(loginToken, TokenItem.OPERATOR,loginDto.getUserId());
		tokenService.putItem(loginToken, TokenItem.NICK_NAME,loginDto.getNickName());
		return loginToken;
	}
}
