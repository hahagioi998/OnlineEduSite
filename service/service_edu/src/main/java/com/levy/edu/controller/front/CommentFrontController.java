package com.levy.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.commonutils.JwtUtils;
import com.levy.commonutils.R;
import com.levy.edu.entity.Comment;
import com.levy.edu.entity.vo.CommentVo;
import com.levy.edu.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

/**
 * @description：前台评论页面的Controller
 * @author：LevyXie
 * @create：2022-03-24 10:26
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/comment")
public class CommentFrontController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/getCommentPage/{courseId}/{current}/{limit}")
    public R getCommentPage(@PathVariable long courseId,
                            @PathVariable long current,
                            @PathVariable long limit){
        Page<Comment> commentPage = new Page<>(current,limit);
        HashMap<String, Object> map = commentService.getComments(courseId,commentPage);
        return R.ok().data(map);
    }

    @PostMapping("/addComment")
    public R getCommentPage(@RequestBody CommentVo commentVo,
                            HttpServletRequest request){
        String id = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(id)){
            return R.error().message("请登录");
        }
        commentService.saveComment(commentVo,id);
        return R.ok();
    }
}
