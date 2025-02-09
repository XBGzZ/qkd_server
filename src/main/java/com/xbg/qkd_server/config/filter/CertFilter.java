package com.xbg.qkd_server.config.filter;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * @Author XBG
 * @Description:
 * @Date 2025-02-09
 */

public class CertFilter extends Filter {
    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        
    }

    @Override
    public String description() {
        return "";
    }

}
