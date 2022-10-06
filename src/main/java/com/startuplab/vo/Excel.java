package com.startuplab.vo;

import java.time.LocalDateTime;

import org.json.simple.JSONObject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class Excel {
    private Object data;
    private int assignment_id;
    private int work_id;
    private LocalDateTime create_time;
    private LocalDateTime update_time;
}
