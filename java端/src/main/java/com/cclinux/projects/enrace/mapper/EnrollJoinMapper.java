package com.cclinux.projects.enrace.mapper;

import com.cclinux.framework.core.mapper.ProjectBaseMapper;
import com.cclinux.projects.enrace.model.EnrollJoinModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository("EnRaceEnrollJoinMapper")
@Mapper
public interface EnrollJoinMapper extends ProjectBaseMapper<EnrollJoinModel> {

}
