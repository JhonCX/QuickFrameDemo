package com.cc.ata.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *
 * </p>
 *
 * @author CC
 * @since 2019-11-10
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class QUser implements Serializable {

    private static final long serialVersionUID = 1L;
    /*自增id*/
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /*登录用户名*/
    private String email;
    /*登录密码*/
    private String pwd;
    /*昵称*/
    private String nickName;
    /*账户类型*/
    private String type;
    /*是否可用*/
    private Integer flag;
    /*登录时间*/
    private String loginTime;
    /*注册时间*/
    private String registerTime;


}
