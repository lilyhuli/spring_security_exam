package cn.lilyhuli.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 返回服务器状态的bean类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBean {
    private Integer status;
    private String msg;
    private Object obj;


}
