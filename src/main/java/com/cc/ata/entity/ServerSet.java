package com.cc.ata.entity;

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
public class ServerSet implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(value = "id")
    private Integer id;
    /*登录许可*/
    private Integer loginFlag;
    /*注册许可*/
    private Integer registerFlag;
    /*服务器通知*/
    private String message;


}
