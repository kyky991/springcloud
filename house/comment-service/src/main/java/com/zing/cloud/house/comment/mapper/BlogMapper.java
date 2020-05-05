package com.zing.cloud.house.comment.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.zing.cloud.house.comment.model.Blog;
import com.zing.cloud.house.comment.model.LimitOffset;

@Mapper
public interface BlogMapper {

    public List<Blog> selectBlog(@Param("blog") Blog blog, @Param("pageParams") LimitOffset limitOffset);

    public Long selectBlogCount(Blog query);

}
