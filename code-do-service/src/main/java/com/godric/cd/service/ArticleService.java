package com.godric.cd.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.po.Article;
import com.godric.cd.po.Category;
import com.godric.cd.po.Comment;
import com.godric.cd.po.Label;
import com.godric.cd.po.User;
import com.godric.cd.repository.ArticleRepository;
import com.godric.cd.repository.CategoryRepository;
import com.godric.cd.repository.CommentRepository;
import com.godric.cd.repository.LabelRepository;
import com.godric.cd.repository.UserRepository;
import com.godric.cd.request.ArticleCreateRequest;
import com.godric.cd.request.QueryArticleListRequest;
import com.godric.cd.result.PaginationResult;
import com.godric.cd.utils.SessionUtil;
import com.godric.cd.vo.ArticleDetailVO;
import com.godric.cd.vo.ArticleVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private LabelRepository labelRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

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

        Set<Long> uids = list.stream().map(Article::getUid).collect(Collectors.toSet());
        List<User> userList = userRepository.queryList(uids);
        Map<Long, User> map = userList.stream().collect(Collectors.toMap(User::getId, Function.identity()));

        List<ArticleVO> vos = list.stream().map(a -> {
            ArticleVO vo = new ArticleVO();
            BeanUtils.copyProperties(a, vo);
            vo.setAuthorName(map.get(a.getUid()).getUsername());
            vo.setAuthorAvatar(map.get(a.getUid()).getAvatar());
            return vo;
        }).collect(Collectors.toList());

        return PaginationResult.success(vos, (int)pageRes.getTotal(), request.getCurPage(), request.getPageSize());
    }

    public ArticleDetailVO queryById(Long id) {
        Article article = articleRepository.queryById(id);
        if (Objects.isNull(article)) {
            throw new BizException(BizErrorEnum.INVALID_ARTICLE);
        }

        ArticleDetailVO vo = new ArticleDetailVO();
        BeanUtils.copyProperties(article, vo);
        User user = userRepository.queryById(id);
        vo.setAuthorName(user.getUsername());
        vo.setAuthorAvatar(user.getAvatar());
        List<Comment> comments = commentRepository.queryByArticleId(article.getId());
        vo.setComments(comments);

        articleRepository.addViewNum(article);

        return vo;
    }
}
