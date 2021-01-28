package com.demo.es.domain.query;

import lombok.Data;

import java.util.List;

@Data
public class StudyQuery {

    private Long id;
    /**
     * 名称
     */
    private String name;

    /**
     * 简介
     */
    private String synopsis;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 所属班级ID
     */
    private Long classId;

    /**
     * 班级名称
     */
    private String className;


    /**
     * 角色ID列表
     */
    private List<Long> roleIds;
}
