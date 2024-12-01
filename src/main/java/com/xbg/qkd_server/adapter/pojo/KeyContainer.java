package com.xbg.qkd_server.adapter.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥容器Pojo类
 * @Date 2024-12-01
 */

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyContainer {

    @JsonProperty("Keys")
    List<KeyData> keys;

    @JsonProperty("key_container_extension")
    Object keyContainerExtension;

}
