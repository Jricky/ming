package ming.admin.vo.trans;

import lombok.Data;
import ming.admin.vo.base.TransReq;

@Data
public class LoginReq extends TransReq {
	private String operator;

	private String password;

	private String verifyCode;
}
