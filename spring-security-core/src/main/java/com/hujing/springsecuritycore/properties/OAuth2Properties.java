package com.hujing.springsecuritycore.properties;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hj
 * @create 2019-05-04 21:50
 */
@Data
public class OAuth2Properties {
    private List<ClientProperties> clients = new ArrayList<>();
}
