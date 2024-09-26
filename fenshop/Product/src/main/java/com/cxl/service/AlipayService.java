package com.cxl.service;

import com.alipay.api.AlipayApiException;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;

public interface AlipayService {
    String pay(String orderNo, BigDecimal total) throws AlipayApiException;
    boolean check(HttpServletRequest request) throws UnsupportedEncodingException, AlipayApiException;
}
