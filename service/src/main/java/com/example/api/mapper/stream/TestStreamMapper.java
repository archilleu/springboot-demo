package com.example.api.mapper.stream;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.api.model.entity.TestStream;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultType;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.mapping.ResultSetType;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Repository;

/**
 * @author cjy
 */
@Mapper
@Repository
public interface TestStreamMapper extends BaseMapper<TestStream> {

    /**
     * 返回数据
     *
     * @param handler 回调
     */
    @Select("select * from test_stream")
    @Options(resultSetType = ResultSetType.FORWARD_ONLY, fetchSize = Integer.MIN_VALUE)
    @ResultType(TestStream.class)
    void stream(ResultHandler<TestStream> handler);
}
