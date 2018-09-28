package com.capstone.precare.controller;

import com.capstone.precare.config.ApiResponseMessage;
import com.capstone.precare.mapper.UsersMapper;
import com.capstone.precare.model.Users;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 사용자 Controller
 *
 * created 18.08.30  by 임지훈
 */

@RestController
@MapperScan("com.capstone.precare.mapper")
@Api(value="Users Controller", description = "사용자 API")
public class UsersController {
    @Autowired
    private UsersMapper usersMapper;


    @RequestMapping(value = "/users/all", method= RequestMethod.GET)
    @ApiOperation(value="user api", notes = "사용자정보를 반환하는 API, Ajax 통신 확인용.")
    public List<Users> showUsers(Model model){
        List<Users> users = usersMapper.getUserList();
        ApiResponseMessage msg = new ApiResponseMessage("Success", "users", "", "");
        //return new ResponseEntity<ApiResponseMessage>(msg, HttpStatus.OK);
        return users;
    }

    /**
     * 사용자 ID 중복여부 확인 API
     *
     *
     */
    @RequestMapping(value = "/user/id_check", method= RequestMethod.POST)
    @ApiOperation(value="사용자 ID 중복여부 확인 API", notes = "사용자 ID 중복여부 반환하는 API, Ajax 통신 확인용.")
    public ResponseEntity<Boolean> checkUserId(@RequestParam("user_id") String user_id){
        Boolean checkUserid = false;
        try{
            int useridNum = usersMapper.checkUserId(user_id);
            if(useridNum > 0) checkUserid = false; // 이미 id가 있으므로 사용 불가능
            else checkUserid = true; //사용 가능
        }catch (Exception ex){
            return new ResponseEntity<Boolean>(checkUserid, HttpStatus.CONFLICT);
        }
        return new ResponseEntity<Boolean>(checkUserid, HttpStatus.OK);
    }










}
