package me.totoku103.gendatabase.mapper;

import me.totoku103.gendatabase.model.ImcMtMsg;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface InsertMapper {

    @Insert("""
                    INSERT INTO IMC_MT_MSG
                    ( MT_TYPE, STATUS, PRIORITY, RESERVED_DATE, PHONE_NUMBER, COUNTRY_CODE, CALLBACK, MESSAGE)
                    VALUES
                    ( #{imcMtMsg.mtType}, #{imcMtMsg.status}, #{imcMtMsg.priority}, #{imcMtMsg.reservedDate}, 
                    #{imcMtMsg.phoneNumber}, #{imcMtMsg.countryCode}, #{imcMtMsg.callback}, #{imcMtMsg.message})
            """)
    int insert(@Param("imcMtMsg") ImcMtMsg imcMtMsg);

//    @Insert("""
//                    INSERT INTO IMC_MT_MSG
//                        ( MT_TYPE, STATUS, PRIORITY, RESERVED_DATE, PHONE_NUMBER, COUNTRY_CODE, CALLBACK, MESSAGE)
//                    VALUES
//                     <foreach collection="list" item="imcMtMsg" index="index" separator=",">
//                        ( #{imcMtMsg.mtType}, #{imcMtMsg.status}, #{imcMtMsg.priority}, #{imcMtMsg.reservedDate}, #{imcMtMsg.phoneNumber}, #{imcMtMsg.countryCode}, #{imcMtMsg.callback}, #{imcMtMsg.message})
//                    </foreach>
//            """)
//    int insertBulk(@Param("imcMtMsg") List<ImcMtMsg> imcMtMsg);

    int insertBulk(List<ImcMtMsg> imcMtMsg);
}
