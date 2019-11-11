package com.cc.ata.constant;
import lombok.Data;

import java.util.List;


@Data
public class TreeResult {
    private Status status;
    private List<TreeEntity> data;
}