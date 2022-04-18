package com.levy.edu.entity.subject;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-12 14:14
 */
@Data
public class OneSubject {
    private String id;
    private String title;
    //单个一级分类有多个二级分类
    private List<TwoSubject> children = new ArrayList<>();
}
