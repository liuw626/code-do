package com.godric.cd.controller;

import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.result.DataResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {

    @GetMapping("do")
    public DataResult<String> doTest(int k) {
        if (k == 1) {
            throw new BizException(BizErrorEnum.PARAM_CANNOT_NULL);
        }

        return DataResult.success("success");
    }

}
