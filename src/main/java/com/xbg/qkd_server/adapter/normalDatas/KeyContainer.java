package com.xbg.qkd_server.adapter.normalDatas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.adapter.NormalData;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @Author XBG
 * @Description: 密钥容器Pojo类
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyContainer extends NormalData {

    @JsonProperty("Keys")
    List<KeyData> keys;

    @JsonProperty("key_container_extension")
    Object keyContainerExtension;

}
