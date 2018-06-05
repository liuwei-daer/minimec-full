package com.allyes.minimec.domain.dto.sys;

import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class ResourceTreeMenu implements Serializable {

    private Integer id;

    private Integer parentId;

    private String title;

    private String icon;

    private Boolean spread;

    private String href;

    private List<ResourceTreeMenu> children;

}
