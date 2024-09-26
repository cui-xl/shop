package com.cxl.service.impl;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.AlipayConfig;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeWapPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.cxl.config.PayConfig;
import com.cxl.service.AlipayService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Component
public class AlipayServiceImpl implements AlipayService {

    @Override
    public String pay(String orderNo, BigDecimal total) throws AlipayApiException {
        AlipayClient client=new DefaultAlipayClient(
                PayConfig.URL,
                PayConfig.APPID,
                PayConfig.RSA_PRIVATE_KEY,
                PayConfig.FORMAT,
                PayConfig.CHARSET,
                PayConfig.ALIPAY_PUBLIC_KEY,
                PayConfig.SIGNTYPE
        );
        AlipayTradeWapPayRequest request=new AlipayTradeWapPayRequest();
        AlipayTradeWapPayModel model=new AlipayTradeWapPayModel();
        model.setOutTradeNo(orderNo);
        model.setTotalAmount(total.toString());
        model.setSubject("崔鑫磊商城订单");
        model.setProductCode("QUICK_WAP_WAY");
        request.setNotifyUrl(PayConfig.NOTIFY_URL);
        request.setReturnUrl(PayConfig.NOTIFY_URL);
        request.setBizModel(model);
        return  client.pageExecute(request).getBody();
    }

    @Override
    public boolean check(HttpServletRequest request) throws AlipayApiException {
        Map<String,String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            if (name.equals("open_id")||name.equals("open_name")){
                continue;
            }
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            System.out.println(name+"======>"+valueStr);
            params.put(name, valueStr);
        }
        return AlipaySignature.rsaCheckV1(params,PayConfig.ALIPAY_PUBLIC_KEY, PayConfig.CHARSET, "RSA2");
    }
}
