package com.admin.module.entity;

import com.admin.commons.base.BaseEntity;
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
 * @author zwq
 * @since 2018-09-06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class City extends BaseEntity<City> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "city_id", type = IdType.AUTO)
    private Integer cityId;

    private String cityName;


    @Override
    protected Serializable pkVal() {
        return this.cityId;
    }

}
