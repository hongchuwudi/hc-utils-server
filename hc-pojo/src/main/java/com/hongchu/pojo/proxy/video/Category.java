// Category.java - 分类结构
package com.hongchu.pojo.proxy.video;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.io.Serializable;

/**
 * 分类结构
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    /**
     * 分类ID
     */
    private Object typeId;
    
    /**
     * 父分类ID（0表示顶级）
     */
    private Object typePid;
    
    /**
     * 分类名称
     */
    private String typeName;
}