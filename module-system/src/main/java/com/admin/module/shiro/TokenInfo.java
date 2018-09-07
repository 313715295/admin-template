package com.admin.module.shiro;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * created by zwq on 2018/9/6
 * 存放shiro用户信息
 */
@Data
@Accessors(chain = true)
public class TokenInfo {

    private Integer userId;


}
