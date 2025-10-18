package com.hongchu.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;
import java.time.LocalDateTime;

/**
 * API调用日志实体类
 */
@Data
@TableName("api_call_logs")
public class ApiCallLog {

    @TableId(type = IdType.AUTO)private Long id;
    @TableField("api_name") private String apiName;
    private String url;
    private String method;
    @TableField("request_params") private String requestParams;
    @TableField("request_body") private String requestBody;
    @TableField("response_code") private Integer responseCode;
    @TableField("response_data") private String responseData;
    @TableField("error_message") private String errorMessage;
    @TableField("start_time") private LocalDateTime startTime;
    @TableField("end_time") private LocalDateTime endTime;
    private Long duration;
    private String status;
    @TableField("created_at") private LocalDateTime createdAt;
    @TableField("caller_ip") private String callerIp;
}