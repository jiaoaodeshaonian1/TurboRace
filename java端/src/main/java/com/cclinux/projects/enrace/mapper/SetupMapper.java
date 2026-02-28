package com.cclinux.projects.enrace.mapper;

import com.cclinux.framework.core.mapper.ProjectBaseMapper;
import com.cclinux.projects.enrace.model.SetupModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("EnRaceSetupMapper")
@Mapper
public interface SetupMapper extends ProjectBaseMapper<SetupModel> {
}
