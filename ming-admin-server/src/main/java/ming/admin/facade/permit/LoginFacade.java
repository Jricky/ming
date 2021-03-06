package ming.admin.facade.permit;

import ming.admin.facade.BaseFacade;
import ming.admin.vo.base.TransRes;
import ming.admin.vo.trans.LoginReq;
import ming.dto.permit.LoginDto;
import ming.service.permit.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class LoginFacade extends BaseFacade {

	@Resource
	private LoginService loginService;

	public TransRes login(LoginReq loginReq) {
		LoginDto loginDto = buildLoginDto(loginReq);
		loginService.login(loginDto);
		fillTokenToReq(loginDto, loginReq);
		return super.buildRes(loginReq);
	}

	private LoginDto buildLoginDto(LoginReq loginReq) {
		LoginDto loginDto = new LoginDto();
		loginDto.setUserId(loginReq.getOperator());
		loginDto.setPassword(loginReq.getPassword());
		loginDto.setVerifyCode(loginReq.getVerifyCode());
		loginDto.setToken(loginReq.getToken());
		return loginDto;
	}

	private void fillTokenToReq(LoginDto loginDto, LoginReq loginReq) {
		loginReq.setLoginToken(loginDto.getLoginToken());
		loginReq.setOperator(loginDto.getUserId());
		loginReq.setNickName(loginDto.getNickName());
	}
}
