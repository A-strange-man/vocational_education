package com.example.online_class.controller;

import com.example.online_class.model.entity.VideoOrder;
import com.example.online_class.model.request.VideoOrderRequest;
import com.example.online_class.service.VideoOrderService;
import com.example.online_class.util.JsonData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author CaoJing
 * @date 2021/07/21 20:52
 */
@RestController
@RequestMapping("/api/v1/pri/order")
public class VideoOrderController {

    @Autowired
    private VideoOrderService videoOrderService;

    /**
     * 下单接口
     * @return
     */
    @RequestMapping(value = "/save_order")
    public JsonData saveOrder(@RequestBody VideoOrderRequest videoOrderRequest, HttpServletRequest request) {

        Integer userId = (Integer) request.getAttribute("user_id");
        int rows = videoOrderService.save(userId, videoOrderRequest.getVideoId());
        return rows == 0 ? JsonData.buildError("下单失败") : JsonData.buildSuccess("下单成功");
    }

    /**
     * 获取订单列表
     * @param request
     * @return
     */
    @GetMapping(value = "/list_order")
    public JsonData listOrder(HttpServletRequest request) {

        Integer userId = (Integer) request.getAttribute("user_id");
        List<VideoOrder> videoOrderList = videoOrderService.listOrderByUserId(userId);
        return JsonData.buildSuccess(videoOrderList);
    }
}
