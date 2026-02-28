package com.cclinux.projects.enrace.mapper;

import com.cclinux.framework.core.mapper.ProjectBaseMapper;
import com.cclinux.projects.enrace.model.FavModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository("EnRaceFavMapper")
@Mapper
public interface FavMapper extends ProjectBaseMapper<FavModel> {
}
