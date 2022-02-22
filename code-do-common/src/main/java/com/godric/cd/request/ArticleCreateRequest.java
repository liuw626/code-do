package com.godric.cd.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class ArticleCreateRequest {

    @NotBlank(message = "请填写标题")
    private String title;

    @NotBlank(message = "请填写内容")
    private String content;

    @NotNull(message = "请选择类目")
    private Long categoryId;

    @NotEmpty(message = "请关联标签")
    private List<Long> labelIds;

}
