
import com.lnet.ums.mapper.dao.mappers.ErpUserMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2016/12/15.
 */
public class selectText {
/*    @Autowired
    SysUserMapper sysUserMapper;



    @Test
    public void Text1() throws IOException {
        SysUser sysUser = sysUserMapper.selectUserById("1");
        System.out.print(sysUser.getName());
    }*/



    @Test
    public void selectAll() throws IOException {
        SqlSession sqlSession=null;
        try {
        /*    ErpUserMapper users = new ErpUserMapper();*/

            System.out.println("1");
            // mybatis配置文件
            String resource = "mybatis.xml";
            System.out.println("2"+resource);
            // 得到配置文件流
            InputStream inputStream = Resources.getResourceAsStream(resource);
            System.out.println("3"+inputStream);

            // 创建会话工厂，传入mybatis的配置文件信息
            SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder()
                    .build(inputStream);
            System.out.println("4"+sqlSessionFactory);

            // 通过工厂得到SqlSession
            sqlSession = sqlSessionFactory.openSession();
            System.out.println("5"+sqlSession);
            // 通过SqlSession操作数据库
            // 第一个参数：映射文件中statement的id，等于=namespace+"."+statement的id
            // 第二个参数：指定和映射文件中所匹配的parameterType类型的参数
            // sqlSession.selectOne结果 是与映射文件中所匹配的resultType类型的对象
            // selectOne查询出一条记录
            ErpUserMapper user = (ErpUserMapper) sqlSession.selectOne("com.lnet.ums.dao.SysUserMapper.selectAll");
            System.out.println("6");
//            System.out.println(user.getUserName());
        } finally {
            // 释放资源
            sqlSession.close();
        }
    }

}


