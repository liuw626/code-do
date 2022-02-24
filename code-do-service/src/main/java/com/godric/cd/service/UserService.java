package com.godric.cd.service;

import com.godric.cd.constant.CodeDoConstant;
import com.godric.cd.constant.RedisConstant;
import com.godric.cd.enums.ActionTypeEnum;
import com.godric.cd.enums.TargetTypeEnum;
import com.godric.cd.exception.BizErrorEnum;
import com.godric.cd.exception.BizException;
import com.godric.cd.po.ActionRecord;
import com.godric.cd.po.Article;
import com.godric.cd.po.Comment;
import com.godric.cd.po.User;
import com.godric.cd.repository.ActionRecordRepository;
import com.godric.cd.repository.ArticleRepository;
import com.godric.cd.repository.CacheRepository;
import com.godric.cd.repository.CommentRepository;
import com.godric.cd.repository.UserRepository;
import com.godric.cd.request.UserUpdateRequest;
import com.godric.cd.utils.CodeUtil;
import com.godric.cd.utils.SessionUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CacheRepository cacheRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ActionRecordRepository actionRecordRepository;

    private static final String DEFAULT_USERNAME_PREFIX = "微信用户";

    public User fillCode(String code) {
        String key = String.format(RedisConstant.VERIFY_CODE, code);
        String openId = cacheRepository.get(key);
        if (StringUtils.isBlank(openId)) {
            throw new BizException(BizErrorEnum.INVALID_VERIFY_CODE);
        }
        User user = userRepository.queryByOpenId(openId);
        if (Objects.isNull(user)) {
            return userRepository.insert(generateName(), CodeDoConstant.DEFAULT_AVATAR, openId);
        }

        return user;
    }

    public String generateVerifyCode() {
        String verifyCode = "";
        String key = "";
        do {
            verifyCode = CodeUtil.randomCode();
            key = String.format(RedisConstant.VERIFY_CODE, verifyCode);
        } while (StringUtils.isNotBlank(cacheRepository.get(key)));
        return verifyCode;
    }

    public String generateName() {
        String suffix = UUID.randomUUID().toString().substring(0, 8);
        return DEFAULT_USERNAME_PREFIX + suffix;
    }

    @Transactional
    public void thumb(String targetType, Long targetId) {

        if (!TargetTypeEnum.valid(targetType)) {
            throw new BizException(BizErrorEnum.PARAM_ERROR);
        }

        if (TargetTypeEnum.ARTICLE.name().equalsIgnoreCase(targetType)) {
            // 点赞文章
            Article article = articleRepository.queryById(targetId);
            if (Objects.isNull(article)) {
                throw new BizException(BizErrorEnum.INVALID_ARTICLE);
            }

            articleRepository.addThumbNum(article);
        }
        if (TargetTypeEnum.COMMENT.name().equalsIgnoreCase(targetType)) {
            // 点赞评论
            Comment comment = commentRepository.queryById(targetId);
            if (Objects.isNull(comment)) {
                throw new BizException(BizErrorEnum.INVALID_COMMENT);
            }

            commentRepository.addThumbNum(comment);
        }

        ActionRecord actionRecord = ActionRecord.builder()
                                                .actionType(ActionTypeEnum.THUMB.name())
                                                .targetId(targetId)
                                                .targetType(targetType)
                                                .uid(SessionUtil.getUid())
                                                .build();

        // 创建点赞记录
        actionRecordRepository.create(actionRecord);
    }

    @Transactional
    public void collect(Long articleId) {
        // 点赞文章
        Article article = articleRepository.queryById(articleId);
        if (Objects.isNull(article)) {
            throw new BizException(BizErrorEnum.INVALID_ARTICLE);
        }

        articleRepository.addCollectNum(article);

        ActionRecord actionRecord = ActionRecord.builder()
                .actionType(ActionTypeEnum.COLLECT.name())
                .targetId(articleId)
                .targetType(TargetTypeEnum.ARTICLE.name())
                .uid(SessionUtil.getUid())
                .build();

        // 创建点赞记录
        actionRecordRepository.create(actionRecord);
    }

    public void update(UserUpdateRequest request) {
        Long uid = SessionUtil.getUid();
        User user = userRepository.queryById(uid);
        user.setAvatar(request.getAvatar());
        user.setUsername(request.getUsername());
        userRepository.update(user);
    }
}
