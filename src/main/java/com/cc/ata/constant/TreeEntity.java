package com.cc.ata.constant;

import lombok.Data;

import java.util.List;

/**
 * @author :cc
 * @date : 2019/6/10
 */
@Data
public class TreeEntity {
    private Integer id;
    private String title;
    private Integer checkArr=0;
    private Integer parentId;
    private Integer itemId;
    private Integer type;
    private Integer flag;
    private Integer checked;
    private Boolean hide;
    private Boolean disabled;
    private List<TreeEntity> children;
}
