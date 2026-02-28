package com.cclinux.projects.enrace.mapper;

import com.cclinux.framework.core.mapper.ProjectBaseMapper;
import com.cclinux.projects.enrace.model.EnrollModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;


@Repository("EnRaceEnrollMapper")
@Mapper
public interface EnrollMapper extends ProjectBaseMapper<EnrollModel> {
}
