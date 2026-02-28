package com.cclinux.projects.enrace.mapper;

import com.cclinux.framework.core.mapper.ProjectBaseMapper;
import com.cclinux.projects.enrace.model.NewsModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("EnRaceNewsMapper")
@Mapper
public interface NewsMapper extends ProjectBaseMapper<NewsModel> {
}
