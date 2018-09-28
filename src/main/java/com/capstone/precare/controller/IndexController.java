package com.capstone.precare.controller;

import com.capstone.precare.mapper.UsersMapper;
import com.capstone.precare.model.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * View Controller
 *
 * created 18.08.29  by 임지훈
 */

@Controller
@MapperScan("com.capstone.precare.mapper")
@Api(value="Index Controller", description = "인덱스 API")
public class IndexController {
    @Autowired
    private UsersMapper usersMapper;

    /**
     * Root 뷰
     *
     * @return index view
     */
    @RequestMapping(value = {"/"}, method = RequestMethod.GET)
    @ApiOperation(value = "index view", notes = "Return index veiw")
    public String welcome(Model model) {
        /*
            1. 사용자 정보 반환
         */
        String user_id= SecurityContextHolder.getContext().getAuthentication().getName();
        if("anonymousUser".equals(user_id) || "".equals(user_id)|| user_id == null) {
            //로그인이 안되있는 경우
        }else {
            //로그인이 되어있는 경우
            model.addAttribute("user_info", usersMapper.findUserByUserId(user_id));
        }

        return "index";

    }

    /**
     * 회원가입 뷰
     *
     * @return signup view
     */
    @RequestMapping(value = "/signup")
    @ApiOperation(value = "signup view", notes = "Return signup view")
    public String signup() {
        return "signup";
    }

    /**
     * 구매내역 View
     *
     * @return view
     */
    @RequestMapping(value = "/checkout")
    @ApiOperation(value = "Order history view", notes = "Return order history view")
    public String orderHistory() {
        return "checkout";
    }

    /**
     * 회원가입완료 View
     *
     * @return view
     */
    @RequestMapping(value = "/success_signup")
    @ApiOperation(value = "Success signup", notes = "Return root view")
    public String successSignup() {
        return "success_signup";
    }







}