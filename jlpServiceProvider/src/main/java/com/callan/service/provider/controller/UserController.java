package com.callan.service.provider.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.callan.service.provider.config.JLPLog;
import com.callan.service.provider.config.ThreadPoolConfig;
import com.callan.service.provider.pojo.AdvanceQueryResponse;
import com.callan.service.provider.pojo.ControllerBaseResponse;
import com.callan.service.provider.pojo.db.JUser;
import com.callan.service.provider.pojo.user.UserChangePwd;
import com.callan.service.provider.pojo.user.UserRole;
import com.callan.service.provider.service.IJUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@Api(description = "用户管理")
public class UserController {
	
	@Autowired
	private IJUserService jUserService;
	
	@ApiOperation(value = "用户登录")
	@RequestMapping(value = "/api/user", method = { RequestMethod.POST })
	public String login(JUser user) {
		JUser loginUser = jUserService.login(user.getLogincode(),user.getLoginpwd());
		if(loginUser == null || loginUser.getId() == 0) {
			ControllerBaseResponse response = new ControllerBaseResponse();
			response.getResponse().setCode("400");
			response.getResponse().setText("用户名或密码错误");
			return response.toJsonString();
		}
		
//        JSystemconfig systemconfig = orclJlpContext.JSystemconfigs.FirstOrDefault(x => x.Activeflag == "1" && x.Classtype == "system" && x.Keyname == "LoginActiveHours");
//        int activeHours = 24;
//        if (systemconfig != null && int.TryParse(systemconfig.Keyvalue, out int tempInt))
//        {
//            activeHours = tempInt;
//        }
//        DateTime date = DateTime.Now;
//        DateTime dateActive = date.AddHours(activeHours);
//        string Ip = StaticProperties.GetHostAddress();
//        string secretKey = Guid.NewGuid().ToString("N");
//        string token = StaticProperties.MD5Encrypt32(dateActive.ToString("yyyy-MM-dd HH:mm:ss") + users.Id.ToString() + Ip + user.userPwd + secretKey);
//        JUsertokens usertokens = new JUsertokens()
//        {
//            Activedate = date.AddHours(activeHours),
//            Activeflag = "1",
//            Createdate = date,
//            Loginip = Ip,
//            Token = token
//        };
//        usertokens.NewId(orclJlpContext);
//        users.Token = token;
//        users.Secretkey = secretKey;
//
//        var roles = (from role in orclJlpContext.JRoles
//                     join roleright in orclJlpContext.JRolerights on role.Id equals roleright.Roleid into rolerights
//                     from roleright in rolerights
//                     where role.Activeflag == JLPStaticProperties.ActiveFlag && roleright.Activeflag == JLPStaticProperties.ActiveFlag && role.Id == users.Userrole
//                     group roleright by role into rolegroups
//                     select rolegroups).ToList();
//        var roleRet = roles.Select(x => new { roleId = x.Key.Id, roleName = x.Key.Name, roleRights = x.Select(y => y.Rightid).ToArray() }).FirstOrDefault();
//
//        orclJlpContext.JUsertokens.Add(usertokens);
//        orclJlpContext.Entry(users).State = System.Data.Entity.EntityState.Modified;
//        orclJlpContext.SaveChanges();
//
//        var userInfo = new { userId = users.Id, userName = users.Name, AuthorizaToken = token, role = roleRet };
//        var ret = ResponseModel.Ok("登录成功").ToDictionaryObj();
//        ret.Add("userInfo", userInfo);
//        return Ok(ret);
		return null;
	}
	
	
	@ApiOperation(value = "更改用户密码")
	@RequestMapping(value = "/api/changepwd", method = { RequestMethod.POST })
    public String ChangePwd(UserChangePwd userChangePwd, HttpSession session) {
		JLPLog log = ThreadPoolConfig.getBaseContext();
		JUser user = (JUser) session.getAttribute("user");
		ControllerBaseResponse response = new ControllerBaseResponse();
		if(user.getLoginpwd() != userChangePwd.getUserPwd()) {
			response.getResponse().setCode("400");
			response.getResponse().setText("用户名或密码错误");
			return response.toJsonString();
		}
		user.setLoginpwd(userChangePwd.getUserNewPwd());
		try {
			jUserService.updatePwd(user);
		}catch(Exception e) {
			log.error("修改密码失败",e);
			response.getResponse().setCode("400");
			response.getResponse().setText(e.getMessage());
			return response.toJsonString();
		}
		return response.toJsonString();
    }

	@ApiOperation(value = "获取角色")
	@RequestMapping(value = "/api/Role", method = { RequestMethod.POST })
    public String Post(UserRole userRole) {
//        JUsers users = orclJlpContext.JUsers.FirstOrDefault(x => x.Activeflag == JLPStaticProperties.ActiveFlag && x.Id == userRole.userId);
//        if (users is null) { return NotFound(); }
//        JRole jRole = orclJlpContext.JRoles.FirstOrDefault(x => x.Activeflag == JLPStaticProperties.ActiveFlag && x.Id == userRole.roleId);
//        if (jRole is null) { return NotFound(); }
//
//        if (users.Userrole != jRole.Id)
//        {
//            users.Userrole = jRole.Id;
//            orclJlpContext.Entry(User).State = System.Data.Entity.EntityState.Modified;
//            orclJlpContext.SaveChanges();
//        }
//
//        return Ok(ResponseModel.Ok().ToDictionaryObj());
		return null;
    }
    
    
}
