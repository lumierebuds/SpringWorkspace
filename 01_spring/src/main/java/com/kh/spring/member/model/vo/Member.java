package com.kh.spring.member.model.vo;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@NoArgsConstructor // 롬복이 매개변수 없는 생성자 만들어줄것
@AllArgsConstructor // 롬복이 매개변수 있는 생성자를 만들어줌
@Getter // 롬복이 getter 함수만듬
@Setter // 롬복이 setter 함수 만듬
@ToString // 롬복이 toString 함수를 만듬
@Builder // 롬복이 빌더패턴을 구현
public class Member {
	private int userNo;
	private String userId;
	private String userPwd;
	private String userName;
	private String email;
	private String birthday;
	private String gender;
	private String phone;
	private String address;
	private Date enrollDate;
	private Date modifyDate;
	private String status;
}
