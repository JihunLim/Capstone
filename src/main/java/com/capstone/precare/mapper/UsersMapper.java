package com.capstone.precare.mapper;


import com.capstone.precare.model.Users;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 사용자 Mapper (DAO)
 *
 * created 18.08.28  by 임지훈
 */

@Mapper
@Repository
public interface UsersMapper {

    //select : 모든 사용자 정보를 가져옴
    List<Users> getUserList();

    //Insert : 사용자 정보 db에 삽입
    void insertUserDao(Users data);

    //select : 사용자 id로 사용자 정보를 가져옴
    Users findUserByUserId(String data);

    //select : 사용자 id가 중복인지 확인
    int checkUserId(String data);





}
