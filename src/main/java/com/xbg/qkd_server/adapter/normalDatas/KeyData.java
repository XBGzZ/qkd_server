package com.xbg.qkd_server.adapter.normalDatas;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.xbg.qkd_server.adapter.NormalData;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author XBG
 * @Description: 每个密钥的信息，不填写key数据，就不会序列化该字段
 * @Date 2024-12-01
 */

@Data
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class KeyData extends NormalData {

    @JsonProperty("key_ID")
    String keyId;

    @JsonProperty("key_ID_extension")
    Object keyIdExtension;

    // example: "550e8400-e29b-41d4-a716-446655440000"
    @JsonProperty("key")
    String key;

    @JsonProperty("key_extension")
    Object keyExtension;

}
