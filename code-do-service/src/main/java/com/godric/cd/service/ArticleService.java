package com.godric.cd.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.po.Article;
import com.godric.cd.po.Category;
import com.godric.cd.po.Label;
import com.godric.cd.repository.ArticleRepository;
import com.godric.cd.repository.CategoryRepository;
import com.godric.cd.repository.LabelRepository;
import com.godric.cd.request.ArticleCreateRequest;
import com.godric.cd.request.QueryArticleListRequest;
import com.godric.cd.result.PaginationResult;
import com.godric.cd.utils.SessionUtil;
import com.godric.cd.vo.ArticleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LabelRepository labelRepository;

    public void create(ArticleCreateRequest request) {
        Long uid = SessionUtil.getUid();
        Long categoryId = request.getCategoryId();
        Category category = categoryRepository.queryById(categoryId);
        if (Objects.isNull(category)) {
            throw new BizException(BizErrorEnum.INVALID_CATEGORY);
        }

        List<Long> labelIds = request.getLabelIds();
        List<Label> labels = labelRepository.queryByIds(labelIds);
        if (CollectionUtils.isEmpty(labels)) {
            throw new BizException(BizErrorEnum.INVALID_LABEL);
        }

        Article article = Article.builder()
                                 .title(request.getTitle())
                                 .content(request.getContent())
                                 .categoryId(request.getCategoryId())
                                 .labelIds(StringUtils.join(labels.stream().map(Label::getId).collect(Collectors.toList()), ","))
                                 .uid(uid).build();

        articleRepository.create(article);
    }


    public PaginationResult<ArticleVO> queryList(QueryArticleListRequest request) {
        IPage<Article> pageRes = articleRepository.queryPage(request.getKeyword(), request.getCurPage(), request.getPageSize());

        List<Article> list = pageRes.getRecords();
        List<ArticleVO> vos = list.stream().map(a -> {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(a, vo);
            return vo;
        }).collect(Collectors.toList());

        return PaginationResult.success(vos, (int)pageRes.getTotal(), request.getCurPage(), request.getPageSize());
    }
}
