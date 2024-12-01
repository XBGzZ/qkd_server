package com.xbg.qkd_server.controller;

import com.xbg.qkd_server.adapter.pojo.KeyRequestDataFormat;
import com.xbg.qkd_server.config.ApiConfig;
import com.xbg.qkd_server.adapter.pojo.KMEStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = ApiConfig.PATH)
public class KMEController {
    @GetMapping("/{slave_SAE_ID}/status")
    public Object GetKeyStatus(@PathVariable("slave_SAE_ID") String slaveSAEId) {
        System.out.printf("Sae id is %s \n", slaveSAEId);
        var kmeStatus = new KMEStatus();
        return kmeStatus;
    }

    @PostMapping("/{slave_SAE_ID}/enc_keys")
    public Object GetKeyEncKeys(@PathVariable("slave_SAE_ID") String slaveSAEId) {
        System.out.printf("Sae id is %s \n", slaveSAEId);
        return KeyRequestDataFormat.builder().size(123).number(123).additionalSlaveSaeIds(List.of("alice", "bob")).build();
    }

    @PostMapping("/{master_SAE_ID}/dec_keys")
    public String GetKeyWithKeyIds(@PathVariable("master_SAE_ID") String masterSAEId) {
        System.out.printf("Sae id is %s \n", masterSAEId);
        return "OK";
    }
}
