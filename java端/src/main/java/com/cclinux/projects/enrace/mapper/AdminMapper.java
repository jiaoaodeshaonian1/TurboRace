package com.cclinux.projects.enrace.mapper;

import com.cclinux.framework.core.mapper.ProjectBaseMapper;
import com.cclinux.projects.enrace.model.AdminModel;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository("EnRaceAdminMapper")
@Mapper
public interface AdminMapper extends ProjectBaseMapper<AdminModel> {
}
