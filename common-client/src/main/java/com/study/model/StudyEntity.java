package com.study.model;

import lombok.Data;

/**
 * 类说明:
 *
 * @author zengpeng
 */
@Data
public class StudyEntity {
    private Long id;
    private String name;
    private Integer age;
    private ClassObjectValue classId;
}
