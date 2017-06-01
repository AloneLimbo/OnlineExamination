package com.limbo.dao;

import com.limbo.entity.Paper;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * Created by limbo on 17-4-17.
 */
@Mapper
public interface PaperDao {


    @Insert("INSERT INTO paper(paper_id, paper_name, paper_question, founder_id, paper_score) \n" +
            "    VALUE(uuid(),#{paper_name},#{paper_question},#{founder_id},#{paper_score})")
    int insertPaper(Paper paper);

    @Select("select paper_name,paper_id from paper WHERE paper_name LIKE #{paperName} AND founder_id=#{founderId} ORDER BY paper_create_date DESC  LIMIT 0,4")
    List<Paper> searchPaper(@Param("paperName") String paperName, @Param("founderId") String founderId);

    @Select("SELECT paper_id,paper_name,paper_question,paper_score FROM  paper where paper_id=#{paperId}")
    Paper queryPaperById(@Param("paperId") String paperId);

    @Select("SELECT paper_id,paper_name,paper_score,paper_create_date,u.real_name FROM paper p Join user u ON p.founder_id=#{founderId} AND u.user_id=p.founder_id ORDER BY paper_create_date DESC LIMIT #{offset},8;")
    List<Paper> queryAllByFounderId(@Param("founderId") String founderId, @Param("offset") int offset);

    @Select("SELECT count(paper_id) FROM paper WHERE founder_id=#{founderId}")
    int queryCountByFounderId(@Param("founderId") String founderId);
}
