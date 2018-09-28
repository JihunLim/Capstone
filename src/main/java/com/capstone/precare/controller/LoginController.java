package com.capstone.precare.controller;


import com.capstone.precare.mapper.UsersMapper;
import com.capstone.precare.model.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * 로그인 Controller
 *
 * created 18.08.29  by 임지훈
 */

@Controller
@MapperScan("com.capstone.precare.mapper")
@Api(value="Login Controller", description = "로그인 API")
public class LoginController {
    @Autowired
    private UsersMapper usersMapper;

    /**
     * 로그인 뷰
     *
     * @return : 로그인 폼 View
     */
    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("loginform");
        return modelAndView;
    }

    /**
     * 회원가입 (사용자 추가)
     *
     * @param users object to be created
     * @param bindingResult bind some results
     * @return ResponseEntity<ApiResponseMessage>
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    @ApiOperation(value="Create user api", notes = "사용자 정보를 생성하는 API")
    public ModelAndView createUser(@ModelAttribute Users users, BindingResult bindingResult) {
        ModelAndView modelAndView = new ModelAndView();
        Users userExists = usersMapper.findUserByUserId(users.getUser_id());
        if(userExists != null){
            bindingResult.rejectValue("ID Error","Id already is in DB");
        }
        if(bindingResult.hasErrors()){
            modelAndView.setViewName("signup");
        }else{
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(); //비밀번호 암호화
            users.setUser_pwd(passwordEncoder.encode(users.getUser_pwd()));
            //System.out.println(users.toString());
            usersMapper.insertUserDao(users);
            modelAndView.setViewName("cmmn/success_signup");
            modelAndView.addObject("successMsg", "회원가입을 완료했습니다.\n로그인해주세요.");
        }
        return modelAndView;
    }


}
