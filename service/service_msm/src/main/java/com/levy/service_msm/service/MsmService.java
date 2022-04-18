package com.levy.service_msm.service;

import java.util.HashMap;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-20 15:15
 */
public interface MsmService {
    boolean sendMsm(String phone, HashMap<String, Object> map);
}
